package com.example.mybooks.retrofit;

import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("message")
    public String message;

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

}
