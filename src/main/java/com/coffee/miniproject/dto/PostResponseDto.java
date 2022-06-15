package com.coffee.miniproject.dto;

import com.coffee.miniproject.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {
    private Long id;

    private String title;

    private String contents;

    private String nickname;

    private LocalDateTime date;

    private String img;

    // 카테고리도 true false로 넘기기.
    private boolean category;

    public PostResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.nickname = post.getNickname();
        this.date = post.getModifiedAt();
        this.img = post.getImg();

        this.category = post.getCategory().name().equals("RECIPE");
    }

}
