package com.coffee.miniproject.dto;

import com.coffee.miniproject.model.Member;
import com.coffee.miniproject.model.Post;
import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Post post;
    private String contents;
    private String nickname;
}
