package com.coffee.miniproject.model;

import com.coffee.miniproject.dto.CommentRequestDto;
import com.coffee.miniproject.repository.CommentRepository;
import com.coffee.miniproject.util.Timestamped;
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

    @Column(nullable = false)
    private String nickname;

    // FK로 MEMBER_ID 들어옴.
    @ManyToOne //ID 유저네임? 그 이아디?
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    // FK로 POST_ID 들어옴.
    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;

    public Comment(CommentRequestDto requestDto ,Post post, String contents, String nickname) {
        this.member = requestDto.getPost().getMember();
        this.contents = contents;
        this.nickname = nickname;
    }

    public Comment(CommentRequestDto requestDto){
        this.post = requestDto.getPost();
        this.nickname = requestDto.getNickname();
        this.contents = requestDto.getContents();
    }
}



// pk comment created modified fk fk || nickname -> MEMBER_ID.nickname

// public Comment(Member member){ this.member = member; this.nickname = member.getNickname(); }