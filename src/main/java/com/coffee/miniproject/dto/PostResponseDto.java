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

    private String title;

    private String nickname;

    private LocalDateTime date;

    private String img;

    // 카테고리도 true false로 넘기기.
    private boolean category;

    public PostResponseDto(Post post){
        this.title = post.getTitle();
        this.nickname = post.getNickname();
        this.date = post.getModifiedAt();
        this.img = post.getImg();

        this.category = post.getCategory().name().equals("RECIPE");
    }

}
