package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse implements Serializable {

    private String oid;
    private String userId;
    private String username;
    @Transient
    private String password;
    private String cellPhone;
}
