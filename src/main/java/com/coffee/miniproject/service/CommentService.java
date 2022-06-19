package com.coffee.miniproject.service;

import com.coffee.miniproject.dto.CommentRequestDto;
import com.coffee.miniproject.dto.CommentRequestDto4Put;
import com.coffee.miniproject.dto.CommentResponseDto;
import com.coffee.miniproject.model.Comment;
import com.coffee.miniproject.model.CommentLike;
import com.coffee.miniproject.model.Member;
import com.coffee.miniproject.model.Post;
import com.coffee.miniproject.repository.CommentLikeRepository;
import com.coffee.miniproject.repository.CommentRepository;
import com.coffee.miniproject.repository.MemberRepository;
import com.coffee.miniproject.repository.PostRepository;
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

    private final CommentLikeRepository commentLikeRepository;

    // 댓글 등록
    @Transactional
    public void saveNewComments(Long postid, String memberProxy, CommentRequestDto requestDtoList) {
        Member member = memberRepository.findByUsername(memberProxy)
                .orElseThrow(RuntimeException::new);
        Post post = postRepository.findById(postid)
                .orElseThrow(RuntimeException::new);

        Comment comment = requestDtoList.toEntity();
        comment.registCommentInfo(post, member);
        commentRepository.save(comment);
    }


    // 댓글 조회
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
    public Boolean deleteComment(Long id, String member){
        // comment내의 memberid와 로그인한 member아이디 일치하는지 확인
        Comment commentByCommentId =  commentRepository.findById(id).get();
        if (!Objects.equals(member, commentByCommentId.getMember().getUsername())) {
            return false;
            //throw new RuntimeException("댓글을 삭제하려는 유저의 아이디가 작성자의 아이디와 일치하지 않습니다.");
        }else {commentRepository.deleteById(id);}
        return true;
    }

    // 댓글 수정
    public Boolean updateComment(Long id, CommentRequestDto4Put requestDto, String member) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );
        // comment내의 memberid와 로그인한 member아이디 일치하는지 확인
        Comment commentByCommentId = commentRepository.findById(id).get();

        if (!Objects.equals(member, comment.getMember().getUsername())){
            return false;
        } else {
            comment.updateComment(requestDto);
            commentRepository.save(comment);
        }
        return true;
    }

    // 댓글 좋아요
    public void likePost(Long commentid, String username) {
        Comment comment = commentRepository.findById(commentid).orElseThrow(
                () -> new RuntimeException("존재하지 않는 댓글입니다.")
        );

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(RuntimeException::new);
        // Member가 기존에 Comment에 좋아요를 했는지 확인
       // CommentLike commentLike = requestDto.toEntity;
        CommentLike commentLikeByMemberId = commentLikeRepository.findByMemberIdAndCommentId(member.getId(), commentid);
        if (commentLikeByMemberId == null){
            // 1. commentLikeByMemberId 가 null인지 확인 -> member가 리플에 좋아요 했는지 판별
            // 2. CommentLike라는 클래스를 선언
            // 3. 내부에 CommentLike 내부에는 registCommentLikeInfo로 실질적으로 받아올
            // 좋아요할 comment의 fk값과 member의 fk값을 저장
            // 4. 레포지토리에 의해 DB에 저장
            // 5. 1 검증과성에서 있는 경우 DB에서 삭제
            CommentLike commentLike = new CommentLike();
            commentLike.registCommentLikeInfo(comment, member);
            commentLikeRepository.save(commentLike);
        }else{
            //delete
            // 주의 할 점, Dto는 데이터를 담는 작은 자바 파일
            commentLikeRepository.delete(commentLikeByMemberId);
        }
    }
}
