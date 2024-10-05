package com.example.project3.classes;

import javax.persistence.*;
@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer number;

    public void setId(Long id){
        id=id;
    }
    public void changeNumber(Integer change){
        number=number+change;
    }
}
