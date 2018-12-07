package com.gokhanaliccii.jsonparser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    <T> T parse(String json, Class<T> clazz) throws JSONException, IllegalAccessException, InstantiationException {
        if (json == null || json.isEmpty()) {
            return null;
        }

        if (!(json.startsWith("{") || json.startsWith("["))) {
            return null;
        }

        T object = newInstance(clazz);

        if (json.startsWith("{")) {
            JSONObject jsonObject = new JSONObject(json);
            fillObject(object, jsonObject);
        }

        return object;
    }

    private <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return null;
    }

    void fillObject(Object object, JSONObject jsonObject) throws JSONException, IllegalAccessException, InstantiationException {
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (field == null)
                continue;

            if (!jsonObject.has(field.getName())) {
                continue;
            }

            field.setAccessible(true);
            if (field.isAnnotationPresent(JsonList.class)) {
                Class<?> value = field.getAnnotation(JsonList.class).value();
                fillArray(object, field, value, jsonObject.getJSONArray(field.getName()));
                continue;
            } else if (field.isAnnotationPresent(JsonObject.class)) {
                Class<?> value = field.getAnnotation(JsonObject.class).value();
                Object newInstance = newInstance(value);
                fillObject(newInstance, jsonObject.getJSONObject(field.getName()));
                field.set(object, newInstance);
                continue;
            }

            Object objectValue = jsonObject.get(field.getName());
            field.set(object, objectValue);

        }
    }

    void fillArray(Object object, Field field, Class<?> clazz, JSONArray jsonArray) throws JSONException, InstantiationException, IllegalAccessException {
        List childs = new ArrayList();

        for (int i = 0; i < jsonArray.length(); i++) {
            Object newInstance = newInstance(clazz);
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            fillObject(newInstance, jsonObject);

            childs.add(newInstance);
        }

        field.set(object, childs);
    }
}
