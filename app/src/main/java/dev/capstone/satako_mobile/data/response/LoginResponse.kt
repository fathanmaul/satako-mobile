package dev.capstone.satako_mobile.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val dataLogin: DataLogin? = null,

	@field:SerializedName("error_code")
	val errorCode: Int? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class CustomUser(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class DataLogin(

	@field:SerializedName("customUser")
	val customUser: CustomUser? = null,

	@field:SerializedName("token")
	val token: String? = null
)
