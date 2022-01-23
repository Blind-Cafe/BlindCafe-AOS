package com.abouttime.blindcafe.domain.use_case.local.database

import com.abouttime.blindcafe.common.Resource
import com.abouttime.blindcafe.data.local.database.entity.MessageEntity
import com.abouttime.blindcafe.domain.repository.LocalMessageRepository
import com.abouttime.blindcafe.presentation.onboarding.login.LoginState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InsertMessageUseCase(
    private val repository: LocalMessageRepository
) {
   operator fun invoke(message: MessageEntity): Flow<Resource<Unit>> = flow {
       emit(Resource.Loading())
       try {
           repository.insertMessage(message)
           emit(Resource.Success(Unit))
       } catch (e: Exception) {
           emit(Resource.Error(message = e.toString()))
       }

   }
}