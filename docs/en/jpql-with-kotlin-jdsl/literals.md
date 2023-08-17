# Values

Kotlin JDSL creates a query by replacing all the values entered through the DSL function with the named parameter.

## Params

You can use the param DSL function to split the query declaration and the param generation. By naming a param to the param DSL function and passing the value corresponding to that param's name to the Map when executed, Kotlin JDSL will match the query param.

## Literals

Instead of printing a value as a query parameter, you can use Literal to print a value directly into the query.

|        |   |   |
| ------ | - | - |
| Long   |   |   |
| String |   |   |
| Enum   |   |   |
