package com.alphagene.model.responseModels

class LoginResponseModel{

    /**
     * id : 1
     * firstName : John
     * lastName : Wick
     * type : client
     * validated : true
     * userCredentials : {"id":1,"phoneNumber":"01095595744","email":"boogy@man.com"}
     * userAddress : [{"id":1,"city":"Cairo","street":"Helmy Street","building":"18","floor":"4","apartment":"42"},{"id":2,"city":"Alexandria","street":"Adaweya Street","building":"10","floor":"6","apartment":"63"}]
     */

    private var id: Int = 0
    private var firstName: String? = null
    private var lastName: String? = null
    private var type: String? = null
    private var validated: Boolean = false
    private var userCredentials: UserCredentialsBean? = null
    private var userAddress: List<UserAddressBean>? = null

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getFirstName(): String? {
        return firstName
    }

    fun setFirstName(firstName: String) {
        this.firstName = firstName
    }

    fun getLastName(): String? {
        return lastName
    }

    fun setLastName(lastName: String) {
        this.lastName = lastName
    }

    fun getType(): String? {
        return type
    }

    fun setType(type: String) {
        this.type = type
    }

    fun isValidated(): Boolean {
        return validated
    }

    fun setValidated(validated: Boolean) {
        this.validated = validated
    }

    fun getUserCredentials(): UserCredentialsBean? {
        return userCredentials
    }

    fun setUserCredentials(userCredentials: UserCredentialsBean) {
        this.userCredentials = userCredentials
    }

    fun getUserAddress(): List<UserAddressBean>? {
        return userAddress
    }

    fun setUserAddress(userAddress: List<UserAddressBean>) {
        this.userAddress = userAddress
    }

    class UserCredentialsBean {
        /**
         * id : 1
         * phoneNumber : 01095595744
         * email : boogy@man.com
         */

        var id: Int = 0
        var phoneNumber: String? = null
        var email: String? = null
    }

    class UserAddressBean {
        /**
         * id : 1
         * city : Cairo
         * street : Helmy Street
         * building : 18
         * floor : 4
         * apartment : 42
         */

        var id: Int = 0
        var city: String? = null
        var street: String? = null
        var building: String? = null
        var floor: String? = null
        var apartment: String? = null
    }

}