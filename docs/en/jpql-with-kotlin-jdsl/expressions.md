# Expressions

Expression represents an expression in JPQL.

## Alias

By using as function, you can use the aliased expression in other clauses of the [select statement](statements.md#select-statement). As function takes an expression object to refer to the source of as function. The expression for reference can be created using expression function.

```kotlin
val bookPrice = expression(BigDecimal::class, "price")
// or val bookPrice = expression<BigDecimal>("price")

select(
    path(BookPrice::class).`as`(bookPrice)
).from(
    entity(Book::class)
).where(
    bookPrice.eq(BigDecimal.valueOf(100))
)
```

You don't have to use an object with the same identity to reference an aliased expression. Because an expression is referenced by an alias, even if you create multiple expression objects, if they have the same alias, they will refer to the same aliased expression.

```kotlin
select(
    path(BookPrice::class).`as`(expression("price"))
).from(
    entity(Book::class)
).where(
    expression("price").eq(BigDecimal.valueOf(100))
)
```

### Type Cast

In some cases, you may want to specify the return type of the expression you want to use, rather than the type inferred by Kotlin JDSL. For example, AVG returns Double, but in JPQL Double can be converted to BigDecimal, so you want AVG to return BigDecimal. For this Kotlin JDSL provides unsafe type casting by specifying the type you want with KClass in as function.

{% hint style="info" %}
This is just a shorthand for `as Expression<T>`, so it may not work for all JPA implementations.
{% endhint %}

```kotlin
avg(path(FullTimeEmployee::annualSalary)(EmployeeSalary::value)).`as`(BigDecimal::class)
```

## Arithmetic operations

Arithmetic operations can be represented by plus, minus, times and div functions.

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

### Parenthesis

When using arithmetic operators, if you want to order the operators using parentheses, call the arithmetic operators using a normal function instead of an extension function. With extension functions, Kotlin JDSL can't identify the order in which you want to call the operators. However, with normal functions, Kotlin JDSL can identify it from the parameters.

```kotlin
// (Book.price - Book.salePrice) * (100)
times(
    path(Book::price).minus(path(Book::salePrice)),
    BigDecimal.valueOf(100),
)

// Book.price - Book.salePrice * 100
path(Book::price).minus(path(Book::salePrice)).times(BigDecimal.valueOf(100))
```

## Values

The value used in a query can be represented by value function. All values created by the value function are printed in the query as query parameters. These query parameters cannot be overridden.

{% hint style="info" %}
If KClass or Class object is passed in the value function, [entity](entities.md) is printed.
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

You may want to create a query in advance without values, and then execute it later with calculated values. In this
case, you can use param function, which represents a query parameter. Param function represents the query parameter the
same as the value function, but unlike the value function, it can override the query parameter.

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

Instead of printing a value as a query parameter, you can use literal to print a value directly into the query. A select clause can be represented by select function.

{% hint style="info" %}
When printing a string literal, if the string includes '(single quote), the '(single quote) is changed to ''(two single quotes). For example: 'literal''s'.
{% endhint %}

<table><thead><tr><th width="155.33333333333331">Type</th><th>Function</th><th>Rendered</th></tr></thead><tbody><tr><td>Int</td><td>intLiteral</td><td>{value}</td></tr><tr><td>Long</td><td>longLiteral</td><td>{value}L</td></tr><tr><td>Float</td><td>floatLiteral</td><td>{value}F</td></tr><tr><td>Double</td><td>doubleLiteral</td><td>{value}</td></tr><tr><td>Boolean</td><td>booleanLiteral</td><td>TRUE or FALSE</td></tr><tr><td>Char</td><td>charLiteral</td><td>'{value}'</td></tr><tr><td>String</td><td>stringLiteral</td><td>'{value}'</td></tr><tr><td>Enum</td><td>enumLiteral</td><td>{qualified name}.{enum name}</td></tr></tbody></table>

## Aggregation functions

Aggregation functions can be represented by a function corresponding to its name.

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

Sum function takes a different return type depending on the type of a parameter.

| Type       | Return Type |
| ---------- | ----------- |
| Int        | Long        |
| Long       | Long        |
| Float      | Double      |
| Double     | Double      |
| BigInteger | BigInteger  |
| BigDecimal | BigDecimal  |

## Functions

Kotlin JDSL provides several functions to support built-in functions in JPA.

### String functions

| Function  | DSL function |
| --------- | ------------ |
| CONCAT    | not yet      |
| SUBSTRING | not yet      |
| TRIM      | not yet      |
| LOWER     | not yet      |
| UPPER     | not yet      |
| LENGTH    | not yet      |
| LOCATE    | not yet      |

### Arithmetic functions

| Function | DSL function |
| -------- | ------------ |
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
| ------------------ | ------------ |
| CURRENT\_DATE      | not yet      |
| CURRENT\_TIME      | not yet      |
| CURRENT\_TIMESTAMP | not yet      |
| LOCAL DATE         | not yet      |
| LOCAL TIME         | not yet      |
| LOCAL DATETIME     | not yet      |
| EXTRACT            | not yet      |

### Database function

The invocation of functions that can represent predefined database functions or user-defined database functions, can be represented by function function. By specifying the return type of the function and passing the parameters of the function as parameters, you can represent the predefined database functions or user-defined database functions.

```kotlin
function(String::class, "myFunction", path(Book::isbn))
```

## Cases

A case when clause can be represented by caseWhen or caseValue function.

CaseWhen function allows you to represent a case when clause based on [predicates](predicates.md).

```kotlin
caseWhen(path(Book::price).lt(BigDecimal.valueOf(100))).then("0")
    .`when`(path(Book::price).lt(BigDecimal.valueOf(200))).then("100")
    .`when`(path(Book::price).lt(BigDecimal.valueOf(300))).then("200")
    .`else`("300")
```

CaseWhen function allows you to represent a case when clause based on [expressions](expressions.md).

```kotlin
caseValue(path(Book::price))
    .`when`(BigDecimal.valueOf("100")).then("10")
    .`when`(BigDecimal.valueOf("200")).then("20")
    .`when`(BigDecimal.valueOf("300")).then("30")
    .`else`(path(Book::price))
```

### Coalesce

Coalesce that returns the first non-null value in the parameters, or null if there are no non-null values in the parameters, can be represented by coalesce function.

```kotlin
coalesce(path(Employee::nickname), path(Employee::name))
```

### NullIf

NullIf that returns null if value1 = value2 is true, otherwise returns value1, can be represented by nullIf function.

```kotlin
nullIf(path(Book::price), BigDecimal.ZERO)
```

## New

DTO projection can be represented by new function. By specifying the DTO class you want to project in new function and passing the parameters of the class as parameters, you can represent the DTO projection.

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

Type operator that can restrict query polymorphism, can be represented by type function.

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

Expressions that are not provided by the Kotlin JDSL, can be represented by customExpression function. By specifying the
return type of the expression and passing the parameters of the expression as parameters, you can represent the your
expression.

```kotlin
customExpression(String::class, "CAST({0} AS VARCHAR)", path(Book::price))
```

If you find that you are using the same customExpression a lot, you may want to create your own DSL, see [Customizing](customizing.md).
