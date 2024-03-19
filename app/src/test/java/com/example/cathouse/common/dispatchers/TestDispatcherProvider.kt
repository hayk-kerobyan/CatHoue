package com.example.cathouse.common.dispatchers

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class TestDispatcherProvider : DispatcherProvider {

    private val testDispatcher = UnconfinedTestDispatcher()

    override val Main: CoroutineDispatcher
        get() = testDispatcher

    override val IO: CoroutineDispatcher
        get() = testDispatcher

    override val Default: CoroutineDispatcher
        get() = testDispatcher

}