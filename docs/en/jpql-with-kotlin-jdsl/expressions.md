# Expressions

Kotlin JDSL has `Expression` interface to represent an expression in JPQL.

## Alias

Calling `as()` on `Expression` allows you to alias `Expression`.
You can create a reference of `Expression` using expression().
The reference is identified by an alias and references `Expression` with the same alias.
This allows you to alias `Expression` or reference the aliased `Expression` in other clauses.

```kotlin
val bookPrice = expression(BigDecimal::class, "price")

select(
    path(Book::price)(BookPrice::value).`as`(bookPrice)
).from(
    entity(Book::class)
).where(
    bookPrice.eq(BigDecimal.valueOf(100))
)

// OR

select(
    path(Book::price)(BookPrice::value).`as`(expression("price"))
).from(
    entity(Book::class)
).where(
    expression(BigDecimal::class, "price").eq(BigDecimal.valueOf(100))
)
```

### Type Cast

In some cases, you may want to change the type of `Expression` you want to use.
For this, Kotlin JDSL provides unsafe type casting through `as()`.

{% hint style="info" %}
This is a shortened form of `as Expression<T>`, so it may not work as expected.
{% endhint %}

```kotlin
avg(path(FullTimeEmployee::annualSalary)(EmployeeSalary::value)).`as`(BigDecimal::class)
```

## Arithmetic operations

To build arithmetic operations, you can use following functions:

* \+ (plus)
* \- (minus)
* \* (times)
* / (div)

```kotlin
path(Book::price).plus(path(Book::salePrice))
plus(path(Book::price), path(Book::salePrice))

path(Book::price).minus(path(Book::salePrice))
minus(path(Book::price), path(Book::salePrice))

path(Book::price).times(path(Book::salePrice))
times(path(Book::price), path(Book::salePrice))

path(Book::price).div(path(Book::salePrice))
div(path(Book::price), path(Book::salePrice))
```

### Parentheses

Calling arithmetic operators using a normal function instead of an extension function allows you to order the operators using parentheses.
In an extended function, Kotlin JDSL cannot add parentheses because the order is ambiguous.

```kotlin
// normal function: (Book.price - Book.salePrice) * (100)
times(
    path(Book::price).minus(path(Book::salePrice)),
    BigDecimal.valueOf(100),
)

// extension function: Book.price - Book.salePrice * 100
path(Book::price).minus(path(Book::salePrice)).times(BigDecimal.valueOf(100))
```

## Values

To build value, you can use `value()`.
All values built by `value()` are interpreted as query parameters.
These query parameters cannot be overridden.

{% hint style="info" %}
If KClass is passed to `value()`, it is considered [Entity](entities.md).
{% endhint %}

```kotlin
// SELECT Book.isbn FROM Book as Book WHERE Book.price = ?
select(
    path(Book::isbn)
).from(
    entity(Book::class)
).where(
    path(Book::price).eq(value(BigDecimal.valueOf(100)))
)
```

### Params

Calling `param()` instead of `value()` allows you to build a query parameter that can be overridden.

```kotlin
val context = JpqlRenderContext()

val query = jpql {
    select(
        path(Book::isbn)
    ).from(
        entity(Book::class)
    ).where(
        path(Book::price).eq(param("price"))
    )
}

val queryParams = mapOf(
    "price" to BigDecimal.valueOf(100)
)

entityManager.createQuery(query, queryParams, context)
```

### Literals

Calling `xxxLiteral()` instead of `value()` allows you to build a literal in query.

{% hint style="info" %}
When printing a string literal, if the string includes '(single quote), the '(single quote) is changed to ''(two single quotes).
For example: 'literal''s'.
{% endhint %}

| Type    | Function       | Rendered                     |
|---------|----------------|------------------------------|
| Int     | intLiteral     | {value}                      |
| Long    | longLiteral    | {value}L                     |
| Float   | floatLiteral   | {value}F                     |
| Double  | doubleLiteral  | {value}                      |
| Boolean | booleanLiteral | TRUE or FALSE                |
| Char    | charLiteral    | '{value}'                    |
| String  | stringLiteral  | '{value}'                    |
| Enum    | enumLiteral    | {qualified name}.{enum name} |

