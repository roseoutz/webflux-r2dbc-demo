package com.example.dto;

import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest implements Serializable {

    private String oid;
    private String userId;
    private String username;
    private String password;
    private String cellPhone;
}
