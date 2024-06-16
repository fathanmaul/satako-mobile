package dev.capstone.satako_mobile.data.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("error_code")
	val errorCode: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)

