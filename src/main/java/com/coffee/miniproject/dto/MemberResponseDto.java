package com.coffee.miniproject.dto;

import com.coffee.miniproject.model.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private String username;

    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(member.getUsername());
    }
}
