package com.coffee.miniproject.service;

import com.coffee.miniproject.dto.CommentRequestDto;
import com.coffee.miniproject.model.Comment;
import com.coffee.miniproject.repository.CommentRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

public class CommentService {

    // 댓글 수정
/*    @Transactional
    public String update(Long id, ReplyRequestDto requestDto, String username, Long userId) {
        Reply reply = ReplyRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않습니다."));
        Long writerId = reply.getUserId();
        if (Objects.equals(writerId, userId)) {
            reply.update(requestDto);
            return "댓글 수정 완료";
        } return "작성한 유저가 아닙니다.";
    }*/

    // 코멘트 업데이트
    public String save(Long id, CommentRequestDto requestDto) {
        Comment comment = CommentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("글이 존재하지 않습니다.")
        );
        comment = new Comment(requestDto, )


    }
}
