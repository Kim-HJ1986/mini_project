package com.coffee.miniproject.controller;

import com.coffee.miniproject.dto.*;
import com.coffee.miniproject.model.Member;
import com.coffee.miniproject.security.UserDetailsImpl;
import com.coffee.miniproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 등록
    @PostMapping("/api/posts")
    public PostDetailResponseDto registerPost(@RequestBody PostRequestDto requestDto){
        //@AuthenticationPrincipal은 null로 받아온다. Authentication으로 받아오기.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        String username = principal.getUsername();

        return postService.registerPost(requestDto, username);
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
    public void updatePost(@PathVariable Long id,
                           @RequestBody PostRequestDto4Put requestDto
                           ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        String username = principal.getUsername();
        // 시큐리티 완료 후 본인의 게시글인지 check 로직 추가
        postService.updatePost(id, requestDto, username);
    }

    //게시글 삭제
    @DeleteMapping("/api/posts/{id}")
    public void deletePost(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        String username = principal.getUsername();
        // 시큐리티 완료 후 본인의 게시글인지 check 로직 추가
        postService.deletePost(id, username);
    }

    // 좋아요 클릭
    @PostMapping("/api/posts/{id}/like")
    public void likePost(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User principal = (User) authentication.getPrincipal();
        String username = principal.getUsername();

        postService.likePost(id, username);
    }

    // 랭킹 조회
    @GetMapping("/api/ranks")
    public RankDto getRanks(){
        return postService.getRanks();
    }
}
