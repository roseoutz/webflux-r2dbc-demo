package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse implements Serializable {

    private String oid;
    private String content;
    private boolean isDeleted;
    private String userId;
    private long insertTime;
    private String insertUser;
    private long updateTime;
    private String updateUser;
}
