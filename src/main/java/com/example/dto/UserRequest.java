package com.example.dto;

import lombok.*;

@Data
public class UserRequest {

    private String oid;
    private String userId;
    private String username;
    private String password;
    private String cellPhone;
}