## Aggregation functions

To build aggregation functions operations, you can use following functions:

* COUNT (count)
* MIN (min)
* MAX (max)
* AVG (avg)
* SUM (sum)

```kotlin
count(path(Book::price))
countDistinct(path(Book::price))

max(path(Book::price))
maxDistinct(path(Book::price))

min(path(Book::price))
minDistinct(path(Book::price))

sum(path(Book::price))
sumDistinct(path(Book::price))
```

### Sum

Depending on the type of the parameter, `sum()` returns a different type.

| Type       | Return Type |
|------------|-------------|
| Int        | Long        |
| Long       | Long        |
| Float      | Double      |
| Double     | Double      |
| BigInteger | BigInteger  |
| BigDecimal | BigDecimal  |

## Functions

Kotlin JDSL provides functions to support built-in functions in JPA.

### String functions

| Function  | DSL function |
|-----------|--------------|
| CONCAT    | support      |
| SUBSTRING | support      |
| TRIM      | support      |
| LOWER     | support      |
| UPPER     | support      |
| LENGTH    | support      |
| LOCATE    | support      |

### Arithmetic functions

| Function | DSL function |
|----------|--------------|
| ABS      | not yet      |
| CEILING  | not yet      |
| EXP      | not yet      |
| FLOOR    | not yet      |
| LN       | not yet      |
| MOD      | not yet      |
| POWER    | not yet      |
| ROUND    | not yet      |
| SIGN     | not yet      |
| SQRT     | not yet      |
| SIZE     | not yet      |
| INDEX    | not yet      |

### Datetime functions

| Function           | DSL function |
|--------------------|--------------|
| CURRENT\_DATE      | not yet      |
| CURRENT\_TIME      | not yet      |
| CURRENT\_TIMESTAMP | not yet      |
| LOCAL DATE         | not yet      |
| LOCAL TIME         | not yet      |
| LOCAL DATETIME     | not yet      |
| EXTRACT            | not yet      |

### Database function

Calling `function()` allows you to build predefined database functions and user-defined database functions.

```kotlin
function(String::class, "myFunction", path(Book::isbn))
```

## Cases

To build a case, you can use `caseWhen()` and `caseValue()`.

```kotlin
caseWhen(path(Book::price).lt(BigDecimal.valueOf(100))).then("0")
    .`when`(path(Book::price).lt(BigDecimal.valueOf(200))).then("100")
    .`when`(path(Book::price).lt(BigDecimal.valueOf(300))).then("200")
    .`else`("300")

caseValue(path(Book::price))
    .`when`(BigDecimal.valueOf("100")).then("10")
    .`when`(BigDecimal.valueOf("200")).then("20")
    .`when`(BigDecimal.valueOf("300")).then("30")
    .`else`(path(Book::price))
```

### Coalesce

To build coalesce, you can use `coalesce()`.
`coalesce()` returns the first non-null value in the parameters, or null if there are no non-null values in the parameters.

```kotlin
coalesce(path(Employee::nickname), path(Employee::name))
```

### NullIf

To build nullIf, you can use `nullIf()`. `nullIf()` returns null if value1 = value2 is true, otherwise returns value1.

```kotlin
nullIf(path(Book::price), BigDecimal.ZERO)
```

## New

To build DTO projection, you can use `new()`.

```kotlin
data class Row(
    val departmentId: Long,
    val count: Long,
)

new(
    Row::class
    path(EmployeeDepartment::departmentId),
    count(Employee::employeeId),
)
```

## Type

To build type operator, you can use `type()`.

```kotlin
select(
    path(Employee::id)
).from(
    entity(Employee::class)
).where(
    type(entity(Employee::class)).eq(FullTimeEmployee::class)
)
```

## Custom expression

Calling `customExpression()` allows you to build a custom expression.

```kotlin
customExpression(String::class, "CAST({0} AS VARCHAR)", path(Book::price))
```

If you use a lot of `customExpression()`, you can create [your own DSL](custom-dsl.md).
