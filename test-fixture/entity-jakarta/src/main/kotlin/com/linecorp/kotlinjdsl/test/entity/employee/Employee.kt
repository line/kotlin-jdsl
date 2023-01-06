package com.linecorp.kotlinjdsl.test.entity.employee

import java.math.BigDecimal
import java.util.*
import jakarta.persistence.*

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Table(name = "employee")
@DiscriminatorColumn(name = "EMP_TYPE")
class Employee(
    @Id
    @GeneratedValue
    val id: Long,
    val name: String
) {
    override fun equals(other: Any?) = Objects.equals(id, (other as? Employee)?.id)
    override fun hashCode() = Objects.hashCode(id)
}

@Entity
@Table(name = "fulltime_employee")
@DiscriminatorValue("F")
class FullTimeEmployee(
    val annualSalary: BigDecimal,
    override val id: Long,
    override val name: String
) : Employee(id, name) {
    override fun equals(other: Any?) = Objects.equals(id, (other as? FullTimeEmployee)?.id)
    override fun hashCode() = Objects.hashCode(id)
}

@Entity
@Table(name = "parttime_employee")
@DiscriminatorValue("P")
class PartTimeEmployee(
    val weeklySalary: BigDecimal,
    override val id: Long,
    override val name: String
) : Employee(id, name) {
    override fun equals(other: Any?) = Objects.equals(id, (other as? PartTimeEmployee)?.id)
    override fun hashCode() = Objects.hashCode(id)
}

@Entity
@Table(name = "contract_employee")
@DiscriminatorValue("C")
class ContractEmployee(
    val hourlyRate: BigDecimal,
    override val id: Long,
    override val name: String
) : Employee(id, name) {
    override fun equals(other: Any?) = Objects.equals(id, (other as? ContractEmployee)?.id)
    override fun hashCode() = Objects.hashCode(id)
}

@Entity
@Table(name = "project")
class Project(
    @Id
    @GeneratedValue
    val id: Long = 0,
    val name: String,

    @OneToMany(cascade = [CascadeType.ALL])
    val employees: List<Employee>,

    @OneToOne(cascade = [CascadeType.ALL], optional = false, fetch = FetchType.LAZY)
    val supervisor: Employee
) {
    override fun equals(other: Any?) = Objects.equals(id, (other as? Project)?.id)
    override fun hashCode() = Objects.hashCode(id)
}

data class EmployeeTestBuilder(
    var id: Long = 0,
    var name: String = "name"
) {
    fun build() = Employee(
        id = id,
        name = name
    )
}

data class PartTimeEmployeeTestBuilder(
    var id: Long = 0,
    var name: String = "name",
    var weeklySalary: BigDecimal = 100.toBigDecimal()
) {
    fun build() = PartTimeEmployee(
        id = id,
        name = name,
        weeklySalary = weeklySalary
    )
}

data class FullTimeEmployeeTestBuilder(
    var id: Long = 0,
    var name: String = "name",
    var annualSalary: BigDecimal = 10000.toBigDecimal()
) {
    fun build() = FullTimeEmployee(
        id = id,
        name = name,
        annualSalary = annualSalary
    )
}

data class ContractEmployeeTestBuilder(
    var id: Long = 0,
    var name: String = "name",
    var hourlyRate: BigDecimal = 50.toBigDecimal()
) {
    fun build() = ContractEmployee(
        id = id,
        name = name,
        hourlyRate = hourlyRate
    )
}

data class ProjectTestBuilder(
    var id: Long = 0,
    var name: String = "name",
    var employees: List<Employee> = listOf(
        FullTimeEmployeeTestBuilder().build(),
        PartTimeEmployeeTestBuilder().build(),
        ContractEmployeeTestBuilder().build()
    ),
    var supervisor: Employee = EmployeeTestBuilder().build()
) {
    fun build() = Project(
        id = id,
        name = name,
        employees = employees,
        supervisor = supervisor
    )
}
