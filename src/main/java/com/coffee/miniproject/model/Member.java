package com.coffee.miniproject.model;


import com.coffee.miniproject.util.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Member extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Transient
    private String passwordCheck;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private UserRole userRole;

    @Column
    private String provider;

    @JsonIgnore
    @Column
    // 자바 객체 사이드에서만 저장됨 (DB에 저장안됨)
    @OneToMany(mappedBy = "member")
    private List<Post> posts;

    @JsonIgnore
    // 자바 객체 사이드에서만 저장됨 (DB에 저장안됨)
    @OneToMany(mappedBy = "member")
    private List<Comment> comments;


    public Member(String username, String nickname, String password, UserRole userRole) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.userRole = userRole;
    }

    public Member(String username, String password, String nickname, UserRole userRole, String provider) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.userRole = userRole;
        this.provider = provider;
    }

    @Builder
    public Member(String username, String password, UserRole userRole, String nickname) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
        this.nickname = nickname;
    }
}

