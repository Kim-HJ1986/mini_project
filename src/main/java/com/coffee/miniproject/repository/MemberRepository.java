package com.coffee.miniproject.repository;

import com.coffee.miniproject.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< HEAD
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);
=======
public interface MemberRepository extends JpaRepository<Member, Long> {
>>>>>>> 1c28ef9 ([etc]First Commit)
}
