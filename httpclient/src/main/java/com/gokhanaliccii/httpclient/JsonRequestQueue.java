package com.gokhanaliccii.httpclient;

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
            if (HTTP_OK <= responseCode && HTTP_ACCEPTED <= 299) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder response = new StringBuilder();

            String currentLine;
            while ((currentLine = in.readLine()) != null)
                response.append(currentLine);

            in.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


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
    public void remove(@NotNull String tag) {

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
