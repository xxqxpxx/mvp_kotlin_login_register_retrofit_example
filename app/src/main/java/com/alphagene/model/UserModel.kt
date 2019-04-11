package com.alphagene.model

class UserModel(name: String, passwd: String) : IUser {
    override var name: String = name
    override var passwd: String = passwd

    override fun checkUserValidity(name: String, passwd: String): Boolean {
        return !(this.name != name || this.passwd != passwd)
    }
}