package com.gokhanaliccii.httpclient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class EasyHttpClient {
    private final String baseUrl;
    private final HttpRequestQueue queue;

    private EasyHttpClient(HttpRequestQueue queue, String baseUrl) {
        this.queue = queue;
        this.baseUrl = baseUrl;
    }

    public static EasyHttpClient with(HttpRequestQueue queue, String baseUrl) {
        return new EasyHttpClient(queue, baseUrl);
    }

    public <T> T create(final Class<T> service) {
        validateServiceInterface(service);

        return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[]{service},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args)
                            throws Throwable {

                        if (method.getDeclaringClass() == Object.class) {
                            return method.invoke(this, args);
                        }

                        HttpRequestQueue.HttpRequestInfo requestInfo =
                                RequestInfoConverter.newInstance()
                                        .javaMethod(method)
                                        .baseUrl(baseUrl)
                                        .args(args)
                                        .compile();

                        return new Request<>(queue, requestInfo);
                    }
                });
    }

    private <T> void validateServiceInterface(Class<T> service) {
        if (!service.isInterface()) {
            throw new IllegalArgumentException("Service declaration must be interfaces.");
        }

        if (service.getInterfaces().length > 0) {
            throw new IllegalArgumentException("Service must not extend other interfaces.");
        }
    }
}
