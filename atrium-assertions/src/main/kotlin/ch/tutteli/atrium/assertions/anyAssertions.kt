package ch.tutteli.atrium.assertions

import ch.tutteli.atrium.assertions.DescriptionAnyAssertion.*
import ch.tutteli.atrium.creating.AssertionPlant
import ch.tutteli.atrium.creating.AssertionPlantNullable
import ch.tutteli.atrium.reporting.RawString

fun <T : Any> _toBe(plant: AssertionPlant<T>, expected: T): Assertion
    = BasicDescriptiveAssertion(TO_BE, expected, { plant.subject == expected })

fun <T : Any> _notToBe(plant: AssertionPlant<T>, expected: T): Assertion
    = BasicDescriptiveAssertion(NOT_TO_BE, expected, { plant.subject != expected })

fun <T : Any> _isSame(plant: AssertionPlant<T>, expected: T): Assertion
    = BasicDescriptiveAssertion(IS_SAME, expected, { plant.subject === expected })

fun <T : Any> _isNotSame(plant: AssertionPlant<T>, expected: T): Assertion
    = BasicDescriptiveAssertion(IS_NOT_SAME, expected, { plant.subject !== expected })

fun <T : Any?> _isNull(plant: AssertionPlantNullable<T>): Assertion
    = BasicDescriptiveAssertion(TO_BE, RawString.NULL, { plant.subject == null })
