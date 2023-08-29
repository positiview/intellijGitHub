package com.busanit.entity;

import com.busanit.constant.Role;
import com.busanit.domain.MemberFormDto;
import com.busanit.domain.MemberSecurityDTO;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.Valid;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member extends BaseEntity{

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String address;

    // Role 값을 String 값으로 저장
    @Enumerated(EnumType.STRING)
    private Role role;
    private boolean social;

    public static Member createMember(MemberFormDto memberFormDTO, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDTO.getName());
        member.setEmail(memberFormDTO.getEmail());


        String password = passwordEncoder.encode(memberFormDTO.getPassword());

        member.setPassword(password);
        member.setRole(Role.USER);
        member.setSocial(false);  //false 이면 일반회원 true 이면 소셜 회원!

        return member;
    }


}
