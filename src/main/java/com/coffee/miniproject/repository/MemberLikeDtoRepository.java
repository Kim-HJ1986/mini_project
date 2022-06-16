package com.coffee.miniproject.repository;

import com.coffee.miniproject.dto.MemberLikeDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberLikeDtoRepository extends JpaRepository<MemberLikeDto, Long> {

    MemberLikeDto findByUsername(String username);
}
