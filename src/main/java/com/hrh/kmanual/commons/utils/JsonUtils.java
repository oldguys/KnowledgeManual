package com.hrh.kmanual.commons.utils;

import com.google.gson.Gson;

import java.util.Map;

/**
 * @author huangrenhao
 * @date 2018/6/11
 */
public class JsonUtils {

    private JsonUtils() {
    }

    private static volatile Gson gson;

    public static Gson getInstance() {

        Gson temp = gson;
        if (temp == null) {
            synchronized (Gson.class) {
                if (gson == null) {
                    gson = new Gson();
                    temp = gson;
                }
            }
        }
        return temp;
    }

    public static Map<String,Object> parseResultMap(String json){
        return getInstance().fromJson(json,Map.class);
    }
}
