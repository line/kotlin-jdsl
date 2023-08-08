package com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.update

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.entity.department.Department
import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.entity.employee.Employee
import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.entity.employee.EmployeeDepartment
import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.entity.employee.EmployeeSalary
import com.linecorp.kotlinjdsl.example.jpql.spring.jpa.javax.entity.employee.FullTimeEmployee
import com.linecorp.kotlinjdsl.executor.spring.jpa.javax.createQuery
import com.linecorp.kotlinjdsl.render.jpql.JpqlRenderContext
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import javax.persistence.EntityManager

@Transactional
@SpringBootTest
class UpdateExample : WithAssertions {
    @Autowired
    private lateinit var context: JpqlRenderContext

    @Autowired
    private lateinit var entityManager: EntityManager

    @Test
    fun increase_the_annual_salaries_of_employees_in_department_03_by_10_percent() {
        // given
        val query = jpql {
            val employeeIds = select<Long>(
                path(EmployeeDepartment::employee)(Employee::employeeId),
            ).from(
                entity(Department::class),
                join(EmployeeDepartment::class)
                    .on(path(Department::departmentId).equal(path(EmployeeDepartment::departmentId))),
            ).where(
                path(Department::name).like("%03"),
            ).asSubquery()

            update(
                FullTimeEmployee::class,
            ).set(
                path(FullTimeEmployee::annualSalary)(EmployeeSalary::value),
                path(FullTimeEmployee::annualSalary)(EmployeeSalary::value).times(BigDecimal.valueOf(1.1)),
            ).where(
                path(FullTimeEmployee::employeeId).`in`(employeeIds),
            )
        }

        // when
        entityManager.createQuery(query, context).executeUpdate()

        data class Row(
            val employeeId: Long,
            val annualSalary: EmployeeSalary,
        )

        val employeeAnnualSalaries = jpql {
            selectNew<Row>(
                path(FullTimeEmployee::employeeId),
                path(FullTimeEmployee::annualSalary),
            ).from(
                entity(FullTimeEmployee::class),
            ).orderBy(
                path(FullTimeEmployee::employeeId).asc(),
            )
        }

        val actual = entityManager.createQuery(employeeAnnualSalaries, context)

        // then
        assertThat(actual.resultList).isEqualTo(
            listOf(
                Row(16, EmployeeSalary(1600)),
                Row(17, EmployeeSalary(1700)),
                Row(18, EmployeeSalary(1800)),
                Row(19, EmployeeSalary(1900)),
                Row(20, EmployeeSalary(2000)),
                Row(21, EmployeeSalary(2310)),
                Row(22, EmployeeSalary(2200)),
                Row(23, EmployeeSalary(2530)),
                Row(24, EmployeeSalary(2640)),
                Row(25, EmployeeSalary(2750)),
                Row(26, EmployeeSalary(2860)),
                Row(27, EmployeeSalary(2970)),
                Row(28, EmployeeSalary(3080)),
                Row(29, EmployeeSalary(3190)),
                Row(30, EmployeeSalary(3300)),
            ),
        )
    }

    @Test
    fun set_the_nickname_to_the_name_for_employees_in_department_03_who_do_not_have_nicknames() {
        // given
        val query = jpql {
            val employeeIds = select<Long>(
                path(EmployeeDepartment::employee)(Employee::employeeId),
            ).from(
                entity(Department::class),
                join(EmployeeDepartment::class)
                    .on(path(Department::departmentId).equal(path(EmployeeDepartment::departmentId))),
            ).where(
                path(Department::name).like("%03"),
            ).asSubquery()

            update(
                Employee::class,
            ).set(
                path(Employee::nickname),
                path(Employee::name),
            ).whereAnd(
                path(Employee::nickname).isNull(),
                path(Employee::employeeId).`in`(employeeIds),
            )
        }

        // when
        entityManager.createQuery(query, context).executeUpdate()

        data class Row(
            val employeeId: Long,
            val nickname: String?,
        )

        val employeeNicknames = jpql {
            selectNew<Row>(
                path(Employee::employeeId),
                path(Employee::nickname),
            ).from(
                entity(Employee::class),
            ).orderBy(
                path(Employee::employeeId).asc(),
            )
        }

        val actual = entityManager.createQuery(employeeNicknames, context)

        // then
        assertThat(actual.resultList).isEqualTo(
            listOf(
                Row(1, "Employee01"),
                Row(2, null),
                Row(3, null),
                Row(4, null),
                Row(5, null),
                Row(6, null),
                Row(7, "Employee07"),
                Row(8, "Nickname08"),
                Row(9, "Nickname09"),
                Row(10, "Nickname10"),
                Row(11, "Nickname11"),
                Row(12, "Nickname12"),
                Row(13, "Nickname13"),
                Row(14, "Nickname14"),
                Row(15, "Nickname15"),
                Row(16, null),
                Row(17, null),
                Row(18, null),
                Row(19, null),
                Row(20, null),
                Row(21, "Employee21"),
                Row(22, null),
                Row(23, "Employee23"),
                Row(24, "Nickname24"),
                Row(25, "Nickname25"),
                Row(26, "Nickname26"),
                Row(27, "Nickname27"),
                Row(28, "Nickname28"),
                Row(29, "Nickname29"),
                Row(30, "Nickname30"),
            ),
        )
    }
}
