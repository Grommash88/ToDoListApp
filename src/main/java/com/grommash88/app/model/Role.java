package com.grommash88.app.model;

import javax.persistence.*;
import java.util.Set;
import lombok.Data;

//@Entity
//@Table(name = "role")
//@Data
public enum Role {
    USER;
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//
//    @ManyToMany(mappedBy = "roles")
//    private Set<User> users;
}
