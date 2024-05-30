package com.example.cih.domain.member;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    @EntityGraph(attributePaths = "roleSet")    // 같이 로딩해야 하는 속성을 명시
    @Query("select m from Member m where m.memberId = :memberId and m.isSocial = false")
    Optional<Member> getWithRoles(@Param("memberId") String memberId);


}
