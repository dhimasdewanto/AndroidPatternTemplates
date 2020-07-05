package com.dhimasdewanto.androidpatterntemplates.features.logic.models

data class AddressModel(
    val city: String,
    val geo: GeoModel,
    val street: String,
    val suite: String,
    val zipcode: String
)