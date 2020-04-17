package ru.filchacov.billsplittest.BillInfo

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import ru.filchacov.billsplittest.Bill.Bill

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseDeserializer: JsonDeserializer<ApiResponse<*>> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ApiResponse<*> {
        val realType = (typeOfT as ParameterizedType).actualTypeArguments[0]
        val tmp = json.asJsonObject
        val jsonCode = tmp.get("code").asInt

        return when (jsonCode) {
            1 -> createResponse(
                realType,
                jsonCode,
                data = context.deserialize<Bill>(tmp.get("data").asJsonObject.get("json"), realType)
            )
            else -> createResponse(realType, jsonCode, error = tmp.get("data").asString)
        }
    }

    protected fun <T> createResponse(realType: T, code: Int, data: T? = null, error: String? = null): ApiResponse<T> {
        return ApiResponse<T>().apply {
            this.code = code
            this.data = data
            this.error = error
        }
    }
}