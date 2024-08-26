package com.wms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data /*getter,setter方法等*/
@AllArgsConstructor /*全参*/
@NoArgsConstructor  /*无参*/
public class User {
    private int id;
    private String no;
    private String name;
    private String password;
    private int age;
    private int sex;
    private int roleId;
    private String phone;
    private String isvalid;

}
