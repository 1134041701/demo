package com.example.demo.service;

import java.util.List;

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
    public String whileMethod(List<String> list){
        for (String bbb : list) {
            if(bbb.equals("hello")){
                return bbb;
            }
        }
        return null;
    }
}
