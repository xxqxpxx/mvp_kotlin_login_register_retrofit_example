package com.alphagene.model

class UserModel(name: String, passwd: String) : IUser {
    override var name: String = name
    override var passwd: String = name

    init {
        this.name = name
        this.passwd = passwd
    }

    override fun checkUserValidity(name: String, passwd: String): Int {
        if (name == null || passwd == null || name != name || passwd != passwd) {
            return -1
        }
        else return  0
    }
}