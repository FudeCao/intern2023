package com.example.project3.classes;

import javax.persistence.*;
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String gender;
    private Integer age;
    private String position;

    private String username;
    private String password;


    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public String getPosition(){
        return position;
    }

    public String getUsername() {
        return username;
    }

}

