# Predicates

Kotlin JDSL은 JPQL의 conditional expression을 표현하기 위해 `Predicate` 인터페이스를 가지고 있습니다.

## Logical operators

논리 연산을 만들기 위해, 다음 함수들을 사용할 수 있습니다.

* AND (and)
* OR (or)
* NOT (not)

{% hint style="info" %}
만약 `and()` 와 `or()`로 넘어온 `Predicate`가 모두 null 이거나 비어 있으면, `and()`의 경우에는 `1 = 1`로 `or()`의 경우에는 `0 = 1`로 해석됩니다.
그렇기 떄문에 다이나믹 쿼리를 만들 때 조심해야 합니다.
{% endhint %}

```kotlin
path(Employee::name).eq("Employee01").and(path(Employee::nickname).eq("E01"))
and(path(Employee::name).eq("Employee01"), path(Employee::nickname).eq("E01"))

path(Employee::name).eq("Employee01").or(path(Employee::nickname).eq("E01"))
or(path(Employee::name).eq("Employee01"), path(Employee::nickname).eq("E01"))

not(path(Employee::name).eq("Employee01"))
```

### Parentheses

확장 함수가 아닌 일반 함수를 호출하는 것으로 논리 연산자에 연산 순서를 위한 소괄호를 추가할 수 있습니다.
확장 함수의 경우 연산 순서가 모호해서 소괄호를 추가할 수 없습니다.

```kotlin
// 일반 함수: (Employee.name = 'Employee01' AND Employee.nickname = 'E01') or (Employee.name = 'Employee02' AND Employee.nickname = 'E02')
or(
    path(Employee::name).eq("Employee01").and(path(Employee::nickname).eq("E01")),
    path(Employee::name).eq("Employee02").and(path(Employee::nickname).eq("E02")),
)

// 확장 함수: Employee.name = 'Employee01' AND Employee.nickname = 'E01' or Employee.name = 'Employee02' AND Employee.nickname = 'E02'
path(Employee::name).eq("Employee01").and(path(Employee::nickname).eq("E01")).or(path(Employee::name).eq("Employee02").and(path(Employee::nickname).eq("E02")))
```

## Comparison operators

비교 연산을 만들기 위해, 다음 함수들을 사용할 수 있습니다.

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

함수 이름 마지막에 `all`과 `any`를 붙이는 것으로 subquery에 대한 All과 Any 연산을 할 수 있습니다.

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

null 비교 연산을 만들기 위해, `isNull()`과 `isNotNull()`을 사용할 수 있습니다.

```kotlin
path(Employee::nickname).isNull()

path(Employee::nickname).isNotNull()
```

## Like

like 비교 연산을 만들기 위해, `like()`와 `notLike()`를 사용할 수 있습니다.

```kotlin
path(Employee::nickname).like("E%")
path(Employee::nickname).like("E_", escape = '_')

path(Employee::nickname).notLike("E%")
path(Employee::nickname).notLike("E_", escape = '_')
```

## Between

between 비교 연산을 만들기 위해, `between()`과 `notBetween()`을 사용할 수 있습니다.

```kotlin
path(Employee::price).between(BigDecimal.valueOf(100), BigDecimal.valueOf(200))

path(Employee::price).notBetween(BigDecimal.valueOf(100), BigDecimal.valueOf(200))
```

## In

in 비교 연산을 만들기 위해, `in()`과 `notIn()`을 사용할 수 있습니다.

```kotlin
path(Employee::price).`in`(BigDecimal.valueOf(100), BigDecimal.valueOf(200))

path(Employee::price).notIn(BigDecimal.valueOf(100), BigDecimal.valueOf(200))
```

## Exists

exists 연산을 만들기 위해, `exists()`와 `notExists()`을 사용할 수 있습니다.

```kotlin
exists(subquery)

notExists(subquery)
```

## Empty

empty 연산을 만들기 위해, `isEmpty()`와 `isNotEmpty()`을 사용할 수 있습니다.

```kotlin
path(Employee::departments).isEmpty()

path(Employee::departments).isNotEmpty()
```
