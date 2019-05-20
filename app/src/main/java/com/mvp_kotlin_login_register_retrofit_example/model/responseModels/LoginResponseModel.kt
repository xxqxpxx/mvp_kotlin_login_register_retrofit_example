package com.mvp_kotlin_login_register_retrofit_example.model.responseModels

data class LoginResponseModel (
    var id: Int = 0,
    var firstName: String ,
    var lastName: String ,
    var sessionId: String ,
    var type: String,
    var validated: Boolean ,
    var phoneNumber: String,
    var email: String ,
    var addresses: List<UserAddressBean> ,
    var identificationToken: String
) {
    /**
     * id : 1
     * firstName : John
     * lastName : Wick
     * type : client
     * validated : true
     * userCredentials : {"id":1,"phoneNumber":"01095595744","email":"boogy@man.com"}
     * userAddress : [{"id":1,"city":"Cairo","street":"Helmy Street","building":"18","floor":"4","apartment":"42"},{"id":2,"city":"Alexandria","street":"Adaweya Street","building":"10","floor":"6","apartment":"63"}]
     */

    data class UserAddressBean (  var id: Int  , var city: String, var street: String, var building: String, var floor: String, var apartment: String) {
        /**
         * id : 1
         * city : Cairo
         * street : Helmy Street
         * building : 18
         * floor : 4
         * apartment : 42
         */
    }

}