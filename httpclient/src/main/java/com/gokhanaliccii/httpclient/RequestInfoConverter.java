package com.gokhanaliccii.httpclient;

import com.gokhanaliccii.httpclient.HttpRequestQueue.HttpRequestInfo;
import com.gokhanaliccii.httpclient.annotation.entity.Body;
import com.gokhanaliccii.httpclient.annotation.header.Header;
import com.gokhanaliccii.httpclient.annotation.method.GET;
import com.gokhanaliccii.httpclient.annotation.method.POST;
import com.gokhanaliccii.httpclient.annotation.method.TYPE;
import com.gokhanaliccii.httpclient.annotation.url.Path;
import com.gokhanaliccii.httpclient.annotation.url.Query;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class RequestInfoConverter {
    private Method javaMethod;
    private String baseUrl;
    private Object[] args;
    private String path;
    private Map<String, String> pathValues;
    private HttpRequestInfo.Method method;
    private String tag;
    private StringBuilder queryParams;
    private Map<String, String> headers;
    private Object body;

    private RequestInfoConverter() {
        queryParams = emptyParams();
        pathValues = new HashMap<>();
        headers = new HashMap<>();
    }

    public static RequestInfoConverter newInstance() {
        return new RequestInfoConverter();
    }

    RequestInfoConverter javaMethod(Method javaMethod) {
        this.javaMethod = javaMethod;
        return this;
    }

    RequestInfoConverter baseUrl(String url) {
        this.baseUrl = url;
        return this;
    }

    RequestInfoConverter args(Object[] args) {
        this.args = args;
        return this;
    }

    HttpRequestInfo compile() {
        determineFromPrototype();
        determineFromArguments();
        updateUrlWithPaths();

        HttpRequestInfo requestInfo = new HttpRequestInfo(getUrl(), method);
        requestInfo.setHeaders(headers);
        requestInfo.setBody(body);

        return requestInfo;
    }

    private void updateUrlWithPaths() {
        String[] relatedPaths = path.split("/");
        for (String _path : relatedPaths) {
            _path = _path.replaceAll("\\?(.*)", "");

            if (_path.matches("\\{(.*)\\}")) {
                String key = _path.replaceAll("\\{(.*)\\}", "$1");
                path = path.replaceAll("\\{("+key+")\\}", pathValues.get(key));
            }
        }
    }

    private String getUrl() {
        if (queryParams.length() > 0) {
            queryParams.deleteCharAt(queryParams.length() - 1);
        }

        return baseUrl + path + queryParams.toString();
    }

    private StringBuilder emptyParams() {
        return new StringBuilder("");
    }

    private void determineFromPrototype() {
        Annotation[] annotations = javaMethod.getAnnotations();

        for(Annotation annotation : annotations) {
            if (annotation instanceof GET) {
                method = HttpRequestInfo.Method.GET;
                path = ((GET) annotation).value();
            } else if(annotation instanceof POST) {
                method = HttpRequestInfo.Method.POST;
                path = ((POST) annotation).value();
            } else if(annotation instanceof Header) {
                String[] headerSet = ((Header) annotation).value();
                determineHeaders(headerSet);
            }
        }
    }

    private void determineFromArguments() {
        Class<?>[] parameterTypes = javaMethod.getParameterTypes();
        if (parameterTypes.length > 0) {
            determineParameterData();
        } else {
            queryParams = emptyParams();
        }
    }

    private void determineHeaders(String[] headerSet) {
        if (headerSet == null) {
            throw new IllegalArgumentException("Headers must not be empty");
        }

        for (String header : headerSet) {
            String[] nameValuePair = header.split(":\\s");
            if (nameValuePair.length < 2) {
                throw new IllegalArgumentException(
                        "Wrong header format, expected format is <name>: <value>");
            }

            headers.put(nameValuePair[0], nameValuePair[1]);
        }
    }

    private void determineParameterData() {
        Annotation[][] parameterAnnotations = javaMethod.getParameterAnnotations();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] annotations = parameterAnnotations[i];
            Object value = args[i];

            for (Annotation annotation : annotations) {
                if (annotation instanceof Query) {
                    if (shouldSkip(value)) {
                        continue;
                    }

                    Query query = (Query) annotation;
                    String name = query.value();
                    boolean isEncoded = query.encoded();

                    initQueryParams();
                    updateQueryParams(value, name, isEncoded);
                } else if (annotation instanceof Body) {
                    body = value;
                } else if (annotation instanceof Path) {
                    Path path = (Path) annotation;
                    if (path.value() != null && value != null) {
                        pathValues.put(path.value(), value.toString());
                    }
                }
            }
        }
    }

    public Object newInstance(String className, Object...args) throws Exception {
        Class<?> clazz = Class.forName(className);
        if(args == null || args.length == 0) {
            return clazz.newInstance();
        }

        List<Class<?>> argTypes = new ArrayList<>();
        for(Object object : args) {
            argTypes.add(object.getClass());
        }
        Constructor<?> explicitConstructor = clazz.getConstructor(
                argTypes.toArray(new Class[argTypes.size()]));
        return explicitConstructor.newInstance(args);
    }

    private boolean shouldSkip(Object value) {
        return value == null;
    }

    private void initQueryParams() {
        if (queryParams.length() == 0) {
            queryParams = new StringBuilder("?");
        }
    }

    private void updateQueryParams(Object value, String name, boolean isEncoded) {
        value = shouldEncode(value, isEncoded);

        queryParams.append(name);
        queryParams.append("=");
        queryParams.append(value);
        queryParams.append("&");
    }

    private Object shouldEncode(Object value, boolean isEncoded) {
        if (!isEncoded) {
            try {
                value = URLEncoder.encode(value.toString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

}
