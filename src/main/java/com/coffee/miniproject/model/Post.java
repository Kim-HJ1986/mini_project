package com.coffee.miniproject.model;

import com.coffee.miniproject.dto.PostRequestDto;
import com.coffee.miniproject.dto.PostRequestDto4Put;
import com.coffee.miniproject.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Post extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    // ERD 누락된 column!
    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private PostCategory category;

    @Column()
    private String img;

    // FK로 memberId 들어옴.
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    // 자바 객체 사이드에서만 저장됨 (DB에 저장안됨)
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    // postRequestDto 받는 생성자
    public Post(PostRequestDto requestDto, String nickname){
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.nickname = nickname;
        this.img = requestDto.getImg();

        if(requestDto.isCategory()){
            this.category = PostCategory.RECIPE;
        }else{
            this.category = PostCategory.CAFE;
        }

    }

    public void updatePost(PostRequestDto4Put requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }
}
