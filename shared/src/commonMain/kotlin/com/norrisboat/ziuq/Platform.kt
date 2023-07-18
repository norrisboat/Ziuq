package com.norrisboat.ziuq

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform