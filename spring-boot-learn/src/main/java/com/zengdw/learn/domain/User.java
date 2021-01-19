package com.zengdw.learn.domain;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : zengd
 * @date : Created in 2021/1/19 15:16
 * @description:
 * @version:
 */
@Data
@Accessors(chain = true)
public class User {
    private Long id;
    private String name;
    private int age;
}
