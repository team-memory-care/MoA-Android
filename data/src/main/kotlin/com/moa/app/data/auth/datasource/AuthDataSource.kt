package com.moa.app.data.auth.datasource

import com.moa.app.data.auth.model.request.PhoneAuthCodeRequest

interface AuthDataSource {
    suspend fun requestPhoneAuthCode(request: PhoneAuthCodeRequest): Result<Unit>
}
