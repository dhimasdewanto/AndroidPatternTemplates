package com.dhimasdewanto.androidpatterntemplates.features.logic.repositories

import com.dhimasdewanto.androidpatterntemplates.core.error_handler.Err
import com.dhimasdewanto.androidpatterntemplates.core.error_handler.Ok
import com.dhimasdewanto.androidpatterntemplates.core.error_handler.Res
import com.dhimasdewanto.androidpatterntemplates.features.logic.failures.Failures
import com.dhimasdewanto.androidpatterntemplates.features.logic.models.UserModel
import com.dhimasdewanto.androidpatterntemplates.features.logic.services.JsonPlaceholderService

class UserRepo(
    private val service: JsonPlaceholderService
) {
    suspend fun getListUsers(): Res<List<UserModel>, Failures> {
        val random = (0..5).random()

        if (random == 1) return Err(Failures.SomeFailure)
        if (random == 2) return Err(Failures.ExampleFailure)

        return Ok(service.getListUsers())
    }
}