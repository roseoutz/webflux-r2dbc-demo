package com.example.entity;

import com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@RequiredArgsConstructor
@Data
@Table("user")
public class User implements Persistable<String> {

    @Id
    @Column("oid")
    private String oid;
    @Column("user_id")
    private String userId;
    @Column("username")
    private String username;
    @Column("password")
    private String password;
    @Column("cell_phone")
    private String cellPhone;

    @Override
    public String getId() {
        return oid;
    }

    @QueryType(PropertyType.NONE)
    @Transient
    private boolean isNew = false;

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }


}

