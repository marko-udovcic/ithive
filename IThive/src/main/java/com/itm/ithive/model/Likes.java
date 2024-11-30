package com.itm.ithive.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Likes{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",  nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "blog_id", referencedColumnName = "id",  nullable = false)
    private Blog blog;

}