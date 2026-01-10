package com.moa.app.network.adapter

import org.assertj.core.api.Assertions.assertThat
import com.moa.app.network.model.BaseResponse
import com.moa.app.network.model.NetworkResult
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Nested
import retrofit2.Response

@DisplayName("ResponseHandler 테스트")
class ResponseHandlerTest {

    private val handler = ResponseHandler<TestData>()

    @Nested
    @DisplayName("성공 케이스")
    inner class SuccessCases {

        @Test
        @DisplayName("HTTP 성공 + business 성공 + data 존재 -> Success 반환")
        fun successWithData() {
            // given
            val mockData = TestData(id = "123", name = "Test")
            val response = Response.success(
                BaseResponse(
                    success = true,
                    message = "ok",
                    data = mockData
                )
            )

            // when
            val result = handler.handle(response)

            // then
            assertThat(result).isInstanceOf(NetworkResult.Success::class.java)

            val success = result as NetworkResult.Success
            assertThat(success.data.id).isEqualTo("123")
            assertThat(success.data.name).isEqualTo("Test")
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
                BaseResponse<TestData>(
                    success = false,
                    message = "Invalid request",
                    data = null
                )
            )

            // when
            val result = handler.handle(response)

            // then
            assertThat(result).isInstanceOf(NetworkResult.Error::class.java)

            val error = result as NetworkResult.Error
            assertThat(error.code).isEqualTo(200)
            assertThat(error.message).isEqualTo("Invalid request")
        }

        @Test
        @DisplayName("HTTP 400 에러 -> Error 반환")
        fun httpBadRequest() {
            // given
            val errorBody = """
                {
                    "success": false,
                    "message": "Bad Request",
                    "data": null
                }
            """.trimIndent().toResponseBody("application/json".toMediaType())

            val response = Response.error<BaseResponse<TestData>>(400, errorBody)

            // when
            val result = handler.handle(response)

            // then
            assertThat(result).isInstanceOf(NetworkResult.Error::class.java)

            val error = result as NetworkResult.Error
            assertThat(error.code).isEqualTo(400)
        }

        @Test
        @DisplayName("HTTP 404 에러 -> Error 반환")
        fun httpNotFound() {
            // given
            val errorBody = "Not Found".toResponseBody("text/plain".toMediaType())
            val response = Response.error<BaseResponse<TestData>>(404, errorBody)

            // when
            val result = handler.handle(response)

            // then
            assertThat(result).isInstanceOf(NetworkResult.Error::class.java)

            val error = result as NetworkResult.Error
            assertThat(error.code).isEqualTo(404)
            assertThat(error.message).isNotEmpty()
        }

        @Test
        @DisplayName("HTTP 500 에러 -> Error 반환")
        fun httpInternalServerError() {
            // given
            val errorBody = "Internal Server Error".toResponseBody("text/plain".toMediaType())
            val response = Response.error<BaseResponse<TestData>>(500, errorBody)

            // when
            val result = handler.handle(response)

            // then
            assertThat(result).isInstanceOf(NetworkResult.Error::class.java)

            val error = result as NetworkResult.Error
            assertThat(error.code).isEqualTo(500)
        }
    }

    @Nested
    @DisplayName("엣지 케이스")
    inner class EdgeCases {

        @Test
        @DisplayName("HTTP 성공이지만 body가 null -> Error 반환")
        fun successButNullBody() {
            // given
            val response = Response.success<BaseResponse<TestData>>(null)

            // when
            val result = handler.handle(response)

            // then
            assertThat(result).isInstanceOf(NetworkResult.Error::class.java)
        }
    }

    @Nested
    @DisplayName("타입 안전성 및 Nullable 테스트")
    inner class TypeSafetyTests {

        @Test
        @DisplayName("Nullable 타입(TestData?)에서 data=null 응답 시 Success(null) 반환")
        fun nullableHandlerWithNullDataReturnsSuccess() {
            // given
            val nullableHandler = ResponseHandler<TestData?>()
            val response = Response.success(
                BaseResponse<TestData?>(success = true, message = "ok", data = null)
            )

            // when
            val result = nullableHandler.handle(response)

            // then
            assertThat(result).isInstanceOf(NetworkResult.Success::class.java)
            assertThat((result as NetworkResult.Success).data).isNull()
        }

        @Test
        @DisplayName("비즈니스 로직 실패(success=false) 시에는 반드시 Error 반환")
        fun businessFailureStillReturnsError() {
            // given
            val response = Response.success(
                BaseResponse<TestData>(success = false, message = "서버 에러", data = null)
            )

            // when
            val result = handler.handle(response)

            // then
            assertThat(result).isInstanceOf(NetworkResult.Error::class.java)
            assertThat((result as NetworkResult.Error).message).isEqualTo("서버 에러")
        }
    }
}

data class TestData(
    val id: String,
    val name: String
)

data class User(
    val id: String,
    val name: String,
    val email: String
)
