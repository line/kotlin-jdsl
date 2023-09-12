package com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.delete

import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.book.Book
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.book.Isbn
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.department.Department
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.employee.Employee
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.repository.book.BookRepository
import com.linecorp.kotlinjdsl.example.spring.data.jpa.jpql.repository.employee.EmployeeRepository
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime

@Transactional
@SpringBootTest
class DeleteExample : WithAssertions {
    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var employeeRepository: EmployeeRepository

    @Test
    fun `delete all books published after June 2023`() {
        // when
        bookRepository.delete {
            deleteFrom(
                entity(Book::class),
            ).where(
                path(Book::publishDate).ge(OffsetDateTime.parse("2023-06-01T00:00:00+09:00")),
            )
        }

        val actual = bookRepository.findAll().map { it.isbn }

        // then
        assertThat(actual).isEqualTo(
            listOf(
                Isbn("01"),
                Isbn("02"),
                Isbn("03"),
                Isbn("04"),
                Isbn("05"),
            ),
        )
    }

    @Test
    fun `delete the employees from department 03`() {
        // when
        employeeRepository.delete {
            val employeeIds = select<Long>(
                path(EmployeeDepartment::employee)(Employee::employeeId),
            ).from(
                entity(Department::class),
                join(EmployeeDepartment::class)
                    .on(path(Department::departmentId).equal(path(EmployeeDepartment::departmentId))),
            ).where(
                path(Department::name).like("%03"),
            ).asSubquery()

            deleteFrom(
                entity(Employee::class),
            ).where(
                path(Employee::employeeId).`in`(employeeIds),
            )
        }

        val actual = employeeRepository.findAll().map { it.employeeId }

        // then
        assertThat(actual).isEqualTo(
            listOf(
                2L,
                3L,
                4L,
                5L,
                6L,
                15L,
                16L,
                17L,
                18L,
                19L,
                20L,
                22L,
            ),
        )
    }
}
