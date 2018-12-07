package com.gokhanaliccii.jsonparser;

import com.gokhanaliccii.jsonparser.annotation.JsonList;
import com.gokhanaliccii.jsonparser.annotation.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    <T> T parse(String json, Class<T> clazz) {
        T object = null;

        if (json == null || json.isEmpty()) {
            return null;
        }

        if (JsonSyntaxKt.hasValidSyntax(json)) {
            object = newInstance(clazz);

            if (JsonSyntaxKt.isJsonObject(json)) {
                try {
                    parseJsonObject(json, object);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }

        return object;
    }

    private <T> void parseJsonObject(String json, T object) throws JSONException, IllegalAccessException, InstantiationException {
        JSONObject jsonObject = new JSONObject(json);
        fillObject(object, jsonObject);
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

    private void fillObject(Object object, JSONObject jsonObject) throws JSONException, IllegalAccessException, InstantiationException {
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (thatFieldNotExistAtJson(jsonObject, field))
                continue;

            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            if (field.isAnnotationPresent(JsonList.class)) {
                Class<?> value = field.getAnnotation(JsonList.class).value();
                fillArray(object, field, value, jsonObject.getJSONArray(field.getName()));
            } else if (field.isAnnotationPresent(JsonObject.class)) {
                Class<?> value = field.getAnnotation(JsonObject.class).value();
                Object newInstance = newInstance(value);
                fillObject(newInstance, jsonObject.getJSONObject(field.getName()));
                field.set(object, newInstance);
            } else {
                Object value = jsonObject.get(field.getName());
                field.set(object, value);
            }
        }
    }

    private boolean thatFieldNotExistAtJson(JSONObject jsonObject, Field field) {
        return !jsonObject.has(field.getName());
    }

    private void fillArray(Object object, Field field, Class<?> newObjectCandidate, JSONArray jsonArray) throws JSONException, InstantiationException, IllegalAccessException {
        List objectList = new ArrayList();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Object newInstance = newInstance(newObjectCandidate);
            fillObject(newInstance, jsonObject);

            objectList.add(newInstance);
        }

        field.set(object, objectList);
    }
}
