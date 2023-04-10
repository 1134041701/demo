package com.example.demo.service;

public class ServiceDemo {
    public String ifElseMethod(Integer age){
        String returnString;
        if(age != null){
            if(age > 0 && age <= 18 ){
                returnString = "juvenile";
                return returnString;
            } else if (age > 18 && age <= 25) {
                returnString = "youth";
                return returnString;
            } else if (age > 25 && age <=120) {
                returnString = "middle-aged";
                return returnString;
            }else {
                returnString = "error";
                return returnString;
            }
        }
        return null;
    }
}
