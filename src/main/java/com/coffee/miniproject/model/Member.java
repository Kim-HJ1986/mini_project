package com.coffee.miniproject.model;

import com.coffee.miniproject.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Member extends Timestamped {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Transient
    private String passwordCheck;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private UserRole userRole;

    // 자바 객체 사이드에서만 저장됨 (DB에 저장안됨)
    @OneToMany(mappedBy = "member")
    private List<Post> posts;

    // 자바 객체 사이드에서만 저장됨 (DB에 저장안됨)
    @OneToMany(mappedBy = "member")
    private List<Comment> comments;


}
