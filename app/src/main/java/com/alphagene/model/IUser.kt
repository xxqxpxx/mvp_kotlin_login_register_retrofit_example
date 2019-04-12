package com.alphagene.model

interface IUser {
    val name: String

    val passwd: String

    fun checkUserValidity(name: String, passwd: String): Boolean
}