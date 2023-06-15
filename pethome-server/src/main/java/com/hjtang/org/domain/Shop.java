package com.hjtang.org.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Shop implements Serializable {
    private Long id;
    private String name;
    private String tel;
    private Date registerTime;
    private Integer state;
    private String address;
    private String logo;
    private Long adminId;

}
