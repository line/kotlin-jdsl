package com.linecorp.kotlinjdsl.spring.data.example.entity

import jakarta.persistence.*

@Entity
@Table(name = "book")
data class Book(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column
    val name: String,
) {
    fun toJson() = """{"id":$id,"name":"$name"}"""
}
