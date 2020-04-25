package com.example.portalgun

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source

// Taken from GithubBrowserSample
fun MockWebServer.enqueueResponse(
    classLoader: ClassLoader,
    fileName: String,
    headers: Map<String, String> = emptyMap()
) {
    val inputStream = classLoader.getResourceAsStream("api-response/$fileName")
    val source = inputStream.source().buffer()
    val mockResponse = MockResponse()
    for ((key, value) in headers) {
        mockResponse.addHeader(key, value)
    }
    enqueue(
        mockResponse
            .setBody(source.readString(Charsets.UTF_8))
    )
}
