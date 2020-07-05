package com.dhimasdewanto.androidpatterntemplates.features.logic.models

data class UserModel(
    val address: AddressModel,
    val company: CompanyModel,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)