package com.example.service;

import com.example.entity.QComment;
import com.example.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;


    @Override
    public void get() {
        commentRepository.query(sqlQuery ->
                sqlQuery.select(commentRepository.entityProjection())
                        .from(QComment.comment)
        );
    }

    @Override
    public void add() {

    }

    @Override
    public void list() {

    }
}
