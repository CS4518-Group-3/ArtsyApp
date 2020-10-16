package com.brycecorbitt.artsyapp.api


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.test.platform.app.InstrumentationRegistry
import com.jraska.livedata.test
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ResponseHandlerTest {
    @get:Rule val testRule = InstantTaskExecutorRule()

    lateinit var apiCaller: ResponseHandler
    lateinit var context: Context

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().context
        apiCaller = ResponseHandler(context)
    }

    @Test
    fun checkIfAuthenticated() {
        var response: LiveData<AuthenticationResponse?> = apiCaller.checkIfAuthenticated()
        response.test()
            .assertValue { it?.authenticated == true }
    }

    @Test
    fun deleteAccount() {
    }

    @Test
    fun signOut() {
    }

    @Test
    fun createPost() {
    }

    @Test
    fun deletePost() {
    }

    @Test
    fun upvote() {
    }

    @Test
    fun downvote() {
    }

    @Test
    fun getPost() {
    }

    @Test
    fun getFeed() {
    }

    @Test
    fun getUserFeed() {
    }
}