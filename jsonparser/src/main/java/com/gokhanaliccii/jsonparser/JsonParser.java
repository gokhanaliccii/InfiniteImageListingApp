package com.gokhanaliccii.jsonparser;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.List;

public class JsonParser {

    <T> T parse(String json, Class<T> clazz) throws JSONException, IllegalAccessException {
        if (json == null || json.isEmpty()) {
            return null;
        }

        if (!(json.startsWith("{") || json.startsWith("["))) {
            return null;
        }


        T object = newInstance(clazz);

        if (json.startsWith("{")) {

            JSONObject jsonObject = new JSONObject(json);


            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                if (field == null)
                    continue;

                if (!jsonObject.has(field.getName())) {
                    continue;
                }

                field.setAccessible(true);
                Class<?> type = field.getType();
                Object objectValue = jsonObject.get(field.getName());

                field.set(object, objectValue);


                if (type.isAssignableFrom(List.class)) {

                }
            }
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


}
