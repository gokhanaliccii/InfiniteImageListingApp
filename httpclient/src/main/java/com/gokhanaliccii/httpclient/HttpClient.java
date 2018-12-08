package com.gokhanaliccii.httpclient;

import java.lang.reflect.*;

import static com.gokhanaliccii.httpclient.TypeUtil.hasUnresolvableType;

public class HttpClient {
    private final String baseUrl;
    private final HttpRequestQueue queue;

    private HttpClient(HttpRequestQueue queue, String baseUrl) {
        this.queue = queue;
        this.baseUrl = baseUrl;
    }

    public static HttpClient with(HttpRequestQueue queue, String baseUrl) {
        return new HttpClient(queue, baseUrl);
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

                        Type type = getReturnType(method);
                        Type returnType = TypeUtil.getParameterUpperBound(
                                0, (ParameterizedType) type);

                        return new Request<>(queue, requestInfo, returnType);
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

    private Type getReturnType(Method javaMethod) {
        Type returnType = javaMethod.getGenericReturnType();

        if (hasUnresolvableType(returnType)) {
            throw new RuntimeException(
                    "Method return type must not include a type variable or wildcard: "
                            + returnType);
        }

        if (returnType == Void.class) {
            throw new RuntimeException("Service methods cannot return void");
        }

        return returnType;
    }
}
