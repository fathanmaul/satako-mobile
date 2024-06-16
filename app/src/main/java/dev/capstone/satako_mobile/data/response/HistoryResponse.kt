package dev.capstone.satako_mobile.data.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("data")
	val historyItem: List<HistoryItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class HistoryItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("disease")
	val disease: String? = null,

	@field:SerializedName("solutions")
	val solutions: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("causes")
	val causes: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
