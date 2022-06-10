package com.coffee.miniproject.model;

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

    @Column(nullable = false)
    private String imgPath;

    // FK로 memberId 들어옴.
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    // 자바 객체 사이드에서만 저장됨 (DB에 저장안됨)
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}
