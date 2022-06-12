package com.coffee.miniproject.controller;

import com.coffee.miniproject.dto.CommentRequestDto;
import com.coffee.miniproject.dto.CommentResponseDto;
import com.coffee.miniproject.model.Comment;
import com.coffee.miniproject.model.Member;
import com.coffee.miniproject.repository.CommentRepository;
import com.coffee.miniproject.security.UserDetailsImpl;
import com.coffee.miniproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    // 댓글 생성
    @PostMapping("/api/post/{postid}/comments")
    public ResponseEntity<Void> registComment (@PathVariable Long postid, @RequestBody CommentRequestDto requestDtoList, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 세션 가져오기...
        Long memberid = userDetails.getUser().getId();
        commentService.saveNewComments(postid, memberid, requestDtoList);
        return ResponseEntity.ok().build();
    }

    // 댓글 조회
    @GetMapping("/api/post/{postid}/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId (@PathVariable Long postid) {
        return ResponseEntity.ok().body(commentService.getCommentsByPostId(postid));
    }

    //  댓글 삭제

    // 댓글 업뎃
}

