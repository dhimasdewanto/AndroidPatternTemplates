package com.dhimasdewanto.androidpatterntemplates.features.logic.repositories

import com.dhimasdewanto.androidpatterntemplates.features.logic.models.UserModel
import com.dhimasdewanto.androidpatterntemplates.features.logic.services.JsonPlaceholderService

class UserRepo(
    private val service: JsonPlaceholderService
) {
    suspend fun getListUsers(): List<UserModel> {
        return service.getListUsers()
    }
}