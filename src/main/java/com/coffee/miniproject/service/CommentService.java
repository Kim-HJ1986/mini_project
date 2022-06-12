package com.coffee.miniproject.service;

import com.coffee.miniproject.dto.CommentRequestDto;
import com.coffee.miniproject.dto.CommentRequestDto4Put;
import com.coffee.miniproject.dto.CommentResponseDto;
import com.coffee.miniproject.model.Comment;
import com.coffee.miniproject.model.Member;
import com.coffee.miniproject.model.Post;
import com.coffee.miniproject.repository.CommentRepository;
import com.coffee.miniproject.repository.MemberRepository;
import com.coffee.miniproject.repository.PostRepository;
import com.coffee.miniproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    // 댓글 삭제
    public Boolean deleteComment(Long id, Long memberid){
        // comment내의 memberid와 로그인한 member아이디 일치하는지 확인
        Comment commentByPostId =  commentRepository.findByPostId(id);
        if (!Objects.equals(memberid, commentByPostId.getMember().getId())) {
            return false;
            //throw new RuntimeException("댓글을 삭제하려는 유저의 아이디가 작성자의 아이디와 일치하지 않습니다.");
        }else {commentRepository.deleteById(id);}
        return true;
    }

    // 댓글 수정
    public Boolean updateComment(Long id, CommentRequestDto4Put requestDto, Long memberid) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );
        // comment내의 memberid와 로그인한 member아이디 일치하는지 확인
        Comment commentByPostId = commentRepository.findByPostId(id);
        if (!Objects.equals(memberid, commentByPostId.getMember().getId())) {
            return false;
        } else {
            comment.updateComment(requestDto);
            commentRepository.save(comment);
        }
        return true;
    }
}
