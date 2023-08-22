# Subqueries

A subquery is a [select statement](statements.md#select-statement) within another statement.

## Derived entity

A select statement can be used as [entity](entities.md) using asEntity extension function. So you can pass it to a [from clause](statements.md#from-clause) to reference it in other clauses of a select statement.

```kotlin
data class DerivedEntity(
    val employeeId: Long,
    val count: Long,
)

val query = jpql {
    val subquery = select<DerivedEntity>(
        path(Employee::employeeId).`as`(expression("employeeId")),
        count(Employee::employeeId).`as`(expression("count")),
    ).from(
        entity(Employee::class),
        join(Employee::departments),
    ).groupBy(
        path(Employee::employeeId),
    ).having(
        count(Employee::employeeId).greaterThan(1L),
    )

    select(
        count(DerivedEntity::employeeId),
    ).from(
        subquery.asEntity(),
    )
}
```

## Subquery

A [select statement](statements.md#select-statement) can be used as [expression](expressions.md) using asSubquery extension function. So you can pass it to [expression](expressions.md) or [predicate](predicates.md). You can also pass it to a [select clause](statements.md#select-clause), depending on your JPA implementation.

```kotlin
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

    deleteFrom(
        Employee::class,
    ).where(
        path(Employee::employeeId).`in`(employeeIds),
    )
}
```
