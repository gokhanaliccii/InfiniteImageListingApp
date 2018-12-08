package com.gokhanaliccii.httpclient;

import com.gokhanaliccii.httpclient.util.IOUtil;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.*;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_ACCEPTED;
import static java.net.HttpURLConnection.HTTP_OK;

public class JsonRequestQueue implements HttpRequestQueue {

    @Override
    public <T> void add(@NotNull HttpRequest<T> request, boolean needCache) {
        HttpRequestInfo requestInfo = request.getRequestInfo();
        HttpResult<T> resultListener = request.getResultListener();

    }

    @Override
    public <T> void add(@NotNull HttpRequest<T> request, boolean needCache, boolean shouldUseRetry) {
        HttpRequestInfo requestInfo = request.getRequestInfo();

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(requestInfo.getUrl()).openConnection();

            putHeaders(requestInfo, connection);
            putHttpMethod(requestInfo, connection);

            int responseCode = connection.getResponseCode();
            InputStream inputStream;

            if (isResponseSucceed(responseCode)) {
                inputStream = connection.getInputStream();

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                IOUtil.pipe(inputStream, outputStream);
                IOUtil.closeStreams(inputStream, outputStream);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean isResponseSucceed(int responseCode) {
        return HTTP_OK <= responseCode && responseCode <= HTTP_ACCEPTED;
    }

    private void putHttpMethod(HttpRequestInfo requestInfo, HttpURLConnection connection) throws ProtocolException {
        connection.setRequestMethod(requestInfo.getMethod().name());
    }

    private void putHeaders(HttpRequestInfo requestInfo, HttpURLConnection connection) {
        Map<String, String> headers = requestInfo.getHeaders();
        for (String key : headers.keySet())
            connection.setRequestProperty(key, headers.get(key));
    }

    @Override
    public void removeAll() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
