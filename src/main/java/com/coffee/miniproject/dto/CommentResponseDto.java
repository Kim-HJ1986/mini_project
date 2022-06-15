package com.coffee.miniproject.dto;

import com.coffee.miniproject.model.Comment;
import com.coffee.miniproject.model.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CommentResponseDto {

    private Long id;
    private String username;
    private String contents;
    private String nickname;
    private LocalDateTime date;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getMember().getUsername();
        this.contents = comment.getContents();
        this.nickname = comment.getMember().getNickname();
        this.date = comment.getModifiedAt();
    }
}
