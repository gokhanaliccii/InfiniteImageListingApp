package com.gokhanaliccii.httpclient;

import com.gokhanaliccii.httpclient.annotation.method.GET;
import com.gokhanaliccii.httpclient.annotation.method.TYPE;
import com.gokhanaliccii.httpclient.annotation.url.Query;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.List;

public class HttpClientTest {

    static final String BASE_URL = "https://api.unsplash.com";
    static final String CLIENT_ID = "f7af843e895c61a1f3434e6823743a08fb08ace46e203353f539a30eeb2a67e7";

    @Test
    public void should_() throws InterruptedException {
        EasyHttpClient httpClient = EasyHttpClient.with(new JsonRequestQueue(), BASE_URL);
        UnSplashImageService imageService = httpClient.create(UnSplashImageService.class);

        imageService.getImages(CLIENT_ID).enqueue(new HttpRequestQueue.HttpResult<Photo>() {

            @Override
            public void onResponse(Photo response) {
                System.out.println("object");
            }

            @Override
            public void onResponse(@NotNull List<? extends Photo> response) {
                System.out.println("array");

            }
        }, false, false);

        Thread.sleep(100 * 1000);
    }

    interface UnSplashImageService {
        @GET("/photos")
        @TYPE(value = Photo.class, isArray = true)
        Request<Photo> getImages(@Query("client_id") String clientId);
    }

}