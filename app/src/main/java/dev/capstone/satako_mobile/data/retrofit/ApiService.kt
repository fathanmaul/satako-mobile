package dev.capstone.satako_mobile.data.retrofit

import dev.capstone.satako_mobile.data.response.HistoryResponse
import dev.capstone.satako_mobile.data.response.LoginResponse
import dev.capstone.satako_mobile.data.response.PredictResponse
import dev.capstone.satako_mobile.data.response.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
//        @Field("confirm password") confirmPassword: String
    ): RegisterResponse

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginResponse

    @Multipart
    @GET("predict/histories")
    suspend fun getHistory(): HistoryResponse

    @Multipart
    @POST("predict")
    suspend fun predict(
        @Part file: MultipartBody.Part,
    ): PredictResponse
}