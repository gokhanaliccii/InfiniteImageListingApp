package com.gokhanaliccii.httpclient;

import com.gokhanaliccii.httpclient.annotation.method.GET;
import com.gokhanaliccii.httpclient.annotation.method.TYPE;
import com.gokhanaliccii.httpclient.annotation.url.Query;
import org.hamcrest.core.IsNull;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class HttpClientTest {

    static final String BASE_URL = "https://api.unsplash.com";
    static final String CLIENT_ID = "f7af843e895c61a1f3434e6823743a08fb08ace46e203353f539a30eeb2a67e7";

    @Test
    public void should_FetchPhotosCorrectly() throws InterruptedException {
        EasyHttpClient httpClient = EasyHttpClient.with(new JsonRequestQueue(), BASE_URL);
        UnSplashImageService imageService = httpClient.create(UnSplashImageService.class);

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        imageService.getImages(CLIENT_ID).enqueue(new HttpRequestQueue.HttpResult<Photo>() {
            @Override
            public void onResponse(Photo response) {
                assertThat(response, notNullValue());
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(@NotNull List<? extends Photo> response) {
                assertTrue(response.size() > 0);
                countDownLatch.countDown();
            }
        }, false, false);

        countDownLatch.await(10, TimeUnit.SECONDS);
    }

    @Test
    public void should_TestFail() {
        fail("failed");
    }

    interface UnSplashImageService {
        @GET("/photos")
        @TYPE(value = Photo.class, isArray = true)
        Request<Photo> getImages(@Query("client_id") String clientId);
    }

}