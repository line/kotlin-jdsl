# Predicates

Kotlin JDSL has `Predicate` interface to represent a conditional expression in JPQL.

## Logical operators

To build logical operations, you can use following functions:

* AND (and)
* OR (or)
* NOT (not)

{% hint style="info" %}
If there is no `Predicate` in `and()` and `or()`, `and()` will be interpreted as `1 = 1` and `or()` will be interpreted as `0 = 1`.
So be careful when creating a dynamic query.
{% endhint %}

```kotlin
path(Employee::name).eq("Employee01").and(path(Employee::nickname).eq("E01"))
and(path(Employee::name).eq("Employee01"), path(Employee::nickname).eq("E01"))

path(Employee::name).eq("Employee01").or(path(Employee::nickname).eq("E01"))
or(path(Employee::name).eq("Employee01"), path(Employee::nickname).eq("E01"))

not(path(Employee::name).eq("Employee01"))
```

### Parentheses

Calling logical operators using a normal function instead of an extension function allows you to order the operators using parentheses.
In an extended function, Kotlin JDSL cannot add parentheses because the order is ambiguous.

```kotlin
// normal function: (Employee.name = 'Employee01' AND Employee.nickname = 'E01') or (Employee.name = 'Employee02' AND Employee.nickname = 'E02')
or(
    path(Employee::name).eq("Employee01").and(path(Employee::nickname).eq("E01")),
    path(Employee::name).eq("Employee02").and(path(Employee::nickname).eq("E02")),
)

// extension function: Employee.name = 'Employee01' AND Employee.nickname = 'E01' or Employee.name = 'Employee02' AND Employee.nickname = 'E02'
path(Employee::name).eq("Employee01").and(path(Employee::nickname).eq("E01")).or(path(Employee::name).eq("Employee02").and(path(Employee::nickname).eq("E02")))
```

## Comparison operators

To build comparison operations, you can use following functions:

* \= (equal or eq)
* <> (notEqual or ne)
* \> (greaterThan or gt)
* \>= (greaterThanOrEqualTo or ge)
* < (lessThan or lt)
* <= (lessThanOrEqualTo or le)

```kotlin
path(Book::price).equal(BigDecimal.valueOf(100))
path(Book::price).eq(BigDecimal.valueOf(100))

path(Book::price).notEqual(BigDecimal.valueOf(100))
path(Book::price).ne(BigDecimal.valueOf(100))

path(Book::price).greaterThan(BigDecimal.valueOf(100))
path(Book::price).gt(BigDecimal.valueOf(100))

path(Book::price).greaterThanOrEqualTo(BigDecimal.valueOf(100))
path(Book::price).ge(BigDecimal.valueOf(100))

path(Book::price).lessThan(BigDecimal.valueOf(100))
path(Book::price).lt(BigDecimal.valueOf(100))

path(Book::price).lessThanOrEqualTo(BigDecimal.valueOf(100))
path(Book::price).le(BigDecimal.valueOf(100))
```

### All or Any

Appending `all` and `any` to the end of the function name allows you to use All and Any operators on a subquery.

```kotlin
val query = jpql {
    val annualSalaries = select(
        path(FullTimeEmployee::annualSalary)(EmployeeSalary::value),
    ).from(
        entity(FullTimeEmployee::class),
        join(FullTimeEmployee::departments),
    ).where(
        path(EmployeeDepartment::departmentId).eq(3L),
    ).asSubquery()

    select(
        path(FullTimeEmployee::employeeId),
    ).from(
        entity(FullTimeEmployee::class),
    ).where(
        path(FullTimeEmployee::annualSalary)(EmployeeSalary::value).gtAll(annualSalaries),
    )
}
```

## Null

To build null comparison operations, you can use `isNull()` and `isNotNull()`.

```kotlin
path(Employee::nickname).isNull()

path(Employee::nickname).isNotNull()
```

## Like

To build like comparison operations, you can use `like()` and `notLike()`.

```kotlin
path(Employee::nickname).like("E%")
path(Employee::nickname).like("E_", escape = '_')

path(Employee::nickname).notLike("E%")
path(Employee::nickname).notLike("E_", escape = '_')
```

## Between

To build between comparison operations, you can use `between()` and `notBetween()`.

```kotlin
path(Employee::price).between(BigDecimal.valueOf(100), BigDecimal.valueOf(200))

path(Employee::price).notBetween(BigDecimal.valueOf(100), BigDecimal.valueOf(200))
```

## In

To build in comparison operations, you can use `in()` and `notIn()`.

```kotlin
path(Employee::price).`in`(BigDecimal.valueOf(100), BigDecimal.valueOf(200))

path(Employee::price).notIn(BigDecimal.valueOf(100), BigDecimal.valueOf(200))
```

## Exists

To build exists operations, you can use `exists()` and `notExists()`.

```kotlin
exists(subquery)

notExists(subquery)
```

## Empty

To build empty operations, you can use `isEmpty()` and `isNotEmpty()`.
These test if the collection is empty.

```kotlin
path(Employee::departments).isEmpty()

path(Employee::departments).isNotEmpty()
```
