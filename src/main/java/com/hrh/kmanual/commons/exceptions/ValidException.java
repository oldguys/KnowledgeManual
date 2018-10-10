package com.hrh.kmanual.commons.exceptions;

/**
 * @author huangrenhao
 * @date 2018/7/6
 */
public class ValidException extends RuntimeException {

    private Object object;

    public ValidException(String message) {
        super(message);
    }

    public ValidException(String message, Object object) {
        super(message);
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
