# Predicates

Predicate represents a conditional expression in JPQL.

## Logical operators

Logical operations can be represented by and, or, and not functions.

* AND (and)
* OR (or)
* NOT (not)

{% hint style="info" %}
The AND and OR operators print 1 = 1 in the case of AND and 0 = 1 in the case of OR if there is no expression to enclose in AND and OR, so be careful when creating dynamic queries.
{% endhint %}

```kotlin
path(Employee::name).eq("Employee01").and(path(Employee::nickname).eq("E01"))
and(path(Employee::name).eq("Employee01"), path(Employee::nickname).eq("E01"))

path(Employee::name).eq("Employee01").or(path(Employee::nickname).eq("E01"))
or(path(Employee::name).eq("Employee01"), path(Employee::nickname).eq("E01"))

not(path(Employee::name).eq("Employee01"))
```

### Parenthesis

When using logical operators, if you want to order the operators using parentheses, call the logical operators using a normal function instead of an extension function. With extension functions, Kotlin JDSL can't identify the order in which you want to call the operators. However, with normal functions, Kotlin JDSL can identify it from the parameters.

```kotlin
// (Employee.name = 'Employee01' AND Employee.nickname = 'E01') or (Employee.name = 'Employee02' AND Employee.nickname = 'E02')
or(
    path(Employee::name).eq("Employee01").and(path(Employee::nickname).eq("E01")),
    path(Employee::name).eq("Employee02").and(path(Employee::nickname).eq("E02")),
)

// Employee.name = 'Employee01' AND Employee.nickname = 'E01' or Employee.name = 'Employee02' AND Employee.nickname = 'E02'
path(Employee::name).eq("Employee01").and(path(Employee::nickname).eq("E01")).or(path(Employee::name).eq("Employee02").and(path(Employee::nickname).eq("E02")))
```

## Comparison operators

Comparison operations can be represented by several functions.

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

By appending all and any to the end of the function name, it can also represent an All or Any conditional expression.

<pre class="language-kotlin"><code class="lang-kotlin"><strong>val query = jpql {
</strong>    val annualSalariesInDepartment3 = select(
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
        path(FullTimeEmployee::annualSalary)(EmployeeSalary::value).gtAll(annualSalariesInDepartment3),
    )
}
</code></pre>

## Null

Null comparison operators can be represented by isNull or isNotNull functions.

```kotlin
path(Employee::nickname).isNull()

path(Employee::nickname).isNotNull()
```

## Like

Like comparison operators can be represented by like or notLike functions.

```kotlin
path(Employee::nickname).like("E%")
path(Employee::nickname).like("E_", escape = '_')

path(Employee::nickname).isNotNull("E%")
path(Employee::nickname).isNotNull("E_", escape = '_')
```

## Between

Between comparison operators can be represented by between or notBetween functions.

```kotlin
path(Employee::price).between(BigDecimal.valueOf(100), BigDecimal.valueOf(200))

path(Employee::price).notBetween(BigDecimal.valueOf(100), BigDecimal.valueOf(200))
```

## In

In comparison operators can be represented by in or notIn functions.

```kotlin
path(Employee::price).`in`(BigDecimal.valueOf(100), BigDecimal.valueOf(200))

path(Employee::price).notIn(BigDecimal.valueOf(100), BigDecimal.valueOf(200))
```

## Exists

Exists that is true only if the result of the subquery consists of one or more values and that is false otherwise, can be represented by exists or notExists functions.

```kotlin
exists(subquery)

notExists(subquery)
```

## Empty

Empty that tests whether or not the collection designated by the collection-valued path expression is empty, can be represented by isEmpty or isNotEmpty functions.

```kotlin
path(Employee::departments).isEmpty()

path(Employee::departments).isNotEmpty()
```
