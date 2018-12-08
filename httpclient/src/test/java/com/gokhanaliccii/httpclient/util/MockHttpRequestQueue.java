package com.gokhanaliccii.httpclient.util;

import com.gokhanaliccii.httpclient.HttpRequestQueue;
import org.jetbrains.annotations.NotNull;

public class MockHttpRequestQueue implements HttpRequestQueue {

    @Override
    public void add(@NotNull HttpRequest info, boolean needCache) {

    }

    @Override
    public void add(@NotNull HttpRequest info, boolean needCache, boolean shouldUseRetry) {

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
