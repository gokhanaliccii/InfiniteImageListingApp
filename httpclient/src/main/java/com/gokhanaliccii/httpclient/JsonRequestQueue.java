package com.gokhanaliccii.httpclient;

import com.gokhanaliccii.httpclient.util.IOUtilKt;
import com.gokhanaliccii.jsonparser.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_ACCEPTED;
import static java.net.HttpURLConnection.HTTP_OK;

public class JsonRequestQueue implements HttpRequestQueue {

    @Override
    public <T> void add(@NotNull HttpRequest<T> request, boolean needCache) {

    }

    @Override
    public <T> void add(@NotNull HttpRequest<T> request, boolean needCache, boolean shouldUseRetry) {
        sendRequest(request);
    }

    private <T> void sendRequest(@NotNull HttpRequest<T> request) {
        HttpRequestInfo requestInfo = request.getRequestInfo();

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(requestInfo.getUrl()).openConnection();

            putHeaders(requestInfo, connection);
            putHttpMethod(requestInfo, connection);

            int responseCode = connection.getResponseCode();

            if (isResponseSucceed(responseCode)) {
                InputStream inputStream = connection.getInputStream();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                IOUtilKt.pipe(inputStream, outputStream);
                IOUtilKt.closeStreams(inputStream, outputStream);

                String json = outputStream.toString();

                if (requestInfo.isArray()) {
                    List<T> list = new JsonParser().parseList(json, requestInfo.getReturnType());
                    request.getResultListener().onResponse(list);
                } else {
                    T object = (T) new JsonParser().parse(json, requestInfo.getReturnType());
                    request.getResultListener().onResponse(object);
                }
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
