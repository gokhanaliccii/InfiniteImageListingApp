package com.gokhanaliccii.httpclient;

import com.gokhanaliccii.httpclient.HttpRequestQueue.HttpRequestInfo.Method;
import com.gokhanaliccii.httpclient.annotation.method.GET;
import com.gokhanaliccii.httpclient.annotation.url.Query;
import com.gokhanaliccii.httpclient.util.MockHttpRequestQueue;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class HttpClientTest {

    static final String BASE_URL = "https://unsplash.com";

    private HttpRequestQueue queue = null;

    @Before
    public void setUp() {
        queue = new MockHttpRequestQueue();
    }

    @Test
    public void should_DetermineGetMethodCorrectly() {
        Service service = HttpClient.with(queue, BASE_URL).create(Service.class);

        Request<String> call = service.getMethod();
        HttpRequestQueue.HttpRequestInfo info = call.getRequestInfo();
        assertThat(info.getMethod(), is(Method.GET));
    }

    @Test
    public void should_GetUrlCorrectly() {
        final String expectedUrl = BASE_URL + "/getMethod";
        Service service = HttpClient.with(queue, BASE_URL).create(Service.class);

        Request<String> call = service.getMethod();
        HttpRequestQueue.HttpRequestInfo info = call.getRequestInfo();

        assertThat(info.getUrl(), is(expectedUrl));
    }

    @Test
    public void should_QueryParamCorrect() {
        final String name = "Name1=Value1";
        final String expectedUrl = BASE_URL + "/getMethodWithParams?" + name;
        Service service =
                HttpClient.with(queue, BASE_URL).create(Service.class);

        Request<String> call = service.getMethodWithQueryParam("Value1");
        HttpRequestQueue.HttpRequestInfo info = call.getRequestInfo();
        assertThat(info.getUrl(), is(equalTo(expectedUrl)));
    }

    interface Service {
        @GET("/getMethod")
        Request<String> getMethod();

        @GET("/getMethodWithParams")
        Request<String> getMethodWithQueryParam(@Query("Name1") String name);
    }
}