package com.gokhanaliccii.jsonparser;

import com.gokhanaliccii.jsonparser.annotation.JsonList;
import com.gokhanaliccii.jsonparser.annotation.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JsonParser {

    <T> T parse(String json, Class<T> clazz) {
        T object = null;

        if (JsonSyntaxKt.hasValidObjectSyntax(json)) {
            object = newInstance(clazz);

            try {
                deserializeJsonToObject(object, new JSONObject(json));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        return object;
    }

    <T> List<T> parseList(String json, Class<T> clazz) {
        List<T> objectArray = null;

        if (JsonSyntaxKt.hasValidArraySyntax(json)) {
            objectArray = new LinkedList();
            try {
                deserializeJsonToList(new JSONArray(json), objectArray, clazz);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        return objectArray;
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

    private void deserializeJsonToObject(Object object, JSONObject jsonObject) throws JSONException, IllegalAccessException, InstantiationException {
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if (fieldNameNotExistAtJson(jsonObject, field))
                continue;

            enableField(field);

            if (field.isAnnotationPresent(JsonList.class)) {
                Class<?> value = field.getAnnotation(JsonList.class).value();
                List objectList = new ArrayList();
                JSONArray jsonArray = jsonObject.getJSONArray(field.getName());
                deserializeJsonToList(jsonArray, objectList, value);
                field.set(object, objectList);
            } else if (field.isAnnotationPresent(JsonObject.class)) {
                Class<?> value = field.getAnnotation(JsonObject.class).value();
                Object newInstance = newInstance(value);
                deserializeJsonToObject(newInstance, jsonObject.getJSONObject(field.getName()));
                field.set(object, newInstance);
            } else {
                Object value = jsonObject.get(field.getName());
                field.set(object, value);
            }
        }
    }

    private void deserializeJsonToList(JSONArray jsonArray, List objectList, Class<?> newObjectCandidate) throws JSONException, InstantiationException, IllegalAccessException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Object newInstance = newInstance(newObjectCandidate);
            deserializeJsonToObject(newInstance, jsonObject);

            objectList.add(newInstance);
        }
    }

    private void enableField(Field field) {
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
    }

    private boolean fieldNameNotExistAtJson(JSONObject jsonObject, Field field) {
        return !jsonObject.has(field.getName());
    }
}
