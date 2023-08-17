# Expressions

## New

An expression that provides a DTO projection

```kotlin
```

## Type

Expressions that return the type of an Entity for polymorphic queries

## Arithmetic operations

Expressions to represent number-type arithmetic operations

## Aggregation functions

## Cases

### Coalesce

Expression that returns the first non-null value in the expressions, or null if there are no non-null value in expressions.

### NullIf

Expression that returns null if left = right is true, otherwise returns left. This is the same as `CASE WHEN left = right THEN NULL ELSE left END`.

## Function

## Custom expression

cast
