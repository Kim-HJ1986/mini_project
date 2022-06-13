package com.coffee.miniproject.service;

import com.coffee.miniproject.dto.PostDetailResponseDto;
import com.coffee.miniproject.dto.PostRequestDto;
import com.coffee.miniproject.dto.PostRequestDto4Put;
import com.coffee.miniproject.dto.PostResponseDto;
import com.coffee.miniproject.model.Member;
import com.coffee.miniproject.model.Post;
import com.coffee.miniproject.model.PostCategory;
import com.coffee.miniproject.repository.MemberRepository;
import com.coffee.miniproject.repository.PostRepository;
import com.coffee.miniproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
//    private final EntityManager em;

    // 게시글 등록
    @Transactional
    public PostDetailResponseDto registerPost(PostRequestDto requestDto, UserDetailsImpl userDetails) {        
        Member member = memberRepository.findById(userDetails.getUser().getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 Id의 회원이 존재하지 않습니다.")
        );
        
        // 게시글 작성자 저장 (편의 메서드 -> member에도 posts에 해당 post add)
        Post post = new Post(requestDto, member);
        postRepository.save(post);
        
        // 저장된 Post -> PostDetailResponseDto에 담아 리턴
        return new PostDetailResponseDto(post);
    }

    // 게시글 전체 조회, 검색 조회, 카테고리 조회
    public List<PostResponseDto> getAllPost(String category, String search) {
        List<Post> postList;
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();

        if(search == null){
            search = "";
        }
        if(category == null){
            category = "";
        }

        if(category.equals("") & search.equals("")){
            postList = postRepository.findAll();
        }else if(category.equals("")){
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
    public void updatePost(Long id, PostRequestDto4Put requestDto, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );

        if (!Objects.equals(userDetails.getUser().getId(), post.getMember().getId())){
            throw new IllegalArgumentException("본인의 게시글만 수정할 수 있습니다.");
        }

        post.updatePost(requestDto);
        postRepository.save(post);
    }

    // 게시글 삭제
    public void deletePost(Long id, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("존재하지 않는 게시글입니다.")
        );

        if (!Objects.equals(userDetails.getUser().getId(), post.getMember().getId())){
            throw new IllegalArgumentException("본인의 게시글만 수정할 수 있습니다.");
        }

        postRepository.deleteById(id);
    }
}
