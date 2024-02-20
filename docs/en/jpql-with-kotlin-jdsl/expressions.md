# Expressions

Kotlin JDSL has the `Expression` interface to represent an expression in JPQL.

## Alias

Call `as()` in `Expression` to alias `Expression`.
You can create a reference of `Expression` using `expression()`.
References are identified by an alias, and you can reference `Expression` with the same alias.
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

### Type cast

In some cases, you may want to change the type of `Expression`.
To this end, Kotlin JDSL provides unsafe type casting through `as()`.

{% hint style="info" %}
This is a short form of `as Expression<T>`, so it may not work as expected.
{% endhint %}

```kotlin
avg(path(FullTimeEmployee::annualSalary)(EmployeeSalary::value)).`as`(BigDecimal::class)
```

## Arithmetic operations

Use the following functions to build arithmetic operations:

* \+ (plus)
* \- (minus)
* \* (times)
* / (div)
* % (mod)

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

Call arithmetic operators using a normal function instead of an extension function to add parentheses for the order of operations.
As for extension functions, Kotlin JDSL cannot add parentheses because the order is ambiguous.

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

Use `value()` to build a value.
All values built by `value()` are interpreted as query parameters.
These query parameters cannot be overridden.

{% hint style="info" %}
If KClass is passed to `value()`, it is recognized as [Entity](entities.md).
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

Use `param()` instead of `value()` to build query parameters.
Parameters created with `param()` can be overridden.

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

Call `xxxLiteral()` instead of `value()` to build a literal in queries.

{% hint style="info" %}
When printing a string literal with single quotation marks, the single quotation mark (') is replaced with the two single quotation marks ('').
It will look like 'literal''s'.
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

Use the following functions to build aggregate functions:

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

`sum()` returns a different type, depending on the type of the parameter.

| Type       | Return Type |
|------------|-------------|
| Int        | Long        |
| Long       | Long        |
| Float      | Double      |
| Double     | Double      |
| BigInteger | BigInteger  |
| BigDecimal | BigDecimal  |

## Functions

Kotlin JDSL provides a series of functions to support built-in functions in JPA.

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

Call `function()` to create predefined database functions and user-defined database functions.

```kotlin
function(String::class, "myFunction", path(Book::isbn))
```

{% hint style="info" %}
You may need to register information about the function you want to use with the JPA Provider.
For example, if you are using Hibernate, you need to register a `FunctionContributor`.
{% endhint %}

## Cases

Use `caseWhen()` and `caseValue()` to build cases.

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

Use `coalesce()` to build coalesce.

```kotlin
coalesce(path(Employee::nickname), path(Employee::name))
```

### NullIf

Use `nullIf()` to build nullIf.

```kotlin
nullIf(path(Book::price), BigDecimal.ZERO)
```

## New

Use `new()` to build DTO projections.

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

Use `type()` to build type operators.

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

Call `customExpression()` to build a custom expression.

```kotlin
customExpression(String::class, "CAST({0} AS VARCHAR)", path(Book::price))
```

If you frequently use `customExpression()`, you can create [your own DSL](custom-dsl.md).
