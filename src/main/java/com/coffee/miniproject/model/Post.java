package com.coffee.miniproject.model;

import com.coffee.miniproject.dto.MemberLikeDto;
import com.coffee.miniproject.dto.PostRequestDto;
import com.coffee.miniproject.dto.PostRequestDto4Put;
import com.coffee.miniproject.security.UserDetailsImpl;
import com.coffee.miniproject.util.Timestamped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
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

    @Column
    private Long likeCnt;

    @JsonIgnore
    @ManyToMany
    private List<MemberLikeDto> likeMembers = new ArrayList<>();

    // FK로 memberId 들어옴.
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    // 자바 객체 사이드에서만 저장됨 (DB에 저장안됨)
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;


    // postRequestDto 받는 생성자
    public Post(PostRequestDto requestDto, Member member){
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.nickname = member.getNickname();
        this.img = requestDto.getImg();
        this.member = member;
        this.likeCnt = 0L;
        // post 등록될 때 작성자의 게시글 리스트에도 this를 추가해준다!
        member.getPosts().add(this);

        if(requestDto.isCategory()){
            this.category = PostCategory.RECIPE;
        }else{
            this.category = PostCategory.CAFE;
        }

    }

    public void setMember(UserDetailsImpl userDetails){
        this.member = userDetails.getUser();
    }

    public void updatePost(PostRequestDto4Put requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.img = requestDto.getImg();

        if(requestDto.isCategory()){
            this.category = PostCategory.RECIPE;
        }else{
            this.category = PostCategory.CAFE;
        }
    }

}
