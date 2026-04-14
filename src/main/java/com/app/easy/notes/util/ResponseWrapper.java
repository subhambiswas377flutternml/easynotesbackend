package com.app.easy.notes.util;

public class ResponseWrapper <T>{
    private Boolean success;
    private String message;
    private T data;

    public ResponseWrapper(T data){
        success = true;
        message = "Success";
        this.data=data;
    }

    public ResponseWrapper(){
        success = false;
        message = "Failed";
        data = null;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
