package com.norrisboat.ziuq.domain.utils

import com.norrisboat.ziuq.data.remote.result.BaseResult
import com.norrisboat.ziuq.data.remote.result.NetworkError
import com.norrisboat.ziuq.domain.utils.Endpoint.makeEndpoint
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

const val baseURL = "http://192.168.0.181:9000/"

fun HttpRequestBuilder.apiUrl(path: String) {
    url {
        takeFrom(baseURL)
        encodedPath = path
    }
}

suspend inline fun <reified T : Any> HttpResponse.getResults(): Result<T> {
    return try {
        if (this.status.isSuccess()) {
            Result.success(this.body())
        } else {
            Result.failure(
                Exception(
                    this.body<NetworkError?>()?.message ?: "Some error occurred"
                )
            )
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}

inline fun <reified T : Any> BaseResult<T?>.getResults(): Result<T> {
    return try {
        if (this.success == true && this.data != null) {
            Result.success(this.data)
        } else {
            Result.failure(
                Exception(this.message ?: "Some error occurred")
            )
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}


/**
 * Creates a MutableSharedFlow with default values to replicate a single event not repeatable
 */
@Suppress("FunctionName")
fun <T> SingleEventFlow(): MutableSharedFlow<T> {
    return MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
}

val WhileViewSubscribed: SharingStarted = SharingStarted.WhileSubscribed(5000L)

fun currentDate(): String {
    val instantNow = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
    return instantNow.toString()
}

suspend fun HttpClient.makePostRequest(
    request: Any? = null,
    endPointPath: Endpoint.Path,
    vararg parameters: String
) = this.post {
    apiUrl(endPointPath.makeEndpoint(*parameters))
    contentType(ContentType.Application.Json)
    if (request != null) {
        setBody(request)
    }
}

suspend fun HttpClient.makeGetRequest(
    request: Any? = null,
    endPointPath: Endpoint.Path,
    vararg parameters: String
) = this.get {
    apiUrl(endPointPath.makeEndpoint(*parameters))
    contentType(ContentType.Application.Json)
    if (request != null) {
        setBody(request)
    }
}