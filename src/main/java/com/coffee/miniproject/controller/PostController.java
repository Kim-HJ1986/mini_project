package com.coffee.miniproject.controller;

import com.coffee.miniproject.dto.PostDetailResponseDto;
import com.coffee.miniproject.dto.PostRequestDto;
import com.coffee.miniproject.dto.PostRequestDto4Put;
import com.coffee.miniproject.dto.PostResponseDto;
import com.coffee.miniproject.security.UserDetailsImpl;
import com.coffee.miniproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 등록
    @PostMapping("/api/posts")
    public void registerPost(@RequestBody PostRequestDto requestDto,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        postService.registerPost(requestDto, userDetails);
    }

    // 게시글 전체 조회, 검색 조회, 카테고리 조회
    @GetMapping("/api/posts")
    public List<PostResponseDto> getAllPost(@RequestParam(required = false) String category,
                                            @RequestParam(required = false) String search){
        return postService.getAllPost(category, search);
    }

    // 게시글 상세 조회
    @GetMapping("/api/posts/{id}")
    public PostDetailResponseDto getPostDetail(@PathVariable Long id){
        return postService.getPostDetail(id);
    }

    // 게시글 수정
    @PutMapping("/api/posts/{id}")
    public void updatePost(@PathVariable Long id, @RequestBody PostRequestDto4Put requestDto){
        // 시큐리티 완료 후 본인의 게시글인지 check 로직 추가
        postService.updatePost(id, requestDto);
    }

    //게시글 삭제
    @DeleteMapping("/api/posts/{id}")
    public void deletePost(@PathVariable Long id){
        // 시큐리티 완료 후 본인의 게시글인지 check 로직 추가
        postService.deletePost(id);
    }
}
