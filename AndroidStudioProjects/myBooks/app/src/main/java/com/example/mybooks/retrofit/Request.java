package com.example.mybooks.retrofit;

import com.google.gson.annotations.SerializedName;

public class Request {

    public String inputEmail;
    public String inputPw;

    public String getInputEmail() { return inputEmail; }
    public String getInputPw() { return inputPw; }
    public void setInputEmail(String inputEmail) { this.inputEmail = inputEmail; }
    public void setInputPw(String inputPw) { this.inputPw = inputPw; }



    public Request(String inputEmail, String inputPw) { // 회원가입 (이멜, 비번)
        this.inputEmail = inputEmail;
        this.inputPw = inputPw;
    }



}
