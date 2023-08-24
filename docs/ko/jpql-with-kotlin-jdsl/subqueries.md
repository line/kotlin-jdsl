# Subqueries

[select statement](statements.md#select-statement)에 `asEntity()`와 `asSubquery()`를 호출하는 것으로 subquery를 만들 수 있습니다.

## Derived entity

`asEntity()`를 통해 select statement는 [Entity](entities.md)로 사용될 수 있습니다.

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

`asSubquery()`를 통해 select statement는 [Expression](expressions.md)로 사용될 수 있습니다.

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
