# Predicates

Kotlin JDSL has the `Predicate` interface to represent a conditional expression in JPQL.

## Logical operators

Use the following functions to build logical operations:

* AND (and)
* OR (or)
* NOT (not)

{% hint style="info" %}
If all `Predicate` passed to `and()` and `or()` is null or empty, `and()` will be interpreted as `1 = 1` and `or()` will be interpreted as `0 = 1`.
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

Call logical operators using a normal function instead of an extension function to add parentheses for the order of operations.
As for extension functions, Kotlin JDSL cannot add parentheses because the order is ambiguous.

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

Use the following functions to build comparison operators:

* = (equal or eq)
* \<> (notEqual or ne)
* \> (greaterThan or gt)
* \>= (greaterThanOrEqualTo or ge)
* \< (lessThan or lt)
* \<= (lessThanOrEqualTo or le)

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

Append `all` and `any` at the end of the function name to use All and Any operators on subqueries.

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

Use `isNull()` and `isNotNull()` to build null comparison operations.

```kotlin
path(Employee::nickname).isNull()

path(Employee::nickname).isNotNull()
```

## Like

Use `like()` and `notLike()` to build like comparison operations.

```kotlin
path(Employee::nickname).like("E%")
path(Employee::nickname).like("E_", escape = '_')

path(Employee::nickname).notLike("E%")
path(Employee::nickname).notLike("E_", escape = '_')
```

## Between

Use `between()` and `notBetween()` to build between comparison operations.

```kotlin
path(Employee::price).between(BigDecimal.valueOf(100), BigDecimal.valueOf(200))

path(Employee::price).notBetween(BigDecimal.valueOf(100), BigDecimal.valueOf(200))
```

## In

Use `in()` and `notIn()` to build in comparison operations.

```kotlin
path(Employee::price).`in`(BigDecimal.valueOf(100), BigDecimal.valueOf(200))

path(Employee::price).notIn(BigDecimal.valueOf(100), BigDecimal.valueOf(200))
```

## Exists

Use `exists()` and `notExists()` to build exists operations.

```kotlin
exists(subquery)

notExists(subquery)
```

## Empty

Use `isEmpty()` and `isNotEmpty()` to build empty operations.

```kotlin
path(Employee::departments).isEmpty()

path(Employee::departments).isNotEmpty()
```

## Database function

Call `function()` with `KClass<Boolean>` to create predefined database functions and user-defined database functions.

```kotlin
function(Boolean::class, "myFunction", path(Book::isbn))
```

{% hint style="info" %}
You may need to register information about the function you want to use with the JPA Provider.
For example, if you are using Hibernate, you need to register a `FunctionContributor`.
{% endhint %}

## Custom predicate

Call `customPredicate()` to build a custom predicate.

```kotlin
customPredicate("{0} MEMBER OF {1}", value(author), path(Book::authors))
```

If you frequently use `customPredicate()`, you can create [your own DSL](custom-dsl.md).
