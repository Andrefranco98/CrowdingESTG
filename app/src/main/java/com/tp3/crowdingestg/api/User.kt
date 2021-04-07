package com.tp3.crowdingestg.api

data class problemas(
    val id: Int,
    val descr: String,
    val latitude: String,
    val longitude: String,
    val user_id: Int
)