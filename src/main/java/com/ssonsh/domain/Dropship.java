package com.ssonsh.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
// 가급적이면 롬복의 data를 쓰지말자. 다만, 연관관계가 없기 때문에 마음편히 쓰는 것도 방법 ㅎ
@Data
public class Dropship {

    @Id @GeneratedValue
    private Integer id;

    private String github;

    // 이름
    @NotEmpty
    private String name;

    // 주소
    @NotEmpty
    private String address;

    // 우편번호
    @NotEmpty
    private String zipCode;

    // 연락처
    @NotEmpty
    private String contactNumber;

    // 부재시 요청사항
    private String memo;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Size size;
}
