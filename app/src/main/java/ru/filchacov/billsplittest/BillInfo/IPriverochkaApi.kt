package ru.filchacov.billsplittest.BillInfo
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.filchacov.billsplittest.Bill

interface IProverochkaApi {
    @Headers(
        "Accept: application/json, text/javascript, */*; q=0.01"
        , "Referer: https://proverkacheka.com/"
        , "Sec-Fetch-Dest: empty"
        , "X-Requested-With: XMLHttpRequest"
        , "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36"
        , "Content-Type: application/x-www-form-urlencoded; charset=UTF-8"
    )
    @FormUrlEncoded
    @POST("/check/get")
    fun checkGet(@Field("fn") fn: String
                 , @Field("fd") fd: String
                 , @Field("fp") fp: String
                 , @Field("n") n: Int?
                 , @Field("s") s: String
                 , @Field("t") t: String
                 , @Field("qr") qr: Int
    ):Call<ApiResponse<Bill>>
}