package com.linecorp.kotlinjdsl.benchmark.sample.entity

import com.linecorp.kotlinjdsl.benchmark.sample.entity.author.Author
import com.linecorp.kotlinjdsl.benchmark.sample.entity.book.Book
import com.linecorp.kotlinjdsl.benchmark.sample.entity.book.BookAuthor
import com.linecorp.kotlinjdsl.benchmark.sample.entity.book.BookPublisher
import com.linecorp.kotlinjdsl.benchmark.sample.entity.department.Department
import com.linecorp.kotlinjdsl.benchmark.sample.entity.employee.Employee
import com.linecorp.kotlinjdsl.benchmark.sample.entity.employee.FullTimeEmployee
import com.linecorp.kotlinjdsl.benchmark.sample.entity.employee.PartTimeEmployee
import com.linecorp.kotlinjdsl.benchmark.sample.entity.publisher.Publisher
import kotlin.reflect.KClass

object Entities {
    private val classes = listOf(
        Employee::class, // 0
        FullTimeEmployee::class, // 1
        PartTimeEmployee::class, // 2
        Author::class, // 3
        Book::class, // 4
        BookAuthor::class, // 5
        BookPublisher::class, // 6
        Department::class, // 7
        Publisher::class, // 8
    )

    fun getClass(index: Int): KClass<*> {
        return classes[index]
    }
}
