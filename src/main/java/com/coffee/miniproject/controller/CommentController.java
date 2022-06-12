package com.coffee.miniproject.controller;

import com.coffee.miniproject.dto.CommentRequestDto;
import com.coffee.miniproject.dto.CommentRequestDto4Put;
import com.coffee.miniproject.dto.CommentResponseDto;
import com.coffee.miniproject.dto.PostRequestDto4Put;
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

    // 댓글 삭제
    @DeleteMapping("/api/post/{postid}/comments/{commentid}")
    public Boolean deleteComment(@PathVariable Long commentid, @AuthenticationPrincipal UserDetailsImpl userDetails){
        //사용자의 id가 null값인지 검증
        Boolean result = false;

        Long memberid = userDetails.getUser().getId();
        if (memberid != null) {
             result = commentService.deleteComment(commentid, memberid);
        }

        return result;
    }
    // 댓글 수정
    @PutMapping("/api/post/{postid}/comments/{commentid}")
    public Boolean updateComment(@PathVariable Long commentid, @RequestBody CommentRequestDto4Put requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        //사용자의 id가 null값인지 검증
        Boolean result = false;

        Long memberid = userDetails.getUser().getId();
        if (memberid != null) {
            result = commentService.updateComment(commentid, requestDto, memberid);
        }
        return result;
    }
}

