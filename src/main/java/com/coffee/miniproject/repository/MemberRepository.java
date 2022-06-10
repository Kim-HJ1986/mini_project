package com.coffee.miniproject.repository;

import com.coffee.miniproject.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
