package com.coffee.miniproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MemberLikeDto {

    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    private String username;

    private Long postId;
    public MemberLikeDto(String username, Long postId){
        this.username = username;
        this.postId = postId;
    }

}
