create table user (
    oid VARCHAR(64) primary key,
    user_id VARCHAR(64),
    username VARCHAR(64),
    password VARCHAR(64),
    cell_phone VARCHAR(64)
);

create table comment (
    oid VARCHAR(64) primary key,
    content VARCHAR(64),
    is_deleted VARCHAR(64),
    user_id VARCHAR(64),
    parent_oid VARCHAR(64),
    root_oid VARCHAR(64),
    insert_user VARCHAR(64),
    update_user VARCHAR(64),
    insert_time BIGINT,
    update_time BIGINT
);

create table post (
     oid VARCHAR(64) primary key,
     content VARCHAR(64),
     is_deleted VARCHAR(64),
     user_id VARCHAR(64),
     insert_user VARCHAR(64),
     update_user VARCHAR(64),
     insert_time BIGINT,
     update_time BIGINT
);