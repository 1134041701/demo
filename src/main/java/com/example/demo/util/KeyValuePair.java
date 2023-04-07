package com.example.demo.util;

import lombok.Data;

import java.io.Serializable;

@Data
public class KeyValuePair implements Serializable {

    private static final long serialVersionUID = 4031931894892394896L;

    protected String key;

    protected String value;

    public KeyValuePair(){}

    public KeyValuePair(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
