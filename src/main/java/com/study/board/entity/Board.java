package com.study.board.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
//mysql에 연동하여 사용하는 테이블
public class Board {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    //datetype column name
    private Integer id;

    private  String title;

    private String content;

    private String filename;

    private String filepath;
}
