# Custom DSL

## DSL

`Jpql` 클래스를 상속하고 나만의 함수를 추가하는 것으로 나만의 DSL을 만들 수 있습니다.

`Jpql`은 Kotlin JDSL이 제공하는 모든 기본 DSL 함수를 가지고 있습니다.
이를 이용해 나만의 함수를 만들 수 있습니다.
그리고 [`Expression`](expressions.md) 혹은 [`Predicate`](predicates.md)를 구현한 나만의 `Model` 클래스를 만들고, 이를 반환하는 함수를 만들 수 있습니다.
이 경우 [`JpqlSerializer`](custom-dsl.md#serializer)를 구현하여 `Model`을 String으로 랜더링하는 방법을 Kotlin JDSL에게 알려줄 수 있습니다.

{% hint style="info" %}
`jpql()`이 DSL을 인식하기 위해서 `JpqlDsl.Constructor`를 companion object로 구현해야 합니다.
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

### Serializer

나만의 `Model`을 String을 랜더링하기 위해 `JpqlSerializer`를 구현하고 이를 `RenderContext`에 추가해야 합니다.

`JpqlSerializer`는 랜더링 로직을 구현할 수 있도록 `JpqlWriter`와 `RenderContext`를 제공합니다.
`RenderContext`를 통해 `JpqlRenderSerializer`를 얻어 나의 `Model`이 가지고 있는 다른 `Model`을 랜더링할 수 있습니다.
또 `RenderContext`를 통해 `JpqlRenderStatement`와 `JpqlRenderClause`를 얻어 현재 어떤 statement와 clause 안에서 랜더링하고 있는지 알 수 있습니다.
이것들을 이용해서 나만의 `Model`을 String으로 랜더링할 수 있습니다.

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

`JpqlRenderContext`는 `JpqlSerializer` 구현체를 추가할 수 있도록 `registerModule()`를 제공합니다.

```kotlin
val myModule = object : JpqlRenderModule {
    override fun setupModule(context: JpqlRenderModule.SetupContext) {
        context.addSerializer(MyRegexLikeSerializer())
    }
}

val myContext = JpqlRenderContext().registerModule(myModule)

val jpqQuery = entityManager.createQuery(query, myContext)
```
