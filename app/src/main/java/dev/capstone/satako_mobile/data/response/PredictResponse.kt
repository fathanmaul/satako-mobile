package dev.capstone.satako_mobile.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class PredictResponse(

	@field:SerializedName("data")
	val dataPredict: DataPredict,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

@Parcelize
data class DataPredict(

	@field:SerializedName("disease")
	val disease: String? = null,

	@field:SerializedName("solutions")
	val solutions: String? = null,

	@field:SerializedName("causes")
	val causes: String? = null,

	@field:SerializedName("description")
	val description: String? = null
): Parcelable
