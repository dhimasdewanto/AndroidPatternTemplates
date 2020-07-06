package com.dhimasdewanto.androidpatterntemplates.features.logic.use_cases

import com.dhimasdewanto.androidpatterntemplates.core.error_handler.Err
import com.dhimasdewanto.androidpatterntemplates.core.error_handler.Ok
import com.dhimasdewanto.androidpatterntemplates.core.error_handler.Res
import com.dhimasdewanto.androidpatterntemplates.core.use_cases.UseCase
import com.dhimasdewanto.androidpatterntemplates.features.logic.failures.Failures
import com.dhimasdewanto.androidpatterntemplates.features.logic.models.UserModel
import com.dhimasdewanto.androidpatterntemplates.features.logic.repositories.UserRepo

class GetListUsers(
    private val userRepo: UserRepo
) : UseCase<List<UserModel>, GetListUsersParams, Failures> {
    override suspend fun execute(params: GetListUsersParams): Res<List<UserModel>, Failures> {
        return userRepo.getListUsers()
    }

    override suspend fun validate(params: GetListUsersParams): Res<Unit, Failures> {
        val random = (0..params.maxRandomNumber).random()

        if (random == 1) return Err(Failures.SomeFailure)
        if (random == 2) return Err(Failures.ExampleFailure)

        return Ok(Unit)
    }
}

data class GetListUsersParams(
    val maxRandomNumber: Int
)