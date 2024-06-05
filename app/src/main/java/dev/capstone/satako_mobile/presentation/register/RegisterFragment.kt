package dev.capstone.satako_mobile.presentation.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.databinding.BottomSheetBinding
import dev.capstone.satako_mobile.databinding.FragmentRegisterBinding
import dev.capstone.satako_mobile.utils.showBottomSheetDialog

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            backButton.setOnClickListener {
                view.findNavController().popBackStack()
            }
            registerButton?.setOnClickListener {
//                Jika Berhasil
                showBottomSheetDialog(
                    requireContext(),
                    getString(R.string.success_register),
                    R.drawable.success_image,
                    onClick = {
                        view.findNavController().popBackStack()
                    }
                )
//                Jika Gagal
                showBottomSheetDialog(
                    requireContext(),
                    getString(R.string.please_fill_out_all_forms),
                    R.drawable.success_image
                )
            }
        }
    }

}