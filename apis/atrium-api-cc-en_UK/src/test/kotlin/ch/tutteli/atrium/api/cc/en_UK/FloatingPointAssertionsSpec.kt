package ch.tutteli.atrium.api.cc.en_UK

import ch.tutteli.atrium.AssertionVerbFactory
import ch.tutteli.atrium.creating.AssertionPlant
import java.math.BigDecimal
import kotlin.reflect.KFunction3

class FloatingPointAssertionsSpec : ch.tutteli.atrium.spec.assertions.FloatingPointAssertionsSpec(
    AssertionVerbFactory,
    toBeWithErrorToleranceFloatPair(),
    toBeWithErrorToleranceDoublePair(),
    toBeWithErrorToleranceBigDecimalPair()

) {
    companion object {

        private val toBeWithErrorToleranceFloatFun: KFunction3<AssertionPlant<Float>, Float, Float, AssertionPlant<Float>> = AssertionPlant<Float>::toBeWithErrorTolerance
        fun toBeWithErrorToleranceFloatPair()
            = "${toBeWithErrorToleranceFloatFun.name} for Float" to Companion::toBeWithErrorToleranceFloat

        private fun toBeWithErrorToleranceFloat(plant: AssertionPlant<Float>, expected: Float, tolerance: Float)
            = plant.toBeWithErrorTolerance(expected, tolerance)

        private val toBeWithErrorToleranceDoubleFun: KFunction3<AssertionPlant<Double>, Double, Double, AssertionPlant<Double>> = AssertionPlant<Double>::toBeWithErrorTolerance
        fun toBeWithErrorToleranceDoublePair()
            = "${toBeWithErrorToleranceDoubleFun.name} for Double" to Companion::toBeWithErrorToleranceDouble

        private fun toBeWithErrorToleranceDouble(plant: AssertionPlant<Double>, expected: Double, tolerance: Double)
            = plant.toBeWithErrorTolerance(expected, tolerance)

        private val toBeWithErrorToleranceBigDecimalFun: KFunction3<AssertionPlant<BigDecimal>, BigDecimal, BigDecimal, AssertionPlant<BigDecimal>> = AssertionPlant<BigDecimal>::toBeWithErrorTolerance
        fun toBeWithErrorToleranceBigDecimalPair()
            = "${toBeWithErrorToleranceBigDecimalFun.name} for BigDecimal" to Companion::toBeWithErrorToleranceBigDecimal

        private fun toBeWithErrorToleranceBigDecimal(plant: AssertionPlant<BigDecimal>, expected: BigDecimal, tolerance: BigDecimal)
            = plant.toBeWithErrorTolerance(expected, tolerance)
    }
}
