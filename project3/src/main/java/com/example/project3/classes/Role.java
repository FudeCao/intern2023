package com.example.project3.classes;
import javax.persistence.*;
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long permissionid;

    public void setId(Long id){
        id=id;
    }

    public String getname() {
        return name;
    }
}
