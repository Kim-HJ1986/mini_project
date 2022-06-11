package com.coffee.miniproject.repository;

import com.coffee.miniproject.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.test.annotation.Commit;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
}