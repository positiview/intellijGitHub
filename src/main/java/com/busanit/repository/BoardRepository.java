package com.busanit.repository;


import com.busanit.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

// 1. JPA
// find + (엔티티 이름) + By + 변수이름
// findByAll - 전체에 대한 select
// OrderBy + 속성명 + Asc or Desc ex) findByOrderByBnoDesc()

// 2. @Query
// JPQL(Java Persistence Query Language)
// - SQL 문법과 유사

// 3. Querydsl(JPQL을 코드로 작성 할 수 있도록 도와주는 빌더 API)
/*
    장점
    - 고정된 SQL문이 아닌 조건에 맞게 동적으로 쿼리를 생성할 수 있다.
    - 비슷한 쿼리를 재사용할 수 있으며 가독성을 향상시킬 수 있다.
    - 문자열이 아닌 자바 소스코드로 작성하기 때문에 컴파일 시점에 오류를 발견 할 수 있다.
    - IDE의 도움을 받아서 자동완성기능을 이용할 수 있기 때문에 생산성을 향상시킬 수 있다.
*/
public interface BoardRepository extends JpaRepository<Board, Long> {

    // List<Board> findByTitle(String title);

    @Query("select i from Board i where i.title like %:title% order by i.bno desc")
    List<Board> findByTitle(@Param("title") String title);

    Board findByBno(long bno);

    Page<Board> findByTitleContaining(String keyword, Pageable pageable);

    Page<Board> findByContentContaining(String keyword, Pageable pageable);
}

