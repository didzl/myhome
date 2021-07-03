package com.example.myhome.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Board {
    @Id//프라이머리 키 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY)//오토 인크리먼트 사용시
    private Long id;
    private String title;
    private String content;
}
