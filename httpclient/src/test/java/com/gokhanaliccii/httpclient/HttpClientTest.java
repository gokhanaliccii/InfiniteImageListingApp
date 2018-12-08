package com.gokhanaliccii.httpclient;

import com.gokhanaliccii.httpclient.HttpRequestQueue.HttpRequestInfo.Method;
import com.gokhanaliccii.httpclient.annotation.header.Header;
import com.gokhanaliccii.httpclient.annotation.method.GET;
import com.gokhanaliccii.httpclient.annotation.method.POST;
import com.gokhanaliccii.httpclient.annotation.url.Path;
import com.gokhanaliccii.httpclient.annotation.url.Query;
import com.gokhanaliccii.httpclient.util.MockHttpRequestQueue;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.fail;

public class HttpClientTest {

    static final String BASE_URL = "https://unsplash.com";

    private HttpRequestQueue queue = null;

    @Before
    public void setUp() {
        queue = new MockHttpRequestQueue();
    }

    @Test
    public void should_DetermineGetMethodCorrectly() {
        Service service = EasyHttpClient.with(queue, BASE_URL).create(Service.class);

        Request<String> call = service.getMethod();
        HttpRequestQueue.HttpRequestInfo info = call.getRequestInfo();
        assertThat(info.getMethod(), is(Method.GET));
    }

    @Test
    public void should_GetUrlCorrectly() {
        final String expectedUrl = BASE_URL + "/getMethod";
        Service service = EasyHttpClient.with(queue, BASE_URL).create(Service.class);

        Request<String> call = service.getMethod();
        HttpRequestQueue.HttpRequestInfo info = call.getRequestInfo();

        assertThat(info.getUrl(), is(expectedUrl));
    }

    @Test
    public void should_UrlCreatedWithQueryParamCorrectly() {
        final String name = "Name1=Value1";
        final String expectedUrl = BASE_URL + "/getMethodWithParams?" + name;
        Service service =
                EasyHttpClient.with(queue, BASE_URL).create(Service.class);

        Request<String> call = service.getMethodWithQueryParam("Value1");
        HttpRequestQueue.HttpRequestInfo info = call.getRequestInfo();
        assertThat(info.getUrl(), is(equalTo(expectedUrl)));
    }

    @Test
    public void should_UrlPathCreatedCorrectly() {
        final String expectedUrl = BASE_URL + "/getMethod" + "/sampleA" + "/sampleB";
        Service service =
                EasyHttpClient.with(queue, BASE_URL).create(Service.class);

        Request<String> call = service.getMethodWithPath("sampleA", "sampleB");
        HttpRequestQueue.HttpRequestInfo info = call.getRequestInfo();
        assertThat(info.getUrl(), is(equalTo(expectedUrl)));
    }

    @Test
    public void should_DeterminePostMethodCorrectly() {
        Service service = EasyHttpClient.with(queue, BASE_URL).create(Service.class);

        Request<String> call = service.postMethod();
        HttpRequestQueue.HttpRequestInfo info = call.getRequestInfo();
        assertThat(info.getMethod(), is(Method.POST));
    }

    @Test
    public void should_RequestWithWithCorrectParams() {
        Service service = EasyHttpClient.with(queue, BASE_URL).create(Service.class);

        Request<String> call = service.postMethodWithHeader();
        HttpRequestQueue.HttpRequestInfo info = call.getRequestInfo();

        String o = (String) info.getHeaders().get("Authorization");

        assertThat(o, is("Client-ID XXX"));
    }


    @Test
    public void should_TestFail() {
        fail("failed");
    }


    interface Service {
        @GET("/getMethod")
        Request<String> getMethod();

        @GET("/getMethodWithParams")
        Request<String> getMethodWithQueryParam(@Query("Name1") String name);

        @GET("/getMethod/{a}/{b}")
        Request<String> getMethodWithPath(@Path("a") String a, @Path("b") String b);

        @POST("/postMethod")
        Request<String> postMethod();

        @POST("/postMethod")
        @Header("Authorization:Client-ID XXX")
        Request<String> postMethodWithHeader();


    }
}