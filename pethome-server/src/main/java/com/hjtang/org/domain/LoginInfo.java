package com.hjtang.org.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginInfo implements Serializable {
    private Long id;
    private String username;
    private String phone;
    private String email;
    private String salt;
    private String password;
    private Integer type;
    private Boolean disable;

}
