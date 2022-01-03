package com.linecorp.kotlinjdsl.eclipselink.example.entity

import javax.persistence.*

@Entity
@Table(name = "book")
data class Book(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column
    val name: String,
)
