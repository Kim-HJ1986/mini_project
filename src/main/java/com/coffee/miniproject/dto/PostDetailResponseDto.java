package com.coffee.miniproject.dto;

import com.coffee.miniproject.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostDetailResponseDto {
    private Long id;

    private String title;

    private String contents;

    private String nickname;

    private String img;

    private LocalDateTime date;

    private boolean category;

    public PostDetailResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.nickname = post.getNickname();
        this.img = post.getImg();
        this.date = post.getModifiedAt();

        this.category = post.getCategory().name().equals("RECIPE");
    }
}
