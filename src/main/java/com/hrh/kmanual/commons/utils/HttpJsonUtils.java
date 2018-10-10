package com.hrh.kmanual.commons.utils;


import com.hrh.kmanual.commons.dto.HttpJsonResult;
import com.hrh.kmanual.configs.HttpStatus;

/**
 * @author huangrenhao
 * @date 2018/6/11
 */
public class HttpJsonUtils {

    public static int SUCCESS = 1;
    public static int FAILURE = 0;

    public static final HttpJsonResult RESULT_SUCCESS = build(SUCCESS);
    public static final HttpJsonResult RESULT_FAILURE = build(FAILURE);

    private HttpJsonUtils() {
    }

    public static HttpJsonResult build(int flag) {

        if (flag == SUCCESS) {
            return build(HttpStatus.CODE_SUCCESS, HttpStatus.STATUS_SUCCESS, null, null);
        }

        if (flag == FAILURE) {
            return build(HttpStatus.CODE_SUCCESS, HttpStatus.STATUS_FAILURE, null, null);
        }

        return null;
    }

    public static HttpJsonResult buildSuccess(String message) {

        return build(HttpStatus.CODE_SUCCESS, HttpStatus.STATUS_SUCCESS, message, null);
    }

    public static HttpJsonResult buildSuccess(String message, Object object) {

        return build(HttpStatus.CODE_SUCCESS, HttpStatus.STATUS_SUCCESS, message, object);
    }

    public static HttpJsonResult buildError(String message, Object object) {

        return build(HttpStatus.CODE_ERROR, HttpStatus.STATUS_FAILURE, message, object);
    }

    public static HttpJsonResult buildError(String message) {

        return build(HttpStatus.CODE_ERROR, HttpStatus.STATUS_FAILURE, message, null);
    }

    public static HttpJsonResult buildValidate(String message) {

        return build(HttpStatus.CODE_VALIDATE_EXCEPTION, HttpStatus.STATUS_FAILURE, message, null);
    }

    public static HttpJsonResult build(int code, String status, String message, Object object) {

        HttpJsonResult result = new HttpJsonResult();
        result.setCode(code);
        result.setStatus(status);
        result.setMessage(message);
        result.setObject(object);
        return result;
    }
}
