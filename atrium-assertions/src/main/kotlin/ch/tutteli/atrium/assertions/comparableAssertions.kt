package ch.tutteli.atrium.assertions

import ch.tutteli.atrium.assertions.DescriptionComparableAssertion.*
import ch.tutteli.atrium.creating.AssertionPlant

fun <T : Comparable<T>> _isLessThan(plant: AssertionPlant<T>, expected: T)
    = AssertionBuilder.descriptive.create(IS_LESS_THAN, expected, { plant.subject < expected })

fun <T : Comparable<T>> _isLessOrEquals(plant: AssertionPlant<T>, expected: T)
    = AssertionBuilder.descriptive.create(IS_LESS_OR_EQUALS, expected, { plant.subject <= expected })

fun <T : Comparable<T>> _isGreaterThan(plant: AssertionPlant<T>, expected: T)
    = AssertionBuilder.descriptive.create(IS_GREATER_THAN, expected, { plant.subject > expected })

fun <T : Comparable<T>> _isGreaterOrEquals(plant: AssertionPlant<T>, expected: T)
    = AssertionBuilder.descriptive.create(IS_GREATER_OR_EQUALS, expected, { plant.subject >= expected })
