package com.moa.app.network.adapter

import com.moa.app.network.model.BaseResponse
import com.moa.app.network.model.NetworkResult
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.Response

@DisplayName("UnitResponseHandler 테스트")
class UnitResponseHandlerTest {

    private val handler = UnitResponseHandler()

    @Nested
    @DisplayName("성공 케이스")
    inner class SuccessCases {

        @Test
        @DisplayName("HTTP 성공 + business 성공 + data null -> Success(Unit) 반환")
        fun successWithNullData() {
            // given
            val response = Response.success(
                BaseResponse<Unit>(
                    success = true,
                    message = "ok",
                    data = null
                )
            )

            // when
            val result = handler.handle(response)

            // then
            assertThat(result).isInstanceOf(NetworkResult.Success::class.java)

            val success = result as NetworkResult.Success
            assertThat(success.data).isEqualTo(Unit)
        }

        @Test
        @DisplayName("HTTP 성공 + business 성공 + data Unit -> Success(Unit) 반환")
        fun successWithUnitData() {
            // given
            val response = Response.success(
                BaseResponse(
                    success = true,
                    message = "ok",
                    data = Unit
                )
            )

            // when
            val result = handler.handle(response)

            // then
            assertThat(result).isInstanceOf(NetworkResult.Success::class.java)

            val success = result as NetworkResult.Success
            assertThat(success.data).isEqualTo(Unit)
        }
    }

    @Nested
    @DisplayName("에러 케이스")
    inner class ErrorCases {

        @Test
        @DisplayName("HTTP 성공 + business 실패 -> Error 반환")
        fun businessFailure() {
            // given
            val response = Response.success(
                BaseResponse<Unit>(
                    success = false,
                    message = "Operation failed",
                    data = null
                )
            )

            // when
            val result = handler.handle(response)

            // then
            assertThat(result).isInstanceOf(NetworkResult.Error::class.java)

            val error = result as NetworkResult.Error
            assertThat(error.code).isEqualTo(200)
            assertThat(error.message).isEqualTo("Operation failed")
        }
    }
}
