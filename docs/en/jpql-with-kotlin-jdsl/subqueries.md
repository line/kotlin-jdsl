# Subqueries

A subquery is the [Select statement](statements.md#select-statement) within another statement.

## Derived entity

The Select statement can be treated as the Entity using the `asEntity` extension function. And you can pass it to the From clause to reference it in other clauses of the Select statement.

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

The Select statement can be treated as the Expression using the `asSubquery` extension function. And you can pass it to the [the Functions](functions.md) or [the Predicates](predicates.md) to add condition in the Where clause. You can also pass it to the Select clause, depending on your JPA implementation.

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
