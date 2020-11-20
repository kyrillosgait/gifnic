package com.github.kyrillosgait.gifnic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterAllCallback
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeAllCallback
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.TestInstancePostProcessor

@ExperimentalCoroutinesApi
@ExtendWith(TestCoroutineExtension::class)
interface CoroutineTest {
    var testScope: TestCoroutineScope
    var dispatcher: TestCoroutineDispatcher
}

/**
 * JUnit 5 Extension for automatically creating a [TestCoroutineDispatcher],
 * then a [TestCoroutineScope] with the same CoroutineContext.
 *
 * [TestCoroutineScope.cleanupTestCoroutines] is called in afterEach
 * instead of afterAll in case Lifecycle.PER_CLASS is selected,
 * and will cause an exception if any coroutines are leaked.
 *
 * Usage of an extension in a Kotlin JUnit 5 test:
 *
 * class MyTest : CoroutineTest {
 *
 *   override lateinit var testScope: TestCoroutineScope
 *   override lateinit var dispatcher: TestCoroutineDispatcher
 * }
 *
 */
@ExperimentalCoroutinesApi
open class TestCoroutineExtension :
    TestInstancePostProcessor,
    BeforeAllCallback,
    AfterEachCallback,
    AfterAllCallback {

    val dispatcher = TestCoroutineDispatcher()
    val testScope = TestCoroutineScope(dispatcher)

    override fun postProcessTestInstance(testInstance: Any?, context: ExtensionContext?) {
        (testInstance as? CoroutineTest)?.let { coroutineTest ->
            coroutineTest.testScope = testScope
            coroutineTest.dispatcher = dispatcher
        }
    }

    override fun beforeAll(context: ExtensionContext?) {
        Dispatchers.setMain(dispatcher)
    }

    override fun afterEach(context: ExtensionContext?) {
        testScope.cleanupTestCoroutines()
    }

    override fun afterAll(context: ExtensionContext?) {
        Dispatchers.resetMain()
    }
}
