package dev.capstone.satako_mobile.presentation.home.diagnose

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.yalantis.ucrop.UCrop
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.data.response.Result
import dev.capstone.satako_mobile.databinding.FragmentDiagnoseBinding
import dev.capstone.satako_mobile.presentation.ViewModelFactory
import dev.capstone.satako_mobile.presentation.home.diagnose.camera.CameraActivity
import dev.capstone.satako_mobile.utils.createCustomTempFile
import dev.capstone.satako_mobile.utils.deleteFromUri
import dev.capstone.satako_mobile.utils.gone
import dev.capstone.satako_mobile.utils.reduceFileImage
import dev.capstone.satako_mobile.utils.show
import dev.capstone.satako_mobile.utils.showBottomSheetDialog
import dev.capstone.satako_mobile.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class DiagnoseFragment : Fragment() {


    private var isPickImage: Boolean = false
    private var currentImageUri: Uri? = null
    private var cameraImageUri: Uri? = null
    private var tempCropFile: File? = null
    private val binding: FragmentDiagnoseBinding by lazy {
        FragmentDiagnoseBinding.inflate(layoutInflater)
    }

    private val viewModel: DiagnoseViewModel by viewModels {
        ViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            backButton.setOnClickListener {
                currentImageUri?.let { deleteFromUri(it) }
                view.findNavController().popBackStack()
            }
            buttonDiagnose.setOnClickListener { view ->
                predict(view)
            }
            galleryCard.setOnClickListener {
                if (!isPickImage) {
                    isPickImage = true
                    startGallery()
                }
            }
            cameraCard.setOnClickListener {
                val intent = Intent(requireActivity(), CameraActivity::class.java)
                launcherIntentCameraX.launch(intent)
            }
        }

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    currentImageUri?.let { deleteFromUri(it) }
                    view.findNavController().popBackStack()
                }
            })

        viewModel.imageUri.observe(viewLifecycleOwner) {
            currentImageUri = it
            showPreview(currentImageUri)
        }
    }


    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CameraActivity.CAMERAX_RESULT) {
            cameraImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            launchUCrop(cameraImageUri!!)
        }
    }
    private val launchGallery =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            uri?.let { launchUCrop(it) } ?: run { isPickImage = false }
        }

    private fun startGallery() {
        launchGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val uCropLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                resultUri?.let { uri ->
                    handleImageUri(uri)
                }
            } else {
                cameraImageUri?.let { deleteFromUri(it) }
                tempCropFile?.delete()
                isPickImage = false
            }
        }

    private fun launchUCrop(sourceUri: Uri) {
        tempCropFile = createCustomTempFile(requireContext())
        val destinationUri = Uri.fromFile(tempCropFile)
        val uCrop = UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f)
        uCrop.getIntent(requireContext()).let {
            uCropLauncher.launch(it)
        }
    }


    private fun predict(view: View) {
        if (currentImageUri != null) {
            currentImageUri?.let { uri ->
                val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
                showLoading(true)

                val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
                val multipartBody = MultipartBody.Part.createFormData(
                    "file",
                    imageFile.name,
                    requestImageFile
                )

                viewModel.predict(multipartBody).observe(viewLifecycleOwner) {
                    if (it != null) {
                     when (it) {
                         is Result.Error -> {
                             showLoading(false)
                             showBottomSheetDialog(
                                 requireContext(),
                                 getString(R.string.failed_to_predict),
                                 R.drawable.error_image,
                                 buttonColorResId = R.color.danger,
                                 onClick = {}
                             )
                         }
                         Result.Loading -> showLoading(true)
                         is Result.Success -> {
                             showLoading(false)
                             val toResultFragment =
                                 DiagnoseFragmentDirections.actionDiagnoseFragmentToResultFragment(
                                     currentImageUri.toString(),
                                     it.data.dataPredict
                                     )
                             isPickImage = false
                             view.findNavController().navigate(toResultFragment)
                         }
                     }
                    }
                }
            }

        } else {
            Toast.makeText(requireContext(), "Please select an image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.pbDiagnose.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun handleImageUri(uri: Uri) {
        currentImageUri?.let { deleteFromUri(it) }
        val imageCompress = uriToFile(uri, requireContext()).reduceFileImage()
        viewModel.setImageUri(imageCompress.toUri())
        tempCropFile?.let { deleteFromUri(it.toUri()) }
        cameraImageUri?.let { deleteFromUri(it) }
        isPickImage = false
    }


    private fun showPreview(uri: Uri?, isShow: Boolean = true) {
        if (uri != null && isShow) {
            binding.previewImageView.setImageURI(uri)
            binding.previewImageView.show()
            binding.previewTextView.show()
        } else {
            binding.previewImageView.gone()
            binding.previewTextView.gone()
        }
    }
}