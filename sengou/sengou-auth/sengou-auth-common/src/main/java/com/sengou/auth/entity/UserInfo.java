package com.sengou.auth.entity;

import lombok.Data;

/**
 * 载荷对象
 */
@Data
public class UserInfo {

    private Long id;

    private String username;

    public UserInfo(Long id,String username){
        this.id=id;
        this.username=username;
    }

}