package com.example.myhome.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class Board {
    @Id//프라이머리 키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)//오토 인크리먼트 사용시
    private Long id;
    @NotNull
    @Size(min=2, max=30, message = "제목은 2자이상 30자 이하입니다.")
    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")//유저 테이블의 아이디는 프라이머리 키라 생략 가능
    @JsonIgnore
    private User user;
}
