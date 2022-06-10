package com.coffee.miniproject.repository;

import com.coffee.miniproject.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
