package com.example.myhome.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private Boolean enabled;

    //조인 설정
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name= "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )

    private List<Role> roles = new ArrayList<>();

//    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, orphanRemoval = false)//보드 와의 양방향 매핑
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)//tomany일땐 lazy, toOne일땐 eager
    private List<Board> boards = new ArrayList<>();
}
