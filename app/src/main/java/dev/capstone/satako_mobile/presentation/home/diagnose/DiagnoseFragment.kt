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
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.yalantis.ucrop.UCrop
import dev.capstone.satako_mobile.databinding.FragmentDiagnoseBinding
import dev.capstone.satako_mobile.presentation.home.diagnose.camera.CameraActivity
import dev.capstone.satako_mobile.utils.createCustomTempFile
import dev.capstone.satako_mobile.utils.deleteFromUri
import dev.capstone.satako_mobile.utils.gone
import dev.capstone.satako_mobile.utils.show
import java.io.File

class DiagnoseFragment : Fragment() {


    private var isPickImage: Boolean = false
    private var currentImageUri: Uri? = null
    private var cameraImageUri: Uri? = null
    private var tempCropFile: File? = null
    private val binding: FragmentDiagnoseBinding by lazy {
        FragmentDiagnoseBinding.inflate(layoutInflater)
    }

    private val viewModel: DiagnoseViewModel by viewModels()

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
                view.findNavController().popBackStack()
            }
            buttonDiagnose.setOnClickListener { view ->
                toResult(view)
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

        showPreview(null, false)

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
            if (uri != null) {
                launchUCrop(uri)
            } else {
                isPickImage = false
            }
        }

    private fun startGallery() {
        launchGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val uCropLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                resultUri?.let { uri ->
                    viewModel.setImageUri(uri)
                    Log.e("DiagnoseFragment", "uCropLauncher: $cameraImageUri")
                    cameraImageUri?.let { deleteFromUri(it) }
                    showPreview(currentImageUri)
                }
            } else {
                if (cameraImageUri != null) {
                    deleteFromUri(cameraImageUri!!)
                }
                tempCropFile?.delete()
                isPickImage = false
            }
        }

    private fun launchUCrop(sourceUri: Uri) {
        tempCropFile = createCustomTempFile(requireContext())
        val destinationUri = Uri.fromFile(tempCropFile)
        val uCrop = UCrop.of(sourceUri, destinationUri)
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(240, 240)
        uCrop.getIntent(requireContext()).let {
            uCropLauncher.launch(it)
        }
    }


    private fun toResult(view: View) {
        if (currentImageUri != null) {
            val toResultFragment =
                DiagnoseFragmentDirections.actionDiagnoseFragmentToResultFragment(
                    currentImageUri.toString()
                )
            isPickImage = false
            view.findNavController().navigate(toResultFragment)
        } else {
            Toast.makeText(requireContext(), "Please select an image", Toast.LENGTH_SHORT).show()
        }
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