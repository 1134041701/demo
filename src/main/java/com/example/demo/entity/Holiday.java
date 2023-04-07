package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname Holiday
 * @Description TODO
 * @Date 2022/11/30 10:41
 * @Created by Dongbo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Holiday {

    /**
     * 2022-9-1
     */
    private String date;

    private Integer year;

    private Integer month;

    private Integer day;

    /**
     * 1: 法定  0: 普通
     */
    private Byte status;
}
