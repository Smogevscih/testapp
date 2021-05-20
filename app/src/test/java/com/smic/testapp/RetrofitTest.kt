package com.smic.testapp

import com.google.common.truth.Truth
import com.smic.testapp.network.RequestGit
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Test

class RetrofitTest {

    private val TEST_NAME = "ann"
    private val TEST_PER_PAGE = 30
    private val TEST_PAGE = 1


    @Test
    fun test_retrofit_quest() {
        val request: RecordedRequest = takeMockRequest {
            searchUsers(TEST_NAME, TEST_PER_PAGE, TEST_PAGE)
                .subscribe({},{})
        }

        Truth.assertThat(request.method).isEqualTo("GET")
        Truth.assertThat(request.path).isEqualTo("/search/users?q=ann&per_page=30&page=1")

    }

}


private fun takeMockRequest(sut: RequestGit.() -> Unit): RecordedRequest {
    return MockWebServer()
        .use {
            it.enqueue(MockResponse())
            it.start()  //start Server

            val testApp = TestApp()
            val url = it.url("/")
            testApp.initRetrofitForTest(url)
            val requestGit = TestApp.requestApi

            sut(requestGit)

            it.takeRequest()
        }
}
