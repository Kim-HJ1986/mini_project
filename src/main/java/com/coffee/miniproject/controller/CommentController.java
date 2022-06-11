package com.coffee.miniproject.controller;

import com.coffee.miniproject.dto.CommentRequestDto;
import com.coffee.miniproject.model.Comment;
import com.coffee.miniproject.model.Member;
import com.coffee.miniproject.repository.CommentRepository;
import com.coffee.miniproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentService commentService;
    // 댓글 조회
    @PostMapping("/api/post/{postid}/comments")
    public Comment createComment(@RequestBody CommentRequestDto requestDto, @PathVariable Member postid) {
        Comment comment = new Comment(requestDto);
        commentService.save(requestDto, postid);
        return comment;
    }
}
