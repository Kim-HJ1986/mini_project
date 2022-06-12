package com.coffee.miniproject.model;

import com.coffee.miniproject.dto.CommentRequestDto;
import com.coffee.miniproject.dto.CommentRequestDto4Put;
import com.coffee.miniproject.repository.CommentRepository;
import com.coffee.miniproject.util.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Comment extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String contents;

    // @Column(nullable = false)
    // private String nickname;

    // FK로 MEMBER_ID 들어옴.
    @ManyToOne //ID 유저네임? 그 이아디?
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    // FK로 POST_ID 들어옴.
    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    @Builder
    public Comment(String contents, Post post, Member member) {
        this.contents = contents;
    }

    public void registCommentInfo(Post post, Member member) {
        this.post = post;
        this.member = member;
    }
    public void updateComment(CommentRequestDto4Put requestDto) {
        this.contents = requestDto.getContents();
    }
}



// pk comment created modified fk fk || nickname -> MEMBER_ID.nickname

// public Comment(Member member){ this.member = member; this.nickname = member.getNickname(); }