package com.coffee.miniproject.repository;

import com.coffee.miniproject.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
