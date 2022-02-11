package com.linecorp.kotlinjdsl.test.entity.test

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "test_table")
class TestTable(
    @Id
    @Column(name = "sequence")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val sequence: Long = 0,

    @Column(name = "id")
    val id: Long,

    @Column(name = "role")
    val role: String,

    @Column(name = "amount")
    val occurAmount: BigDecimal,
)
