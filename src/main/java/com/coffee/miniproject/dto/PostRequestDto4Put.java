package com.coffee.miniproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto4Put {

    private String title;
    private String contents;
    private String img;
    private boolean category;
}
