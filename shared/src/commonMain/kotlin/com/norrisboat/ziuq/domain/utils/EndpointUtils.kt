package com.norrisboat.ziuq.domain.utils

object Endpoint {

    fun Path.makeEndpoint(vararg parameters: String): String {
        var endpoint = this.value
        parameters.forEach { param ->
            endpoint = endpoint.replaceFirst("%", param)
        }
        return endpoint
    }

    enum class Path(val value: String) {
        LOGIN("/v1/auth/login"),
        REGISTER("/v1/auth/register"),
        QUIZ_SETUP("/v1/quiz/setup"),
        CREATE_QUIZ("/v1/quiz/create/%"),
        CREATE_LIVE_QUIZ("/v1/quiz/live/quiz/create/%"),
        JOIN_LIVE_QUIZ("/v1/quiz/live/quiz/join/%"),
    }

}