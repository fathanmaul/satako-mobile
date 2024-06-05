package dev.capstone.satako_mobile.utils

import android.content.Context
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import dev.capstone.satako_mobile.R
import dev.capstone.satako_mobile.databinding.BottomSheetBinding

fun showBottomSheetDialog(
    context: Context,
    text: String,
    imageResId: Int,
    buttonColorResId: Int? = null,
    onClick: (() -> Unit)? = null
) {
    val bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
    val bottomSheetBinding = BottomSheetBinding.inflate(LayoutInflater.from(context))

    bottomSheetBinding.apply {
        descriptionTextView.text = text
        sheetImageView.setImageResource(imageResId)
        buttonSheet.setOnClickListener {
            onClick?.invoke()
            bottomSheetDialog.dismiss()
        }
        buttonColorResId?.let { colorResId ->
            buttonSheet.setBackgroundColor(ContextCompat.getColor(context, colorResId))
        }
    }

    bottomSheetDialog.apply {
        setContentView(bottomSheetBinding.root)
        setCancelable(false)
        show()
    }
}