package com.github.kyrillosgait.gifnic.data.remote

import com.github.kyrillosgait.gifnic.ANSWER_SHOULD_BE_ERROR
import com.github.kyrillosgait.gifnic.ANSWER_SHOULD_BE_SUCCESS
import com.github.kyrillosgait.gifnic.DATA_SHOULD_BE_EQUAL
import com.github.kyrillosgait.gifnic.DATA_SHOULD_NOT_BE_NULL
import com.github.kyrillosgait.gifnic.ERROR_SHOULD_BE_EQUAL
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import retrofit2.Response

class RequestTest<T> {

    private val response = mockk<Response<GiphyResponse<T>>>()

    fun apiRequest(): Response<GiphyResponse<T>> = response
    fun throwsExceptionApiRequest(): Response<GiphyResponse<T>> = throw Exception()

    @BeforeEach
    fun setUp() {
        clearMocks(response)
    }

    @Nested
    inner class RequestAnswerShould {

        @Test
        fun `return Answer#Success with data if the body is not null`() {
            val data = Any() as T

            every { response.isSuccessful } returns true
            every { response.body()?.data } returns data

            val answer = requestAnswer { apiRequest() }
            assertTrue(answer is Answer.Success<*, *>, ANSWER_SHOULD_BE_SUCCESS)

            answer as Answer.Success // Request was successful
            assertNotNull(answer.value, DATA_SHOULD_NOT_BE_NULL)
            assertEquals(answer.value, data, DATA_SHOULD_BE_EQUAL)
        }

        @Test
        fun `return Answer#Error with an error if response data are null`() {
            every { response.isSuccessful } returns true
            every { response.body()?.data } returns null

            val answer = requestAnswer { apiRequest() }
            assertTrue(answer is Answer.Error<*, *>, ANSWER_SHOULD_BE_ERROR)

            answer as Answer.Error // Request was successful but body was null
            assertEquals(answer.error, GENERIC_ERROR, ERROR_SHOULD_BE_EQUAL)
        }

        @Test
        fun `return Answer#Error if response is not successful`() {
            every { response.isSuccessful } returns false

            val answer = requestAnswer { apiRequest() }
            assertTrue(answer is Answer.Error<*, *>, ANSWER_SHOULD_BE_ERROR)

            answer as Answer.Error // Request was not successful
            assertEquals(answer.error, GENERIC_ERROR, ERROR_SHOULD_BE_EQUAL)
        }

        @Test
        fun `return Answer#Error with an error if an exceptions was thrown`() {
            val answer = requestAnswer { throwsExceptionApiRequest() }
            assertTrue(answer is Answer.Error<*, *>, ANSWER_SHOULD_BE_ERROR)

            answer as Answer.Error // An exception was thrown
            assertEquals(answer.error, GENERIC_ERROR, ERROR_SHOULD_BE_EQUAL)
        }
    }

    @Nested
    inner class RequestAnswerPaginatedShould {

        @Test
        fun `return Answer#Success with data if the body is not null`() {
            val data = Any() as T

            every { response.isSuccessful } returns true
            every { response.body()?.data } returns data

            val answer = requestAnswer { apiRequest() }
            assertTrue(answer is Answer.Success<*, *>, ANSWER_SHOULD_BE_SUCCESS)

            answer as Answer.Success // Request was successful
            assertNotNull(answer.value, DATA_SHOULD_NOT_BE_NULL)
            assertEquals(answer.value, data, DATA_SHOULD_BE_EQUAL)
        }

        @Test
        fun `return Answer#Error with an error if response data are null`() {
            every { response.isSuccessful } returns true
            every { response.body()?.data } returns null

            val answer = requestAnswer { apiRequest() }
            assertTrue(answer is Answer.Error<*, *>, ANSWER_SHOULD_BE_ERROR)

            answer as Answer.Error // Request was successful but body was null
            assertEquals(answer.error, GENERIC_ERROR, ERROR_SHOULD_BE_EQUAL)
        }

        @Test
        fun `return Answer#Error if response is not successful`() {
            every { response.isSuccessful } returns false

            val answer = requestAnswer { apiRequest() }
            assertTrue(answer is Answer.Error<*, *>, ANSWER_SHOULD_BE_ERROR)

            answer as Answer.Error // Request was not successful
            assertEquals(answer.error, GENERIC_ERROR, ERROR_SHOULD_BE_EQUAL)
        }

        @Test
        fun `return Answer#Error with an error if an exceptions was thrown`() {
            val answer = requestAnswer { throwsExceptionApiRequest() }
            assertTrue(answer is Answer.Error<*, *>, ANSWER_SHOULD_BE_ERROR)

            answer as Answer.Error // An exception was thrown
            assertEquals(answer.error, GENERIC_ERROR, ERROR_SHOULD_BE_EQUAL)
        }
    }
}
