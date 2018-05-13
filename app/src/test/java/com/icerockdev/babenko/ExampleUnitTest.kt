package com.icerockdev.babenko

import android.content.Context
import android.webkit.URLUtil

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.runners.MockitoJUnitRunner

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {

    @Mock
    internal var mMockContext: Context? = null

    @Test
    @Throws(Exception::class)
    fun addition_isCorrect() {
        assertEquals(4, (2 + 2).toLong())
        //        assertThat(Patterns.WEB_URL.matcher("www.mocky.io/v2/58fa10ce110000b81ad2106c").matches(), is(true)); wow that doesn`t work cus it is available since api 8
        assertThat(URLUtil.isValidUrl("www.mocky.io/v2/58fa10ce110000b81ad2106c"), `is`(true))
    }
}