package com.coffee.miniproject.model;

<<<<<<< HEAD
import com.coffee.miniproject.dto.SignupDto;
import com.coffee.miniproject.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
=======
import com.coffee.miniproject.util.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
>>>>>>> 1c28ef9 ([etc]First Commit)
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

<<<<<<< HEAD
=======
    @Transient
    private String passwordCheck;

>>>>>>> 1c28ef9 ([etc]First Commit)
    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private UserRole userRole;

<<<<<<< HEAD
    @Column
    private String provider;

    @Column

=======
>>>>>>> 1c28ef9 ([etc]First Commit)
    // 자바 객체 사이드에서만 저장됨 (DB에 저장안됨)
    @OneToMany(mappedBy = "member")
    private List<Post> posts;

    // 자바 객체 사이드에서만 저장됨 (DB에 저장안됨)
    @OneToMany(mappedBy = "member")
    private List<Comment> comments;

<<<<<<< HEAD
    public Member(String username, String nickname, String password, UserRole userRole){
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
=======

>>>>>>> 1c28ef9 ([etc]First Commit)
}
