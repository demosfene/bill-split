package ru.filchacov.billsplittest.BillInfo

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BillService {

    val gson = GsonBuilder().apply {
        registerTypeAdapter(ApiResponse::class.java,
            ApiResponseDeserializer()
        )
    }.create()
    val converterFactory = GsonConverterFactory.create(gson)


    val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder().apply {
        addNetworkInterceptor(interceptor)
    }.build()

    val retrofit = Retrofit.Builder().apply {
        baseUrl("https://proverkacheka.com/")
        client(client)
        addConverterFactory(converterFactory)
        addCallAdapterFactory(CoroutineCallAdapterFactory())
    }.build()

    val api = retrofit.create(IProverochkaApi::class.java)

    val s = "sss"
}

