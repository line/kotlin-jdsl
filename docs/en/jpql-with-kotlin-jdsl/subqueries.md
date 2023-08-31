# Subqueries

Calling `asEntity()` and `asSubquery()` after a [select statement](statements.md#select-statement) allows you to build a subquery.

## Derived entity

A select statement can be used as [Entity](entities.md) using `asEntity()`.

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

A select statement can be used as [Expression](expressions.md) using `asSubquery()`

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
