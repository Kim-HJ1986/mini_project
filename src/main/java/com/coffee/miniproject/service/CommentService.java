package com.coffee.miniproject.service;

import com.coffee.miniproject.dto.CommentRequestDto;
import com.coffee.miniproject.model.Comment;
import com.coffee.miniproject.model.Member;
import com.coffee.miniproject.model.Post;
import com.coffee.miniproject.repository.CommentRepository;
import com.coffee.miniproject.repository.MemberRepository;
import com.coffee.miniproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void saveNewComment(Long postid, Long memberid, CommentRequestDto requestDtoList) {
        Member member = memberRepository.findById(memberid)
                .orElseThrow(RuntimeException::new);
        Post post = postRepository.findById(postid)
                .orElseThrow(RuntimeException::new);

        Comment comment = requestDtoList.toEntity();
        comment.registCommentInfo(post, member);
        commentRepository.save(comment);
    }

    //댓글 등록
}
