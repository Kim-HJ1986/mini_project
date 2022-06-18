package com.coffee.miniproject.repository;

import com.coffee.miniproject.model.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    CommentLike findByMemberIdAndCommentId(Long memberId, Long commentId);
}
