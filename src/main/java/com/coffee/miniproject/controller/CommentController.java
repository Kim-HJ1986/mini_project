package com.coffee.miniproject.controller;

import com.coffee.miniproject.dto.CommentRequestDto;
import com.coffee.miniproject.model.Comment;
import com.coffee.miniproject.model.Member;
import com.coffee.miniproject.repository.CommentRepository;
import com.coffee.miniproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    // 댓글 생성
    @PostMapping("/api/post/{postid}/comments")
    public ResponseEntity<Void> registComment (@PathVariable Long postid, @RequestBody CommentRequestDto requestDtoList) {
        // 세션 가져오기...
        commentService.saveNewComments(postid, requestDtoList);
        return
    }
}
