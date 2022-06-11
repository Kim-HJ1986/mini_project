package com.coffee.miniproject.repository;

import com.coffee.miniproject.model.Post;
<<<<<<< HEAD
import com.coffee.miniproject.model.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByCategoryAndTitleContaining(PostCategory postCategory, String search);

    List<Post> findAllByTitleContaining(String search);

=======
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
>>>>>>> 1c28ef9 ([etc]First Commit)
}
