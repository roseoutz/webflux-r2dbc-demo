package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearch implements Serializable {

    private String oid;
    private String userId;
    private int page;
    private int pageSize;
}
