package com.github.kyrillosgait.gifnic.data.repositories

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import com.github.kyrillosgait.gifnic.ANSWER_SHOULD_BE_ERROR
import com.github.kyrillosgait.gifnic.ANSWER_SHOULD_BE_SUCCESS
import com.github.kyrillosgait.gifnic.DATA_SHOULD_BE_EQUAL
import com.github.kyrillosgait.gifnic.DATA_SHOULD_NOT_BE_NULL
import com.github.kyrillosgait.gifnic.ERROR_SHOULD_BE_EQUAL
import com.github.kyrillosgait.gifnic.MESSAGE_SHOULD_NOT_BE_NULL
import com.github.kyrillosgait.gifnic.OFFSET
import com.github.kyrillosgait.gifnic.OFFSET_SHOULD_BE_EQUAL
import com.github.kyrillosgait.gifnic.PAGE_SIZE_SHOULD_BE_EQUAL
import com.github.kyrillosgait.gifnic.data.models.Gif
import com.github.kyrillosgait.gifnic.data.models.Pagination
import com.github.kyrillosgait.gifnic.data.remote.Answer
import com.github.kyrillosgait.gifnic.data.remote.Api
import com.github.kyrillosgait.gifnic.data.remote.GENERIC_ERROR
import com.github.kyrillosgait.gifnic.data.remote.GiphyResponse
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.io.IOException
import kotlinx.serialization.UnstableDefault
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import retrofit2.Response

@UnstableDefault
@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GiphyRepositoryTest {

    private val apiMock = mockk<Api>()
    private val repository = GiphyRepository(apiMock)

    @BeforeEach
    fun setup() {
        clearMocks(apiMock)
    }

    @Nested
    inner class GetRandomShould {

        @Test
        fun `call Api#getRandom`() {
            runBlockingTest { repository.getRandom() }
            coVerify(exactly = 1) { apiMock.getRandom() }
        }

        @Test
        fun `return Answer#Success when api call is successful (2xx)`() {
            val fakeGif = Gif()

            // Return 200 with a fake GIF
            coEvery { apiMock.getRandom() } returns Response.success(
                200,
                GiphyResponse(data = fakeGif)
            )

            runBlockingTest {
                val answer = repository.getRandom()
                assertTrue(answer is Answer.Success, ANSWER_SHOULD_BE_SUCCESS)

                answer as Answer.Success // Answer was successful
                assertNotNull(answer.value, DATA_SHOULD_NOT_BE_NULL)
                assertEquals(fakeGif, answer.value, DATA_SHOULD_BE_EQUAL)
            }

            coVerify(exactly = 1) { apiMock.getRandom() }
        }

        @Test
        fun `return Answer#Error with an error if an unexpected error occurs`() {
            coEvery { apiMock.getRandom() } throws IOException()

            runBlockingTest {
                val answer = repository.getRandom()
                assertTrue(answer is Answer.Error, ANSWER_SHOULD_BE_ERROR)

                answer as Answer.Error // result is error
                assertNotNull(answer.error, MESSAGE_SHOULD_NOT_BE_NULL)
                assertEquals(answer.error, GENERIC_ERROR, ERROR_SHOULD_BE_EQUAL)
            }

            coVerify(exactly = 1) { apiMock.getRandom() }
        }
    }

    @Nested
    inner class GetTrendingPaginatedShould {

        @Test
        fun `call Api#getTrending`() {
            runBlockingTest { repository.getTrendingPaginated(offset = OFFSET.toInt()) }
            coVerify(exactly = 1) { apiMock.getTrending(offset = OFFSET) }
        }

        @Test
        fun `return Answer#Success when api call is successful (2xx)`() {
            val fakeGifs = listOf(
                Gif(),
                Gif()
            )

            // Return 200 with fake GIFs
            coEvery { apiMock.getTrending(offset = OFFSET) } returns Response.success(
                200,
                GiphyResponse(
                    data = fakeGifs,
                    pagination = Pagination(offset = OFFSET.toInt(), count = PAGE_SIZE)
                )
            )

            runBlockingTest {
                val answer = repository.getTrendingPaginated(OFFSET.toInt())
                assertTrue(answer is Answer.Success, ANSWER_SHOULD_BE_SUCCESS)

                answer as Answer.Success // Answer was successful
                assertNotNull(answer.value.data, DATA_SHOULD_NOT_BE_NULL)
                assertEquals(fakeGifs, answer.value.data, DATA_SHOULD_BE_EQUAL)
                assertEquals(OFFSET.toInt(), answer.value.pagination.offset, OFFSET_SHOULD_BE_EQUAL)
                assertEquals(PAGE_SIZE, answer.value.pagination.count, PAGE_SIZE_SHOULD_BE_EQUAL)
            }

            coVerify(exactly = 1) { apiMock.getTrending(offset = OFFSET) }
        }

        @Test
        fun `return Answer#Error with an error if an unexpected error occurs`() {
            coEvery { apiMock.getTrending(offset = OFFSET) } throws IOException()

            runBlockingTest {
                val answer = repository.getTrendingPaginated(OFFSET.toInt())
                assertTrue(answer is Answer.Error, ANSWER_SHOULD_BE_ERROR)

                answer as Answer.Error // result is error
                assertNotNull(answer.error, MESSAGE_SHOULD_NOT_BE_NULL)
                assertEquals(answer.error, GENERIC_ERROR, ERROR_SHOULD_BE_EQUAL)
            }

            coVerify(exactly = 1) { apiMock.getTrending(offset = OFFSET) }
        }
    }
}