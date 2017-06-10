package com.icerockdev.babenko;

import android.content.Context;
import android.webkit.URLUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    Context mMockContext;

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
//        assertThat(Patterns.WEB_URL.matcher("www.mocky.io/v2/58fa10ce110000b81ad2106c").matches(), is(true)); wow that doesn`t work cus it is available since api 8
        assertThat(URLUtil.isValidUrl("www.mocky.io/v2/58fa10ce110000b81ad2106c"), is(true));
    }
}