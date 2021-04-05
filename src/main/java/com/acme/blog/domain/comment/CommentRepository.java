package com.acme.blog.domain.comment;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<CommentDto> findAllByPostId(Long postId);
}
