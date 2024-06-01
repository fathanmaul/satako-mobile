package dev.capstone.satako_mobile.data.response

import com.google.gson.annotations.SerializedName

data class PredictResponse(

    @field:SerializedName("data")
	val data: Data? = null,

    @field:SerializedName("error")
	val error: Boolean? = null,

    @field:SerializedName("message")
	val message: String? = null
)

data class Data(

	@field:SerializedName("result")
	val result: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("suggestion")
	val suggestion: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
