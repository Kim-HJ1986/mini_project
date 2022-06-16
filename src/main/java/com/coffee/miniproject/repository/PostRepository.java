package com.coffee.miniproject.repository;

import com.coffee.miniproject.model.Post;
import com.coffee.miniproject.model.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCategoryAndTitleContaining(PostCategory postCategory, String search);

    List<Post> findAllByTitleContaining(String search);

    List<Post> findAllByOrderByLikeCntDesc();

}
