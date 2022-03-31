package com.example.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@RequiredArgsConstructor
@Table("post")
public class Post implements Persistable<String> {

    @Id
    @Column("oid")
    private String oid;
    @Column("content")
    private String content;
    @Column("is_deleted")
    private boolean isDeleted;
    @Column("user_id")
    private String userId;
    @Column("insert_time")
    private long insertTime;
    @Column("insert_user")
    private String insertUser;
    @Column("update_time")
    private long updateTime;
    @Column("update_user")
    private String updateUser;

    @Override
    public String getId() {
        return oid;
    }

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
