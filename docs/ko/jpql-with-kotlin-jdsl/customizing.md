# Customizing

## DSL

The `Jpql` class has all the DSL functions that Kotlin JDSL provides by default. You can inherit it and create your own functions. When you create your own functions, you can provide your own QueryModel that implements the Expression or Predicate. And you can register [the Serializer](customizing.md#serializer) with Kotlin JDSL so that Kotlin JDSL can serialize your QueryModel.

{% hint style="info" %}
You must to implement `JpqlDsl.Constructor` as a companion object so that the jpql function can recognize the DSL class and create an object of it.
{% endhint %}

```kotlin
class MyJpql : Jpql() {
    companion object Constructor : JpqlDsl.Constructor<MyJpql> {
        override fun newInstance(): MyJpql = MyJpql()
    }
    
    fun myFunction(value: String): Expression<String> {
        return function(String::class, "myFunction", listOf(value(value)))
    }

    fun Expressionable<String>.regexLike(value: String): Predicate {
        return MyRegexLike(this.toExpression(), value)
    }
}

val query = jpql(MyJpql) {
    select(
        entity(Book::class)
    ).from(
        entity(Book::class)
    ).where(
        myFunction("test").regexLike(".*")
    )
}
```

## Serializer

You can implement the `JpqlSerializer` and add it to the `JpqlRenderContext` to serialize the QueryModel you provide to the Kotlin JDSL in [the DSL section](customizing.md#dsl).&#x20;

The `JpqlSerializer` provides the `JpqlWriter` and the `RenderContext` for you to implement serialization. From the `RenderContext`, you get the `JpqlRenderSerializer`, which can serialize other QueryParts in your QueryModel, and the `JpqlRenderStatement` and `JpqlRenderClause`, which let you know which statement and clause are currently being rendered. And you can write the query as a String through the write function of the `JpqlWriter`.

```kotlin
class MyRegexLikeSerializer : JpqlSerializer<MyRegexLike> {
    override fun handledType(): KClass<MyRegexLike> {
        return MyRegexLike::class
    }

    override fun serialize(part: MyRegexLike, writer: JpqlWriter, context: RenderContext) {
        val delegate = context.getValue(JpqlRenderSerializer)

//        val statement = context.getValue(JpqlRenderStatement)
//        val clause = context.getValue(JpqlRenderClause)
//
//        statement.isSelect()
//        clause.isWhere()

        writer.write("REGEXP_LIKE")
        writer.write("(")
        delegate.serialize(part.expr, writer, context)
        writer.write(", ")
        delegate.serialize(part.pattern, writer, context)
        writer.write(")")
    }
}
```

The JpqlRenderContext provides a registerModule function that allows you to register the JpqlSerializer you implemented. You can add your `JpqlSerializer` to the `JpqlRenderContext` by implementing the `JpqlRenderModule`. And when you execute the `JpqlQuery`, you can serialize it with your `JpqlSerializer` by passing it as a parameter to the `createQuery` function.

```kotlin
val myModule = object : JpqlRenderModule {
    override fun setupModule(context: JpqlRenderModule.SetupContext) {
        context.addSerializer(MyRegexLikeSerializer())
    }
}

val myContext = JpqlRenderContext().registerModule(myModule)

val jpqQuery = entityManager.createQuery(query, myContext)
```
