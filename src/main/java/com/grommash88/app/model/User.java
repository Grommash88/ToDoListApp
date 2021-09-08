package com.grommash88.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.*;
import java.util.Set;
import lombok.Data;

@Entity
@Data
@Table(name = "usr", indexes = {
    @Index(name = "usrnameindex", columnList = "username", unique = true)})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    @Transient
    private String passwordConfirm;

    @ManyToMany
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    @JsonIgnore
    private List<Task> tasks;
}
