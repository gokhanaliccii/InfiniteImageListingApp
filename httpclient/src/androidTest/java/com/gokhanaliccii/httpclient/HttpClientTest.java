package com.gokhanaliccii.httpclient;

import com.gokhanaliccii.httpclient.annotation.method.GET;
import com.gokhanaliccii.httpclient.annotation.url.Query;
import org.junit.Test;

import java.util.List;

public class HttpClientTest {

    static final String BASE_URL = "https://api.unsplash.com";
    static final String CLIENT_ID = "f7af843e895c61a1f3434e6823743a08fb08ace46e203353f539a30eeb2a67e7";

    @Test
    public void should_() throws InterruptedException {
        HttpClient httpClient = HttpClient.with(new JsonRequestQueue(), BASE_URL);
        UnSplashImageService imageService = httpClient.create(UnSplashImageService.class);

        imageService.getImages(CLIENT_ID).enqueue(new HttpRequestQueue.HttpResult<List<Photo>>() {
            @Override
            public void onResponse(List<Photo> response) {
                System.out.println();
            }
        }, false, false);

        Thread.sleep(100 * 1000);
    }

    interface UnSplashImageService {

        @GET("/photos")
        Request<List<Photo>> getImages(@Query("client_id") String clientId);
    }

    class Photo {
        String id;
        int width;
        int height;
    }


}