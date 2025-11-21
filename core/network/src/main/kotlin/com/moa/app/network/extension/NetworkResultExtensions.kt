package com.moa.app.network.extension

import com.moa.app.network.model.NetworkResult

/**
 * NetworkResult를 Result로 변환하는 확장 함수
 */
fun <T, R> NetworkResult<T>.toResult(mapper: (T) -> R): Result<R> {
    return when (this) {
        is NetworkResult.Success -> Result.success(mapper(this.data))
        is NetworkResult.Error -> Result.failure(Exception(this.message))
        is NetworkResult.Exception -> Result.failure(this.e)
    }
}

/**
 * Unit 타입의 NetworkResult를 Result<Unit>으로 변환하는 확장 함수
 */
fun NetworkResult<Unit>.toResult(): Result<Unit> {
    return toResult { Unit }
}
