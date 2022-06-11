package com.coffee.miniproject.service;

import com.coffee.miniproject.dto.PostDetailResponseDto;
import com.coffee.miniproject.dto.PostRequestDto;
import com.coffee.miniproject.dto.PostRequestDto4Put;
import com.coffee.miniproject.dto.PostResponseDto;
import com.coffee.miniproject.model.Post;
import com.coffee.miniproject.model.PostCategory;
import com.coffee.miniproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 게시글 등록
    @Transactional
    public void registerPost(PostRequestDto requestDto, String nickname) {
        Post post = new Post(requestDto, nickname);
        postRepository.save(post);
    }

    // 게시글 전체 조회, 검색 조회, 카테고리 조회
    public List<PostResponseDto> getAllPost(String category, String search) {
        List<Post> postList;
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();

        if(search == null){
            search = "";
        }

        if(category == null & search.equals("")){
            postList = postRepository.findAll();
        }else if(category == null){
            postList = postRepository.findAllByTitleContaining(search);
        }else if(category.equals(PostCategory.RECIPE.name())){
            postList = postRepository.findAllByCategoryAndTitleContaining(PostCategory.RECIPE, search);
        }else{
            postList = postRepository.findAllByCategoryAndTitleContaining(PostCategory.CAFE, search);
        }

        for(Post post : postList){
            PostResponseDto postResponseDto = new PostResponseDto(post);
            postResponseDtoList.add(postResponseDto);
        }

        return postResponseDtoList;
    }

    // 게시글 상세 조회
    public PostDetailResponseDto getPostDetail(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );

        return new PostDetailResponseDto(post);
    }

    // 게시글 수정
    @Transactional
    public void updatePost(Long id, PostRequestDto4Put requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );

        post.updatePost(requestDto);
        postRepository.save(post);
    }

    // 게시글 삭제
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
