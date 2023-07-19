package com.linecorp.kotlinjdsl.dsl.sql.insert.impl

import com.linecorp.kotlinjdsl.dsl.sql.insert.*
import com.linecorp.kotlinjdsl.querymodel.sql.*
import com.linecorp.kotlinjdsl.querymodel.sql.impl.DerivedTable
import com.linecorp.kotlinjdsl.querymodel.sql.impl.Param

class InsertQueryDsl<T : Any, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21, V22> private constructor(
    private val builder: InsertQueryBuilder<T>,
) : InsertQueryColumnStep<T>,
    InsertQueryValueStep1<T, V1>,
    InsertQueryValueStep2<T, V1, V2>,
    InsertQueryValueStep3<T, V1, V2, V3>,
    InsertQueryValueStep4<T, V1, V2, V3, V4>,
    InsertQueryValueStep5<T, V1, V2, V3, V4, V5>,
    InsertQueryValueStep6<T, V1, V2, V3, V4, V5, V6>,
    InsertQueryValueStep7<T, V1, V2, V3, V4, V5, V6, V7>,
    InsertQueryValueStep8<T, V1, V2, V3, V4, V5, V6, V7, V8>,
    InsertQueryValueStep9<T, V1, V2, V3, V4, V5, V6, V7, V8, V9>,
    InsertQueryValueStep10<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10>,
    InsertQueryValueStep11<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11>,
    InsertQueryValueStep12<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12>,
    InsertQueryValueStep13<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13>,
    InsertQueryValueStep14<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14>,
    InsertQueryValueStep15<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15>,
    InsertQueryValueStep16<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16>,
    InsertQueryValueStep17<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17>,
    InsertQueryValueStep18<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18>,
    InsertQueryValueStep19<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19>,
    InsertQueryValueStep20<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20>,
    InsertQueryValueStep21<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21>,
    InsertQueryValueStep22<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21, V22>,
    InsertQueryValueStepN<T>,
    InsertQueryColumnValueStep<T> {

    constructor(table: TableReference<T>) : this(InsertQueryBuilder(table))

    override fun <V1> columns(
        column1: Column<T, V1>,
    ): InsertQueryValueStep1<T, V1> {
        val columns = listOf(
            column1,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep1<T, V1>
    }

    override fun <V1, V2> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
    ): InsertQueryValueStep2<T, V1, V2> {
        val columns = listOf(
            column1,
            column2,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep2<T, V1, V2>
    }

    override fun <V1, V2, V3> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
    ): InsertQueryValueStep3<T, V1, V2, V3> {
        val columns = listOf(
            column1,
            column2,
            column3,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep3<T, V1, V2, V3>
    }

    override fun <V1, V2, V3, V4> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
        column4: Column<T, V4>,
    ): InsertQueryValueStep4<T, V1, V2, V3, V4> {
        val columns = listOf(
            column1,
            column2,
            column3,
            column4,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep4<T, V1, V2, V3, V4>
    }

    override fun <V1, V2, V3, V4, V5> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
        column4: Column<T, V4>,
        column5: Column<T, V5>,
    ): InsertQueryValueStep5<T, V1, V2, V3, V4, V5> {
        val columns = listOf(
            column1,
            column2,
            column3,
            column4,
            column5,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep5<T, V1, V2, V3, V4, V5>
    }

    override fun <V1, V2, V3, V4, V5, V6> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
        column4: Column<T, V4>,
        column5: Column<T, V5>,
        column6: Column<T, V6>,
    ): InsertQueryValueStep6<T, V1, V2, V3, V4, V5, V6> {
        val columns = listOf(
            column1,
            column2,
            column3,
            column4,
            column5,
            column6,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep6<T, V1, V2, V3, V4, V5, V6>
    }

    override fun <V1, V2, V3, V4, V5, V6, V7> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
        column4: Column<T, V4>,
        column5: Column<T, V5>,
        column6: Column<T, V6>,
        column7: Column<T, V7>,
    ): InsertQueryValueStep7<T, V1, V2, V3, V4, V5, V6, V7> {
        val columns = listOf(
            column1,
            column2,
            column3,
            column4,
            column5,
            column6,
            column7,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep7<T, V1, V2, V3, V4, V5, V6, V7>
    }

    override fun <V1, V2, V3, V4, V5, V6, V7, V8> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
        column4: Column<T, V4>,
        column5: Column<T, V5>,
        column6: Column<T, V6>,
        column7: Column<T, V7>,
        column8: Column<T, V8>,
    ): InsertQueryValueStep8<T, V1, V2, V3, V4, V5, V6, V7, V8> {
        val columns = listOf(
            column1,
            column2,
            column3,
            column4,
            column5,
            column6,
            column7,
            column8,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep8<T, V1, V2, V3, V4, V5, V6, V7, V8>
    }

    override fun <V1, V2, V3, V4, V5, V6, V7, V8, V9> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
        column4: Column<T, V4>,
        column5: Column<T, V5>,
        column6: Column<T, V6>,
        column7: Column<T, V7>,
        column8: Column<T, V8>,
        column9: Column<T, V9>,
    ): InsertQueryValueStep9<T, V1, V2, V3, V4, V5, V6, V7, V8, V9> {
        val columns = listOf(
            column1,
            column2,
            column3,
            column4,
            column5,
            column6,
            column7,
            column8,
            column9,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep9<T, V1, V2, V3, V4, V5, V6, V7, V8, V9>
    }

    override fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
        column4: Column<T, V4>,
        column5: Column<T, V5>,
        column6: Column<T, V6>,
        column7: Column<T, V7>,
        column8: Column<T, V8>,
        column9: Column<T, V9>,
        column10: Column<T, V10>,
    ): InsertQueryValueStep10<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10> {
        val columns = listOf(
            column1,
            column2,
            column3,
            column4,
            column5,
            column6,
            column7,
            column8,
            column9,
            column10,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep10<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10>
    }

    override fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
        column4: Column<T, V4>,
        column5: Column<T, V5>,
        column6: Column<T, V6>,
        column7: Column<T, V7>,
        column8: Column<T, V8>,
        column9: Column<T, V9>,
        column10: Column<T, V10>,
        column11: Column<T, V11>,
    ): InsertQueryValueStep11<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11> {
        val columns = listOf(
            column1,
            column2,
            column3,
            column4,
            column5,
            column6,
            column7,
            column8,
            column9,
            column10,
            column11,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep11<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11>
    }

    override fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
        column4: Column<T, V4>,
        column5: Column<T, V5>,
        column6: Column<T, V6>,
        column7: Column<T, V7>,
        column8: Column<T, V8>,
        column9: Column<T, V9>,
        column10: Column<T, V10>,
        column11: Column<T, V11>,
        column12: Column<T, V12>,
    ): InsertQueryValueStep12<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12> {
        val columns = listOf(
            column1,
            column2,
            column3,
            column4,
            column5,
            column6,
            column7,
            column8,
            column9,
            column10,
            column11,
            column12,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep12<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12>
    }

    override fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
        column4: Column<T, V4>,
        column5: Column<T, V5>,
        column6: Column<T, V6>,
        column7: Column<T, V7>,
        column8: Column<T, V8>,
        column9: Column<T, V9>,
        column10: Column<T, V10>,
        column11: Column<T, V11>,
        column12: Column<T, V12>,
        column13: Column<T, V13>,
    ): InsertQueryValueStep13<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13> {
        val columns = listOf(
            column1,
            column2,
            column3,
            column4,
            column5,
            column6,
            column7,
            column8,
            column9,
            column10,
            column11,
            column12,
            column13,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep13<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13>
    }

    override fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
        column4: Column<T, V4>,
        column5: Column<T, V5>,
        column6: Column<T, V6>,
        column7: Column<T, V7>,
        column8: Column<T, V8>,
        column9: Column<T, V9>,
        column10: Column<T, V10>,
        column11: Column<T, V11>,
        column12: Column<T, V12>,
        column13: Column<T, V13>,
        column14: Column<T, V14>,
    ): InsertQueryValueStep14<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14> {
        val columns = listOf(
            column1,
            column2,
            column3,
            column4,
            column5,
            column6,
            column7,
            column8,
            column9,
            column10,
            column11,
            column12,
            column13,
            column14,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep14<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14>
    }

    override fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
        column4: Column<T, V4>,
        column5: Column<T, V5>,
        column6: Column<T, V6>,
        column7: Column<T, V7>,
        column8: Column<T, V8>,
        column9: Column<T, V9>,
        column10: Column<T, V10>,
        column11: Column<T, V11>,
        column12: Column<T, V12>,
        column13: Column<T, V13>,
        column14: Column<T, V14>,
        column15: Column<T, V15>,
    ): InsertQueryValueStep15<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15> {
        val columns = listOf(
            column1,
            column2,
            column3,
            column4,
            column5,
            column6,
            column7,
            column8,
            column9,
            column10,
            column11,
            column12,
            column13,
            column14,
            column15,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep15<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15>
    }

    override fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
        column4: Column<T, V4>,
        column5: Column<T, V5>,
        column6: Column<T, V6>,
        column7: Column<T, V7>,
        column8: Column<T, V8>,
        column9: Column<T, V9>,
        column10: Column<T, V10>,
        column11: Column<T, V11>,
        column12: Column<T, V12>,
        column13: Column<T, V13>,
        column14: Column<T, V14>,
        column15: Column<T, V15>,
        column16: Column<T, V16>,
    ): InsertQueryValueStep16<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16> {
        val columns = listOf(
            column1,
            column2,
            column3,
            column4,
            column5,
            column6,
            column7,
            column8,
            column9,
            column10,
            column11,
            column12,
            column13,
            column14,
            column15,
            column16,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep16<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16>
    }

    override fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
        column4: Column<T, V4>,
        column5: Column<T, V5>,
        column6: Column<T, V6>,
        column7: Column<T, V7>,
        column8: Column<T, V8>,
        column9: Column<T, V9>,
        column10: Column<T, V10>,
        column11: Column<T, V11>,
        column12: Column<T, V12>,
        column13: Column<T, V13>,
        column14: Column<T, V14>,
        column15: Column<T, V15>,
        column16: Column<T, V16>,
        column17: Column<T, V17>,
    ): InsertQueryValueStep17<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17> {
        val columns = listOf(
            column1,
            column2,
            column3,
            column4,
            column5,
            column6,
            column7,
            column8,
            column9,
            column10,
            column11,
            column12,
            column13,
            column14,
            column15,
            column16,
            column17,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep17<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17>
    }

    override fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
        column4: Column<T, V4>,
        column5: Column<T, V5>,
        column6: Column<T, V6>,
        column7: Column<T, V7>,
        column8: Column<T, V8>,
        column9: Column<T, V9>,
        column10: Column<T, V10>,
        column11: Column<T, V11>,
        column12: Column<T, V12>,
        column13: Column<T, V13>,
        column14: Column<T, V14>,
        column15: Column<T, V15>,
        column16: Column<T, V16>,
        column17: Column<T, V17>,
        column18: Column<T, V18>,
    ): InsertQueryValueStep18<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18> {
        val columns = listOf(
            column1,
            column2,
            column3,
            column4,
            column5,
            column6,
            column7,
            column8,
            column9,
            column10,
            column11,
            column12,
            column13,
            column14,
            column15,
            column16,
            column17,
            column18,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep18<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18>
    }

    override fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
        column4: Column<T, V4>,
        column5: Column<T, V5>,
        column6: Column<T, V6>,
        column7: Column<T, V7>,
        column8: Column<T, V8>,
        column9: Column<T, V9>,
        column10: Column<T, V10>,
        column11: Column<T, V11>,
        column12: Column<T, V12>,
        column13: Column<T, V13>,
        column14: Column<T, V14>,
        column15: Column<T, V15>,
        column16: Column<T, V16>,
        column17: Column<T, V17>,
        column18: Column<T, V18>,
        column19: Column<T, V19>,
    ): InsertQueryValueStep19<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19> {
        val columns = listOf(
            column1,
            column2,
            column3,
            column4,
            column5,
            column6,
            column7,
            column8,
            column9,
            column10,
            column11,
            column12,
            column13,
            column14,
            column15,
            column16,
            column17,
            column18,
            column19,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep19<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19>
    }

    override fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
        column4: Column<T, V4>,
        column5: Column<T, V5>,
        column6: Column<T, V6>,
        column7: Column<T, V7>,
        column8: Column<T, V8>,
        column9: Column<T, V9>,
        column10: Column<T, V10>,
        column11: Column<T, V11>,
        column12: Column<T, V12>,
        column13: Column<T, V13>,
        column14: Column<T, V14>,
        column15: Column<T, V15>,
        column16: Column<T, V16>,
        column17: Column<T, V17>,
        column18: Column<T, V18>,
        column19: Column<T, V19>,
        column20: Column<T, V20>,
    ): InsertQueryValueStep20<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20> {
        val columns = listOf(
            column1,
            column2,
            column3,
            column4,
            column5,
            column6,
            column7,
            column8,
            column9,
            column10,
            column11,
            column12,
            column13,
            column14,
            column15,
            column16,
            column17,
            column18,
            column19,
            column20,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep20<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20>
    }

    override fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
        column4: Column<T, V4>,
        column5: Column<T, V5>,
        column6: Column<T, V6>,
        column7: Column<T, V7>,
        column8: Column<T, V8>,
        column9: Column<T, V9>,
        column10: Column<T, V10>,
        column11: Column<T, V11>,
        column12: Column<T, V12>,
        column13: Column<T, V13>,
        column14: Column<T, V14>,
        column15: Column<T, V15>,
        column16: Column<T, V16>,
        column17: Column<T, V17>,
        column18: Column<T, V18>,
        column19: Column<T, V19>,
        column20: Column<T, V20>,
        column21: Column<T, V21>,
    ): InsertQueryValueStep21<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21> {
        val columns = listOf(
            column1,
            column2,
            column3,
            column4,
            column5,
            column6,
            column7,
            column8,
            column9,
            column10,
            column11,
            column12,
            column13,
            column14,
            column15,
            column16,
            column17,
            column18,
            column19,
            column20,
            column21,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep21<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21>
    }

    override fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21, V22> columns(
        column1: Column<T, V1>,
        column2: Column<T, V2>,
        column3: Column<T, V3>,
        column4: Column<T, V4>,
        column5: Column<T, V5>,
        column6: Column<T, V6>,
        column7: Column<T, V7>,
        column8: Column<T, V8>,
        column9: Column<T, V9>,
        column10: Column<T, V10>,
        column11: Column<T, V11>,
        column12: Column<T, V12>,
        column13: Column<T, V13>,
        column14: Column<T, V14>,
        column15: Column<T, V15>,
        column16: Column<T, V16>,
        column17: Column<T, V17>,
        column18: Column<T, V18>,
        column19: Column<T, V19>,
        column20: Column<T, V20>,
        column21: Column<T, V21>,
        column22: Column<T, V22>,
    ): InsertQueryValueStep22<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21, V22> {
        val columns = listOf(
            column1,
            column2,
            column3,
            column4,
            column5,
            column6,
            column7,
            column8,
            column9,
            column10,
            column11,
            column12,
            column13,
            column14,
            column15,
            column16,
            column17,
            column18,
            column19,
            column20,
            column21,
            column22,
        )

        builder.columns(columns)

        @Suppress("UNCHECKED_CAST")
        return this as InsertQueryValueStep22<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21, V22>
    }

    override fun values(
        value1: V1,
    ): InsertQueryValueStep1<T, V1> {
        val values = listOf(
            value1,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
    ): InsertQueryValueStep2<T, V1, V2> {
        val values = listOf(
            value1,
            value2,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
    ): InsertQueryValueStep3<T, V1, V2, V3> {
        val values = listOf(
            value1,
            value2,
            value3,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
    ): InsertQueryValueStep4<T, V1, V2, V3, V4> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
        value5: V5,
    ): InsertQueryValueStep5<T, V1, V2, V3, V4, V5> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
        value5: V5,
        value6: V6,
    ): InsertQueryValueStep6<T, V1, V2, V3, V4, V5, V6> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
        value5: V5,
        value6: V6,
        value7: V7,
    ): InsertQueryValueStep7<T, V1, V2, V3, V4, V5, V6, V7> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
        value5: V5,
        value6: V6,
        value7: V7,
        value8: V8,
    ): InsertQueryValueStep8<T, V1, V2, V3, V4, V5, V6, V7, V8> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
        value5: V5,
        value6: V6,
        value7: V7,
        value8: V8,
        value9: V9,
    ): InsertQueryValueStep9<T, V1, V2, V3, V4, V5, V6, V7, V8, V9> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
        value5: V5,
        value6: V6,
        value7: V7,
        value8: V8,
        value9: V9,
        value10: V10,
    ): InsertQueryValueStep10<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
        value5: V5,
        value6: V6,
        value7: V7,
        value8: V8,
        value9: V9,
        value10: V10,
        value11: V11,
    ): InsertQueryValueStep11<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
        value5: V5,
        value6: V6,
        value7: V7,
        value8: V8,
        value9: V9,
        value10: V10,
        value11: V11,
        value12: V12,
    ): InsertQueryValueStep12<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
        value5: V5,
        value6: V6,
        value7: V7,
        value8: V8,
        value9: V9,
        value10: V10,
        value11: V11,
        value12: V12,
        value13: V13,
    ): InsertQueryValueStep13<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
        value5: V5,
        value6: V6,
        value7: V7,
        value8: V8,
        value9: V9,
        value10: V10,
        value11: V11,
        value12: V12,
        value13: V13,
        value14: V14,
    ): InsertQueryValueStep14<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
        value5: V5,
        value6: V6,
        value7: V7,
        value8: V8,
        value9: V9,
        value10: V10,
        value11: V11,
        value12: V12,
        value13: V13,
        value14: V14,
        value15: V15,
    ): InsertQueryValueStep15<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
            value15,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
        value5: V5,
        value6: V6,
        value7: V7,
        value8: V8,
        value9: V9,
        value10: V10,
        value11: V11,
        value12: V12,
        value13: V13,
        value14: V14,
        value15: V15,
        value16: V16,
    ): InsertQueryValueStep16<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
            value15,
            value16,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
        value5: V5,
        value6: V6,
        value7: V7,
        value8: V8,
        value9: V9,
        value10: V10,
        value11: V11,
        value12: V12,
        value13: V13,
        value14: V14,
        value15: V15,
        value16: V16,
        value17: V17,
    ): InsertQueryValueStep17<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
            value15,
            value16,
            value17,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
        value5: V5,
        value6: V6,
        value7: V7,
        value8: V8,
        value9: V9,
        value10: V10,
        value11: V11,
        value12: V12,
        value13: V13,
        value14: V14,
        value15: V15,
        value16: V16,
        value17: V17,
        value18: V18,
    ): InsertQueryValueStep18<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
            value15,
            value16,
            value17,
            value18,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
        value5: V5,
        value6: V6,
        value7: V7,
        value8: V8,
        value9: V9,
        value10: V10,
        value11: V11,
        value12: V12,
        value13: V13,
        value14: V14,
        value15: V15,
        value16: V16,
        value17: V17,
        value18: V18,
        value19: V19,
    ): InsertQueryValueStep19<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
            value15,
            value16,
            value17,
            value18,
            value19,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
        value5: V5,
        value6: V6,
        value7: V7,
        value8: V8,
        value9: V9,
        value10: V10,
        value11: V11,
        value12: V12,
        value13: V13,
        value14: V14,
        value15: V15,
        value16: V16,
        value17: V17,
        value18: V18,
        value19: V19,
        value20: V20,
    ): InsertQueryValueStep20<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
            value15,
            value16,
            value17,
            value18,
            value19,
            value20,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
        value5: V5,
        value6: V6,
        value7: V7,
        value8: V8,
        value9: V9,
        value10: V10,
        value11: V11,
        value12: V12,
        value13: V13,
        value14: V14,
        value15: V15,
        value16: V16,
        value17: V17,
        value18: V18,
        value19: V19,
        value20: V20,
        value21: V21,
    ): InsertQueryValueStep21<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
            value15,
            value16,
            value17,
            value18,
            value19,
            value20,
            value21,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        value1: V1,
        value2: V2,
        value3: V3,
        value4: V4,
        value5: V5,
        value6: V6,
        value7: V7,
        value8: V8,
        value9: V9,
        value10: V10,
        value11: V11,
        value12: V12,
        value13: V13,
        value14: V14,
        value15: V15,
        value16: V16,
        value17: V17,
        value18: V18,
        value19: V19,
        value20: V20,
        value21: V21,
        value22: V22,
    ): InsertQueryValueStep22<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21, V22> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
            value15,
            value16,
            value17,
            value18,
            value19,
            value20,
            value21,
            value22,
        ).map {
            Param(it)
        }

        builder.values(values)

        return this
    }

    override fun values(
        vararg values: Any?,
    ): InsertQueryValueStepN<T> {
        builder.values(values.map { Param(it) })

        return this
    }

    override fun values(
        value1: Expression<V1>,
    ): InsertQueryValueStep1<T, V1> {
        val values = listOf(
            value1,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
    ): InsertQueryValueStep2<T, V1, V2> {
        val values = listOf(
            value1,
            value2,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
    ): InsertQueryValueStep3<T, V1, V2, V3> {
        val values = listOf(
            value1,
            value2,
            value3,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
    ): InsertQueryValueStep4<T, V1, V2, V3, V4> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
        value5: Expression<V5>,
    ): InsertQueryValueStep5<T, V1, V2, V3, V4, V5> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
        value5: Expression<V5>,
        value6: Expression<V6>,
    ): InsertQueryValueStep6<T, V1, V2, V3, V4, V5, V6> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
        value5: Expression<V5>,
        value6: Expression<V6>,
        value7: Expression<V7>,
    ): InsertQueryValueStep7<T, V1, V2, V3, V4, V5, V6, V7> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
        value5: Expression<V5>,
        value6: Expression<V6>,
        value7: Expression<V7>,
        value8: Expression<V8>,
    ): InsertQueryValueStep8<T, V1, V2, V3, V4, V5, V6, V7, V8> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
        value5: Expression<V5>,
        value6: Expression<V6>,
        value7: Expression<V7>,
        value8: Expression<V8>,
        value9: Expression<V9>,
    ): InsertQueryValueStep9<T, V1, V2, V3, V4, V5, V6, V7, V8, V9> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
        value5: Expression<V5>,
        value6: Expression<V6>,
        value7: Expression<V7>,
        value8: Expression<V8>,
        value9: Expression<V9>,
        value10: Expression<V10>,
    ): InsertQueryValueStep10<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
        value5: Expression<V5>,
        value6: Expression<V6>,
        value7: Expression<V7>,
        value8: Expression<V8>,
        value9: Expression<V9>,
        value10: Expression<V10>,
        value11: Expression<V11>,
    ): InsertQueryValueStep11<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
        value5: Expression<V5>,
        value6: Expression<V6>,
        value7: Expression<V7>,
        value8: Expression<V8>,
        value9: Expression<V9>,
        value10: Expression<V10>,
        value11: Expression<V11>,
        value12: Expression<V12>,
    ): InsertQueryValueStep12<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
        value5: Expression<V5>,
        value6: Expression<V6>,
        value7: Expression<V7>,
        value8: Expression<V8>,
        value9: Expression<V9>,
        value10: Expression<V10>,
        value11: Expression<V11>,
        value12: Expression<V12>,
        value13: Expression<V13>,
    ): InsertQueryValueStep13<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
        value5: Expression<V5>,
        value6: Expression<V6>,
        value7: Expression<V7>,
        value8: Expression<V8>,
        value9: Expression<V9>,
        value10: Expression<V10>,
        value11: Expression<V11>,
        value12: Expression<V12>,
        value13: Expression<V13>,
        value14: Expression<V14>,
    ): InsertQueryValueStep14<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
        value5: Expression<V5>,
        value6: Expression<V6>,
        value7: Expression<V7>,
        value8: Expression<V8>,
        value9: Expression<V9>,
        value10: Expression<V10>,
        value11: Expression<V11>,
        value12: Expression<V12>,
        value13: Expression<V13>,
        value14: Expression<V14>,
        value15: Expression<V15>,
    ): InsertQueryValueStep15<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
            value15,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
        value5: Expression<V5>,
        value6: Expression<V6>,
        value7: Expression<V7>,
        value8: Expression<V8>,
        value9: Expression<V9>,
        value10: Expression<V10>,
        value11: Expression<V11>,
        value12: Expression<V12>,
        value13: Expression<V13>,
        value14: Expression<V14>,
        value15: Expression<V15>,
        value16: Expression<V16>,
    ): InsertQueryValueStep16<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
            value15,
            value16,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
        value5: Expression<V5>,
        value6: Expression<V6>,
        value7: Expression<V7>,
        value8: Expression<V8>,
        value9: Expression<V9>,
        value10: Expression<V10>,
        value11: Expression<V11>,
        value12: Expression<V12>,
        value13: Expression<V13>,
        value14: Expression<V14>,
        value15: Expression<V15>,
        value16: Expression<V16>,
        value17: Expression<V17>,
    ): InsertQueryValueStep17<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
            value15,
            value16,
            value17,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
        value5: Expression<V5>,
        value6: Expression<V6>,
        value7: Expression<V7>,
        value8: Expression<V8>,
        value9: Expression<V9>,
        value10: Expression<V10>,
        value11: Expression<V11>,
        value12: Expression<V12>,
        value13: Expression<V13>,
        value14: Expression<V14>,
        value15: Expression<V15>,
        value16: Expression<V16>,
        value17: Expression<V17>,
        value18: Expression<V18>,
    ): InsertQueryValueStep18<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
            value15,
            value16,
            value17,
            value18,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
        value5: Expression<V5>,
        value6: Expression<V6>,
        value7: Expression<V7>,
        value8: Expression<V8>,
        value9: Expression<V9>,
        value10: Expression<V10>,
        value11: Expression<V11>,
        value12: Expression<V12>,
        value13: Expression<V13>,
        value14: Expression<V14>,
        value15: Expression<V15>,
        value16: Expression<V16>,
        value17: Expression<V17>,
        value18: Expression<V18>,
        value19: Expression<V19>,
    ): InsertQueryValueStep19<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
            value15,
            value16,
            value17,
            value18,
            value19,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
        value5: Expression<V5>,
        value6: Expression<V6>,
        value7: Expression<V7>,
        value8: Expression<V8>,
        value9: Expression<V9>,
        value10: Expression<V10>,
        value11: Expression<V11>,
        value12: Expression<V12>,
        value13: Expression<V13>,
        value14: Expression<V14>,
        value15: Expression<V15>,
        value16: Expression<V16>,
        value17: Expression<V17>,
        value18: Expression<V18>,
        value19: Expression<V19>,
        value20: Expression<V20>,
    ): InsertQueryValueStep20<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
            value15,
            value16,
            value17,
            value18,
            value19,
            value20,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
        value5: Expression<V5>,
        value6: Expression<V6>,
        value7: Expression<V7>,
        value8: Expression<V8>,
        value9: Expression<V9>,
        value10: Expression<V10>,
        value11: Expression<V11>,
        value12: Expression<V12>,
        value13: Expression<V13>,
        value14: Expression<V14>,
        value15: Expression<V15>,
        value16: Expression<V16>,
        value17: Expression<V17>,
        value18: Expression<V18>,
        value19: Expression<V19>,
        value20: Expression<V20>,
        value21: Expression<V21>,
    ): InsertQueryValueStep21<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
            value15,
            value16,
            value17,
            value18,
            value19,
            value20,
            value21,
        )

        builder.values(values)

        return this
    }

    override fun values(
        value1: Expression<V1>,
        value2: Expression<V2>,
        value3: Expression<V3>,
        value4: Expression<V4>,
        value5: Expression<V5>,
        value6: Expression<V6>,
        value7: Expression<V7>,
        value8: Expression<V8>,
        value9: Expression<V9>,
        value10: Expression<V10>,
        value11: Expression<V11>,
        value12: Expression<V12>,
        value13: Expression<V13>,
        value14: Expression<V14>,
        value15: Expression<V15>,
        value16: Expression<V16>,
        value17: Expression<V17>,
        value18: Expression<V18>,
        value19: Expression<V19>,
        value20: Expression<V20>,
        value21: Expression<V21>,
        value22: Expression<V22>,
    ): InsertQueryValueStep22<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21, V22> {
        val values = listOf(
            value1,
            value2,
            value3,
            value4,
            value5,
            value6,
            value7,
            value8,
            value9,
            value10,
            value11,
            value12,
            value13,
            value14,
            value15,
            value16,
            value17,
            value18,
            value19,
            value20,
            value21,
            value22,
        )

        builder.values(values)

        return this
    }

    override fun values(
        vararg values: Expression<*>,
    ): InsertQueryValueStepN<T> {
        builder.values(values.toList())

        return this
    }

    override fun values(values: Iterable<Expression<*>>): InsertQueryDsl<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21, V22> {
        builder.values(values)

        return this
    }

    override fun select(table: Table<*>): SqlQueryable<InsertQuery<T>> {
        builder.select(table)

        return this
    }

    override fun select(select: SelectQuery): SqlQueryable<InsertQuery<T>> {
        builder.select(DerivedTable<Any>(select))

        return this
    }

    override fun select(select: SqlQueryable<SelectQuery>): SqlQueryable<InsertQuery<T>> {
        builder.select(DerivedTable<Any>(select.toQuery()))

        return this
    }

    override fun toQuery(): InsertQuery<T> {
        return builder.build()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InsertQueryDsl<*, *, *, *, *, *, *, *, *, *, *, *, *, *, *, *, *, *, *, *, *, *, *>

        return builder == other.builder
    }

    override fun hashCode(): Int {
        return builder.hashCode()
    }

    override fun toString(): String {
        return "InsertQueryDsl(" +
            "builder=$builder)"
    }
}
