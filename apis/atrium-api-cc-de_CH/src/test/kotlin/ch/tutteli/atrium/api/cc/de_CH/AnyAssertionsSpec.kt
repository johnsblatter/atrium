package ch.tutteli.atrium.api.cc.de_CH

import ch.tutteli.atrium.AssertionVerbFactory
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.creating.AssertionPlantNullable
import ch.tutteli.atrium.spec.assertions.AnyAssertionsSpec
import kotlin.reflect.KFunction2
import kotlin.reflect.KProperty1

class AnyAssertionsSpec : ch.tutteli.atrium.spec.assertions.AnyAssertionsSpec(
    AssertionVerbFactory,
    AnyAssertionsSpecFunFactory(),
    AnyAssertionsSpecFunFactory(),
    Assert<Int>::ist.name,
    Assert<Int>::istNicht.name,
    Assert<Int>::istSelbeInstanzWie.name,
    Assert<Int>::istNichtSelbeInstanzWie.name,
    AssertionPlantNullable<Int?>::istNull.name to AssertionPlantNullable<Int?>::istNull,
    getAndImmediatePair(),
    getAndLazyPair()
) {
    class AnyAssertionsSpecFunFactory<T : Any> : AnyAssertionsSpec.AnyAssertionsSpecFunFactory<T> {
        override val toBeFun = Assert<T>::ist
        override val notToBeFun = Assert<T>::istNicht
        override val isSameFun = Assert<T>::istSelbeInstanzWie
        override val isNotSameFun = Assert<T>::istNichtSelbeInstanzWie
    }

    companion object {

        private fun andImmediateName(): String {
            val f: KProperty1<Assert<Int>, Assert<Int>> = Assert<Int>::und
            return f.name
        }

        fun getAndImmediatePair(): Pair<String, Assert<Int>.() -> Assert<Int>>
            = andImmediateName() to Assert<Int>::und

        private fun andLazyName(): String {
            val f: KFunction2<Assert<Int>, Assert<Int>.() -> Unit, Assert<Int>> = Assert<Int>::und
            return f.name
        }

        fun getAndLazyPair(): Pair<String, Assert<Int>.(Assert<Int>.() -> Unit) -> Assert<Int>>
            = andLazyName() to Assert<Int>::und
    }
}
