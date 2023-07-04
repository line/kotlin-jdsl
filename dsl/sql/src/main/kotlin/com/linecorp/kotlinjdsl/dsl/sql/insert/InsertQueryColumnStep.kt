package com.linecorp.kotlinjdsl.dsl.sql.insert

interface InsertQueryColumnStep<T : Any> {
    fun <V1> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
    ): InsertQueryValueStep1<T, V1>

    fun <V1, V2> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
    ): InsertQueryValueStep2<T, V1, V2>

    fun <V1, V2, V3> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
    ): InsertQueryValueStep3<T, V1, V2, V3>

    fun <V1, V2, V3, V4> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
        column4: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V4>,
    ): InsertQueryValueStep4<T, V1, V2, V3, V4>

    fun <V1, V2, V3, V4, V5> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
        column4: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V4>,
        column5: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V5>,
    ): InsertQueryValueStep5<T, V1, V2, V3, V4, V5>

    fun <V1, V2, V3, V4, V5, V6> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
        column4: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V4>,
        column5: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V5>,
        column6: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V6>,
    ): InsertQueryValueStep6<T, V1, V2, V3, V4, V5, V6>

    fun <V1, V2, V3, V4, V5, V6, V7> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
        column4: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V4>,
        column5: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V5>,
        column6: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V6>,
        column7: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V7>,
    ): InsertQueryValueStep7<T, V1, V2, V3, V4, V5, V6, V7>

    fun <V1, V2, V3, V4, V5, V6, V7, V8> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
        column4: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V4>,
        column5: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V5>,
        column6: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V6>,
        column7: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V7>,
        column8: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V8>,
    ): InsertQueryValueStep8<T, V1, V2, V3, V4, V5, V6, V7, V8>

    fun <V1, V2, V3, V4, V5, V6, V7, V8, V9> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
        column4: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V4>,
        column5: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V5>,
        column6: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V6>,
        column7: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V7>,
        column8: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V8>,
        column9: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V9>,
    ): InsertQueryValueStep9<T, V1, V2, V3, V4, V5, V6, V7, V8, V9>

    fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
        column4: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V4>,
        column5: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V5>,
        column6: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V6>,
        column7: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V7>,
        column8: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V8>,
        column9: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V9>,
        column10: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V10>,
    ): InsertQueryValueStep10<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10>

    fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
        column4: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V4>,
        column5: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V5>,
        column6: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V6>,
        column7: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V7>,
        column8: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V8>,
        column9: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V9>,
        column10: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V10>,
        column11: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V11>,
    ): InsertQueryValueStep11<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11>

    fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
        column4: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V4>,
        column5: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V5>,
        column6: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V6>,
        column7: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V7>,
        column8: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V8>,
        column9: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V9>,
        column10: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V10>,
        column11: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V11>,
        column12: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V12>,
    ): InsertQueryValueStep12<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12>

    fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
        column4: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V4>,
        column5: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V5>,
        column6: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V6>,
        column7: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V7>,
        column8: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V8>,
        column9: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V9>,
        column10: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V10>,
        column11: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V11>,
        column12: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V12>,
        column13: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V13>,
    ): InsertQueryValueStep13<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13>

    fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
        column4: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V4>,
        column5: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V5>,
        column6: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V6>,
        column7: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V7>,
        column8: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V8>,
        column9: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V9>,
        column10: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V10>,
        column11: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V11>,
        column12: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V12>,
        column13: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V13>,
        column14: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V14>,
    ): InsertQueryValueStep14<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14>

    fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
        column4: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V4>,
        column5: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V5>,
        column6: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V6>,
        column7: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V7>,
        column8: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V8>,
        column9: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V9>,
        column10: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V10>,
        column11: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V11>,
        column12: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V12>,
        column13: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V13>,
        column14: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V14>,
        column15: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V15>,
    ): InsertQueryValueStep15<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15>

    fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
        column4: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V4>,
        column5: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V5>,
        column6: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V6>,
        column7: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V7>,
        column8: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V8>,
        column9: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V9>,
        column10: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V10>,
        column11: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V11>,
        column12: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V12>,
        column13: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V13>,
        column14: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V14>,
        column15: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V15>,
        column16: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V16>,
    ): InsertQueryValueStep16<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16>

    fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
        column4: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V4>,
        column5: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V5>,
        column6: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V6>,
        column7: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V7>,
        column8: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V8>,
        column9: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V9>,
        column10: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V10>,
        column11: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V11>,
        column12: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V12>,
        column13: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V13>,
        column14: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V14>,
        column15: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V15>,
        column16: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V16>,
        column17: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V17>,
    ): InsertQueryValueStep17<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17>

    fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
        column4: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V4>,
        column5: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V5>,
        column6: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V6>,
        column7: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V7>,
        column8: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V8>,
        column9: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V9>,
        column10: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V10>,
        column11: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V11>,
        column12: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V12>,
        column13: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V13>,
        column14: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V14>,
        column15: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V15>,
        column16: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V16>,
        column17: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V17>,
        column18: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V18>,
    ): InsertQueryValueStep18<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18>

    fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
        column4: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V4>,
        column5: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V5>,
        column6: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V6>,
        column7: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V7>,
        column8: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V8>,
        column9: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V9>,
        column10: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V10>,
        column11: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V11>,
        column12: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V12>,
        column13: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V13>,
        column14: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V14>,
        column15: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V15>,
        column16: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V16>,
        column17: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V17>,
        column18: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V18>,
        column19: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V19>,
    ): InsertQueryValueStep19<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19>

    fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
        column4: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V4>,
        column5: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V5>,
        column6: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V6>,
        column7: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V7>,
        column8: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V8>,
        column9: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V9>,
        column10: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V10>,
        column11: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V11>,
        column12: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V12>,
        column13: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V13>,
        column14: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V14>,
        column15: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V15>,
        column16: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V16>,
        column17: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V17>,
        column18: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V18>,
        column19: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V19>,
        column20: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V20>,
    ): InsertQueryValueStep20<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20>

    fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
        column4: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V4>,
        column5: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V5>,
        column6: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V6>,
        column7: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V7>,
        column8: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V8>,
        column9: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V9>,
        column10: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V10>,
        column11: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V11>,
        column12: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V12>,
        column13: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V13>,
        column14: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V14>,
        column15: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V15>,
        column16: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V16>,
        column17: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V17>,
        column18: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V18>,
        column19: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V19>,
        column20: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V20>,
        column21: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V21>,
    ): InsertQueryValueStep21<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21>

    fun <V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21, V22> columns(
        column1: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V1>,
        column2: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V2>,
        column3: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V3>,
        column4: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V4>,
        column5: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V5>,
        column6: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V6>,
        column7: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V7>,
        column8: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V8>,
        column9: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V9>,
        column10: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V10>,
        column11: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V11>,
        column12: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V12>,
        column13: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V13>,
        column14: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V14>,
        column15: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V15>,
        column16: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V16>,
        column17: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V17>,
        column18: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V18>,
        column19: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V19>,
        column20: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V20>,
        column21: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V21>,
        column22: com.linecorp.kotlinjdsl.querymodel.sql.Column<T, V22>,
    ): InsertQueryValueStep22<T, V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18, V19, V20, V21, V22>
}
