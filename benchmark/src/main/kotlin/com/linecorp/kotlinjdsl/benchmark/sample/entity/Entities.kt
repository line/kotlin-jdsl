package com.linecorp.kotlinjdsl.benchmark.sample.entity

import com.linecorp.kotlinjdsl.benchmark.entity.author.Author
import com.linecorp.kotlinjdsl.benchmark.entity.book.Book
import com.linecorp.kotlinjdsl.benchmark.entity.book.BookAuthor
import com.linecorp.kotlinjdsl.benchmark.entity.book.BookPublisher
import com.linecorp.kotlinjdsl.benchmark.entity.department.Department
import com.linecorp.kotlinjdsl.benchmark.entity.employee.Employee
import com.linecorp.kotlinjdsl.benchmark.entity.employee.FullTimeEmployee
import com.linecorp.kotlinjdsl.benchmark.entity.employee.PartTimeEmployee
import com.linecorp.kotlinjdsl.benchmark.sample.entity.publisher.Publisher
import kotlin.reflect.KClass

object Entities {
    private val classes = listOf(
        Employee::class,
        FullTimeEmployee::class,
        PartTimeEmployee::class,
        Author::class,
        Book::class,
        BookAuthor::class,
        BookPublisher::class,
        Department::class,
        Publisher::class,
    )

    fun getClass(index: Int): KClass<*> {
        return classes[index]
    }
}
