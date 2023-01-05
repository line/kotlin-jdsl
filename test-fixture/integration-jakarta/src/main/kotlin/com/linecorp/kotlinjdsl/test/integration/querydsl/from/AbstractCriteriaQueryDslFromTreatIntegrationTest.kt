package com.linecorp.kotlinjdsl.test.integration.querydsl.from

import com.linecorp.kotlinjdsl.listQuery
import com.linecorp.kotlinjdsl.query.spec.expression.ColumnSpec
import com.linecorp.kotlinjdsl.query.spec.expression.EntitySpec
import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.querydsl.from.treat
import com.linecorp.kotlinjdsl.subquery
import com.linecorp.kotlinjdsl.test.entity.employee.*
import com.linecorp.kotlinjdsl.test.integration.AbstractCriteriaQueryDslIntegrationTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal


/**
 * implement https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/criteria-api-downcasting-with-treat-method.html
 */
abstract class AbstractCriteriaQueryDslFromTreatIntegrationTest : AbstractCriteriaQueryDslIntegrationTest() {
    private val persistProjects = createProjects()

    @BeforeEach
    fun setUp() {
        entityManager.persistAll(persistProjects)
        entityManager.flushAndClear()
    }

    @Test
    fun getByPartTimeEmployeesWeeklySalary() {
        // when
        val employees = queryFactory.listQuery<Employee> {
            selectDistinct(entity(Employee::class))
            from(entity(Employee::class))
            treat<Employee, PartTimeEmployee>()
            where(
                or(
                    col(PartTimeEmployee::weeklySalary).lessThan(1000.toBigDecimal()),
                )
            )
        }

        // then
        assertThat(employees).containsExactlyInAnyOrder(
            *persistProjects.asSequence().flatMap { it.employees + it.supervisor }
                .filter { it is PartTimeEmployee }
                .filter { (it as PartTimeEmployee).weeklySalary < 1000.toBigDecimal() }
                .toList().toTypedArray()
        )
    }

    @Test
    fun getByPartTimeAndContractEmployeesBySalary() {
        // when
        val employees = queryFactory.listQuery<Employee> {
            selectDistinct(entity(Employee::class))
            from(entity(Employee::class))
            treat<Employee, PartTimeEmployee>()
            treat<Employee, ContractEmployee>()
            where(
                or(
                    col(PartTimeEmployee::weeklySalary).greaterThan(1000.toBigDecimal()),
                    col(ContractEmployee::hourlyRate).lessThan(75.toBigDecimal()),
                )
            )
        }

        // then
        assertThat(employees).containsExactlyInAnyOrder(
            *persistProjects.asSequence().flatMap { it.employees + it.supervisor }
                .filter {
                    (it as? PartTimeEmployee)?.weeklySalary
                        ?.let { weeklySalary -> weeklySalary > 1000.toBigDecimal() } ?: false ||
                        (it as? ContractEmployee)?.hourlyRate
                            ?.let { hourlyRate -> hourlyRate < 75.toBigDecimal() } ?: false
                }
                .toList().toTypedArray()
        )
    }

    @Test
    open fun getProjectByFullTimeEmployeesSalarySelectFullTimeEmployee() {
        // when
        val employees = queryFactory.listQuery {
            val project: EntitySpec<Project> = Project::class.alias("project")
            val fullTimeEmployee = FullTimeEmployee::class.alias("fe")
            val employee = Employee::class.alias("e")

            selectDistinct(fullTimeEmployee)
            from(project)
            treat(ColumnSpec<FullTimeEmployee>(project, Project::employees.name), employee, fullTimeEmployee)
            where(
                ColumnSpec<BigDecimal>(fullTimeEmployee, FullTimeEmployee::annualSalary.name)
                    .greaterThan(100000.toBigDecimal())
            )
        }

        // then
        assertThat(employees).containsExactlyInAnyOrder(*persistProjects.asSequence().flatMap { it.employees }
            .filter { it is FullTimeEmployee }
            .filter { (it as FullTimeEmployee).annualSalary > 100000.toBigDecimal() }
            .map { it as FullTimeEmployee }
            .toList().toTypedArray())
    }

    @Test
    fun getProjectByFullTimeEmployeesSalaryWithFetch() {
        // when
        val projects = queryFactory.listQuery<Project> {
            selectDistinct(entity(Project::class))
            from(entity(Project::class))

            treat<Employee, FullTimeEmployee>(col(Project::employees))
            fetch(entity(Project::class), entity(Employee::class, "emp"), on(Project::supervisor))
            where(
                col(FullTimeEmployee::annualSalary).greaterThan(100000.toBigDecimal())
            )
        }

        // then
        assertThat(projects).containsExactlyInAnyOrder(*persistProjects.asSequence()
            .filter { it.employees.any { e -> e is FullTimeEmployee && e.annualSalary > 100000.toBigDecimal() } }
            .toList().toTypedArray())
    }

