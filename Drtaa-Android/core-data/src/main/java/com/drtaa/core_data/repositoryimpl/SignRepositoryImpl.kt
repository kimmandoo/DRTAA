package com.drtaa.core_data.repositoryimpl

import com.drtaa.core_data.datasource.SignDataSource
import com.drtaa.core_data.repository.SignRepository
import com.drtaa.core_data.util.FormDataConverterUtil
import com.drtaa.core_data.util.ResultWrapper
import com.drtaa.core_data.util.safeApiCall
import com.drtaa.core_model.data.Tokens
import com.drtaa.core_model.network.RequestSignUp
import com.drtaa.core_model.sign.SocialUser
import com.drtaa.core_model.sign.UserLoginInfo
import com.drtaa.core_model.util.toSocialUser
import com.drtaa.core_model.util.toTokens
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class SignRepositoryImpl @Inject constructor(
    private val signDataSource: SignDataSource
) : SignRepository {

    override suspend fun setFCMToken(fcmToken: String): Flow<Result<String>> = flow {
        when (val result = safeApiCall { signDataSource.setFCMToken(fcmToken) }) {
            is ResultWrapper.Success -> {
                emit(Result.success(result.data))
            }

            is ResultWrapper.GenericError -> {
                emit(Result.failure(Exception(result.message)))
            }

            is ResultWrapper.NetworkError -> {
                emit(Result.failure(Exception("네트워크 에러")))
            }
        }
    }

    override suspend fun getTokens(userLoginInfo: UserLoginInfo): Flow<Result<Tokens>> = flow {
        when (
            val response = safeApiCall { signDataSource.getTokens(userLoginInfo) }
        ) {
            is ResultWrapper.Success -> {
                emit(Result.success(response.data.toTokens()))
                Timber.d("성공")
            }

            is ResultWrapper.GenericError -> {
                emit(Result.failure(Exception(response.message)))
                Timber.d("실패")
            }

            is ResultWrapper.NetworkError -> {
                emit(Result.failure(Exception("네트워크 에러")))
                Timber.d("네트워크 에러")
            }
        }
    }

    override suspend fun signUp(
        requestSignUp: RequestSignUp,
        image: File?
    ): Flow<Result<String>> = flow {
        when (
            val response = safeApiCall {
                val requestPart = FormDataConverterUtil.getJsonRequestBody(requestSignUp)
                val filePart: MultipartBody.Part? =
                    FormDataConverterUtil.getNullableMultiPartBody("image", image)
                signDataSource.signUp(requestPart, filePart)
            }
        ) {
            is ResultWrapper.Success -> {
                emit(Result.success(response.data))
                Timber.d("성공")
            }

            is ResultWrapper.GenericError -> {
                emit(Result.failure(Exception(response.message)))
                Timber.d("실패")
            }

            is ResultWrapper.NetworkError -> {
                emit(Result.failure(Exception("네트워크 에러")))
                Timber.d("네트워크 에러")
            }
        }
    }

    override suspend fun checkDuplicatedId(userProviderId: String): Flow<Result<Boolean>> = flow {
        when (
            val response = safeApiCall {
                signDataSource.checkDuplicatedId(userProviderId)
            }
        ) {
            is ResultWrapper.Success -> {
                emit(Result.success(response.data))
                Timber.d("성공")
            }

            is ResultWrapper.GenericError -> {
                emit(Result.failure(Exception(response.message)))
                Timber.d("실패")
            }

            is ResultWrapper.NetworkError -> {
                emit(Result.failure(Exception("네트워크 에러")))
                Timber.d("네트워크 에러")
            }
        }
    }

    override suspend fun getUserData(): Flow<Result<SocialUser>> = flow {
        val user = signDataSource.getUserData().first()
        if (user.isValid()) {
            emit(Result.success(user))
        } else {
            emit(Result.failure(Exception("유저 정보가 없습니다.")))
        }
    }

    override suspend fun setUserData(user: SocialUser) {
        signDataSource.setUserData(user)
    }

    override suspend fun clearUserData() {
        signDataSource.clearUserData()
    }

    override suspend fun updateProfileImage(image: File?): Flow<Result<SocialUser>> = flow {
        when (
            val response = safeApiCall {
                val filePart: MultipartBody.Part? =
                    FormDataConverterUtil.getNullableMultiPartBody("image", image)
                signDataSource.updateUserProfileImage(filePart)
            }
        ) {
            is ResultWrapper.Success -> {
                emit(Result.success(response.data))
            }

            is ResultWrapper.GenericError -> {
                emit(Result.failure(Exception(response.message)))
            }

            is ResultWrapper.NetworkError -> {
                emit(Result.failure(Exception("네트워크 에러")))
            }
        }
    }

    override suspend fun getUserInfo(): Flow<Result<SocialUser>> = flow {
        when (
            val response = safeApiCall { signDataSource.getUserInfo() }
        ) {
            is ResultWrapper.Success -> {
                Timber.d("서버에서 사용자 정보 불러오기 성공")
                emit(Result.success(response.data.toSocialUser()))
            }

            is ResultWrapper.GenericError -> {
                emit(Result.failure(Exception(response.message)))
                Timber.d("서버에서 사용자 정보 불러오기 실패")
            }

            is ResultWrapper.NetworkError -> {
                emit(Result.failure(Exception("네트워크 에러")))
                Timber.d("네트워크 에러")
            }
        }
    }
}