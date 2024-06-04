package dev.capstone.satako_mobile.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("loginResult")
	val loginResult: LoginResult,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class LoginResult(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("userUID")
	val userUID: String,

	@field:SerializedName("token")
	val token: String
)
