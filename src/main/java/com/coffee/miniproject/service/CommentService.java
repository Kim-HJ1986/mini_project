package com.coffee.miniproject.service;

import com.coffee.miniproject.dto.CommentRequestDto;
import com.coffee.miniproject.dto.CommentResponseDto;
import com.coffee.miniproject.model.Comment;
import com.coffee.miniproject.model.Member;
import com.coffee.miniproject.model.Post;
import com.coffee.miniproject.repository.CommentRepository;
import com.coffee.miniproject.repository.MemberRepository;
import com.coffee.miniproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    //댓글 등록
    @Transactional
    public void saveNewComments(Long postid, Long memberid, CommentRequestDto requestDtoList) {
        Member member = memberRepository.findById(memberid)
                .orElseThrow(RuntimeException::new);
        Post post = postRepository.findById(postid)
                .orElseThrow(RuntimeException::new);

        Comment comment = requestDtoList.toEntity();
        comment.registCommentInfo(post, member);
        commentRepository.save(comment);
    }


    // 댓글조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByPostId(Long postid) {
        List<Comment> commentListByPostId =  commentRepository.findAllByPostId(postid);

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for(Comment comment : commentListByPostId){
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            commentResponseDtoList.add(commentResponseDto);
        }

        return commentResponseDtoList;
    }

}