    @Test
    fun getProjectByFullTimeEmployeesSalaryAndPartTimeEmployeeWeeklySalaryWithFetch() {
        // when
        val projects = queryFactory.listQuery<Project> {
            selectDistinct(entity(Project::class))
            from(entity(Project::class))

            treat<Employee, FullTimeEmployee>(col(Project::employees))
            treat<Employee, PartTimeEmployee>(col(Project::employees))
            fetch(entity(Project::class), entity(Employee::class, "emp"), on(Project::supervisor))
            where(
                or(
                    col(FullTimeEmployee::annualSalary).greaterThan(100000.toBigDecimal()),
                    col(PartTimeEmployee::weeklySalary).greaterThan(1000.toBigDecimal()),
                )
            )
        }

        // then
        assertThat(projects).containsExactlyInAnyOrder(*persistProjects.asSequence()
            .filter {
                it.employees.any { e -> e is FullTimeEmployee && e.annualSalary > 100000.toBigDecimal() }
                    || it.employees.any { e -> e is PartTimeEmployee && e.weeklySalary > 1000.toBigDecimal() }
            }
            .toList().toTypedArray())
    }

    @Test
    fun getProjectsWithSupervisorSalaryEqualBySubqueryFullTimeEmployeesSalaryAndPartTimeEmployeeWeeklySalary() {
        // when
        getProjectsWithSupervisorSalaryEqualBySubqueryFullTimeEmployeesSalaryAndPartTimeEmployeeWeeklySalary(false)
    }

    @Test
    open fun getProjectsWithSupervisorSalaryEqualBySubqueryFullTimeEmployeesSalaryAndPartTimeEmployeeWeeklySalaryWithFetch() {
        getProjectsWithSupervisorSalaryEqualBySubqueryFullTimeEmployeesSalaryAndPartTimeEmployeeWeeklySalary(true)
    }

    private fun getProjectsWithSupervisorSalaryEqualBySubqueryFullTimeEmployeesSalaryAndPartTimeEmployeeWeeklySalary(fetch: Boolean) {
        // when
        val sub = queryFactory.subquery {
            select(col(Project::id))
            from(entity(Project::class))

            treat<Employee, FullTimeEmployee>(col(Project::employees))
            treat<Employee, PartTimeEmployee>(col(Project::employees))
            where(
                or(
                    col(FullTimeEmployee::annualSalary).greaterThan(100000.toBigDecimal()),
                    col(PartTimeEmployee::weeklySalary).greaterThan(1000.toBigDecimal()),
                )
            )
        }
        val projects = queryFactory.listQuery {
            val project = Project::class.alias("dedupeProject")
            selectDistinct(project)
            from(project)
            val supervisor = Employee::class.alias("super")
            val partTimeSuper = PartTimeEmployee::class.alias("partSuper")
            if (fetch) {
                fetch(project, supervisor, on(Project::supervisor))
            }
            treat(ColumnSpec<PartTimeEmployee>(project, Project::supervisor.name), supervisor, partTimeSuper)
            where(
                and(
                    col(project, Project::id).`in`(sub),
                    col(partTimeSuper, PartTimeEmployee::weeklySalary).equal(900.toBigDecimal()),
                )
            )
        }

        // then
        assertThat(projects).containsExactlyInAnyOrder(*persistProjects.asSequence()
            .filter {
                it.employees.any { e -> e is FullTimeEmployee && e.annualSalary > 100000.toBigDecimal() }
                    || it.employees.any { e -> e is PartTimeEmployee && e.weeklySalary > 1000.toBigDecimal() }
            }
            .filter {
                it.supervisor is PartTimeEmployee && (it.supervisor as PartTimeEmployee).weeklySalary == 900.toBigDecimal()
            }
            .toList().toTypedArray())
    }

    private fun createProjects(): List<Project> {
        val e1 = fullTimeEmployee {
            name = "Sara"
            annualSalary = 120000.toBigDecimal()
        }
        val e2 = partTimeEmployee {
            name = "Jon"
            weeklySalary = 900.toBigDecimal()
        }
        val e3 = contractEmployee {
            name = "Tom"
            hourlyRate = 60.toBigDecimal()
        }
        val supervisor1 = partTimeEmployee {
            name = "Tina"
            weeklySalary = 1300.toBigDecimal()
        }

        val e4 = fullTimeEmployee {
            name = "Mike"
            annualSalary = 80000.toBigDecimal()
        }
        val e5 = partTimeEmployee {
            name = "Jackie"
            weeklySalary = 1200.toBigDecimal()
        }
        val e6 = contractEmployee {
            name = "Aly"
            hourlyRate = 90.toBigDecimal()
        }
        val supervisor2 = partTimeEmployee {
            name = "Trish"
            weeklySalary = 900.toBigDecimal()
        }

        return listOf(project {
            name = "Trade UI"
            supervisor = supervisor1
            employees = listOf(e1, e2, e3)
        }, project {
            name = "Broker UI"
            supervisor = supervisor2
            employees = listOf(e4, e5, e6)
        })
    }
}
