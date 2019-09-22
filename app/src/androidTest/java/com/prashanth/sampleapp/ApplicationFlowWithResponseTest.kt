package com.prashanth.sampleapp

import android.content.Intent
import android.widget.EditText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.prashanth.sampleapp.ui.MainActivity
import com.robotium.solo.Solo
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.apache.commons.io.IOUtils
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ApplicationFlowWithResponseTest {
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
    fun startFlowWithResponse() {
        //Launch app with the MockServer running
        rule.launchActivity(Intent())

        assertTrue(solo.waitForActivity(MainActivity::class.java.simpleName))

        introduceDelay(1000L)

        val editTextUsername = solo.getView(R.id.search_edittext) as EditText

        //search for the text
        solo.enterText(editTextUsername, "sunt")

        solo.pressSoftKeyboardSearchButton()

        introduceDelay(2000L)

        solo.clickOnText("aut")

        introduceDelay(2000L)

        assertTrue(solo.waitForText("Post ID is 1"))

        //click ok on alert dialog
        solo.clickOnText("OK")

        introduceDelay(2000L)

        //verify if delete pop up has come
        solo.clickLongOnText("aut")

        introduceDelay(2000L)

        assertTrue(solo.waitForText("Are you sure you want to delete this post?"))

        //click ok
        solo.clickOnText("OK")

        introduceDelay(2000L)

    }

    @Throws(Exception::class)
    private fun setupServer() {
        server = MockWebServer()
        val dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                if (request.method == "GET" && request.path!!.contains("/posts")) {

                    val response: String
                    try {
                        response =
                            IOUtils.toString(getInstrumentation().context.resources.assets.open("json/response.json"))
                        return MockResponse().setResponseCode(200)
                            .setBody(response)
                    } catch (e: IOException) {
                        Timber.e(e)
                    }

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