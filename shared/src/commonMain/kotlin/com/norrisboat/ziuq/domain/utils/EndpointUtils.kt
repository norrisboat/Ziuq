package com.norrisboat.ziuq.domain.utils

object Endpoint {

    fun makeEndpoint(path: Path, vararg parameters: String): String {
        var endpoint = path.value
        parameters.forEach { param ->
            endpoint = endpoint.replaceFirst("%", param)
        }
        return endpoint
    }

    enum class Path(val value: String) {
        LOGIN("/v1/auth/login"),
        REGISTER("/v1/auth/register"),
    }

}