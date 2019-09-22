package com.prashanth.sampleapp

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.prashanth.sampleapp.ui.MainActivity
import com.robotium.solo.Solo
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ApplicationFlowWithoutResponseTest {
    private lateinit var server: MockWebServer

    private lateinit var solo: Solo

    @Rule
    @JvmField
    var rule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    @Throws(Exception::class)
    fun setUp() {
        setupServer()
        solo = Solo(getInstrumentation(), rule.activity)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        solo.finishOpenedActivities()
        server.shutdown()
    }

    @Test
    fun startFlowWithoutResponse() {
        //Launch app with the MockServer running
        rule.launchActivity(Intent())

        assertTrue(solo.waitForActivity(MainActivity::class.java.simpleName))

        introduceDelay(500L)

        assertTrue(solo.waitForText("Our servers seem to be busy, please try again later"))

    }

    @Throws(Exception::class)
    private fun setupServer() {
        server = MockWebServer()
        val dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                if (request.method == "GET" && request.path!!.contains("/posts")) {
                    return MockResponse().setResponseCode(500)
                        .setBody("")
                }
                return MockResponse().setResponseCode(200).setBody("")
            }
        }
        server.dispatcher = dispatcher
        server.start(8080)
    }

    private fun introduceDelay(timeout: Long) {
        Thread.sleep(timeout)
    }
}