package com.busanit.entity;

import com.busanit.domain.MemberFormDto;
import com.busanit.repository.CartRrepository;
import com.busanit.repository.MemberRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")

class CartTest {

    @Autowired
    private CartRrepository cartRrepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;
    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울");
        memberFormDto.setPassword("1234");

        return Member.createMember(memberFormDto, passwordEncoder);
    }

    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
    public void findCarAndMemberTest(){
        Member member = createMember();
        memberRepository.save(member);

        Cart cart = new Cart();
        cart.setMember(member);
        cartRrepository.save(cart);

        em.flush(); // 강제로 데이터베이스에 반영
        em.clear(); // 영속성 컨텍스트 비워줌

        // 저장된 장바구니 엔티티 조회
        Cart savedCart = cartRrepository.findById(cart.getId())
                .orElseThrow(EntityNotFoundException::new);

        // saveCart의 id값과 member 엔티티의 id값을 비교
        assertEquals(savedCart.getMember().getId(), member.getId());
    }
}