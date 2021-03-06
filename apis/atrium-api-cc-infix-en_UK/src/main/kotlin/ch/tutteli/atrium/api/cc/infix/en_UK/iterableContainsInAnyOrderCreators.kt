package ch.tutteli.atrium.api.cc.infix.en_UK

import ch.tutteli.atrium.assertions.iterable.contains.builders.IterableContainsCheckerBuilder
import ch.tutteli.atrium.assertions.iterable.contains.creators._containsEntriesInAnyOrder
import ch.tutteli.atrium.assertions.iterable.contains.creators._containsObjectsInAnyOrder
import ch.tutteli.atrium.assertions.iterable.contains.searchbehaviours.IterableContainsInAnyOrderSearchBehaviour
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.creating.AssertionPlant

/**
 * Finishes the specification of the sophisticated `contains` assertion where the [expected] value shall be searched
 * within the [Iterable].
 *
 * Delegates to `the Objects(expected)`.
 *
 * @param expected The value which is expected to be contained within the [Iterable].
 *
 * @return The [AssertionPlant] for which the assertion was built to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
infix fun <E, T : Iterable<E>> IterableContainsCheckerBuilder<E, T, IterableContainsInAnyOrderSearchBehaviour>.value(expected: E): AssertionPlant<T>
    = this the Objects(expected)

/**
 * Finishes the specification of the sophisticated `contains` assertion where the expected [values]
 * shall be searched within the [Iterable].
 *
 * Delegates to `the Objects(values)`.
 *
 * @param values The values which are expected to be contained within the [Iterable].
 *
 * @return The [AssertionPlant] for which the assertion was built to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
infix fun <E, T : Iterable<E>> IterableContainsCheckerBuilder<E, T, IterableContainsInAnyOrderSearchBehaviour>.the(values: Values<E>): AssertionPlant<T>
    = this the Objects(values)


/**
 * Finishes the specification of the sophisticated `contains` assertion where the [expected] object shall be searched
 * within the [Iterable].
 *
 * Delegates to `the Objects(expected)`.
 *
 * @param expected The object which is expected to be contained within the [Iterable].
 *
 * @return The [AssertionPlant] for which the assertion was built to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
infix fun <E, T : Iterable<E>> IterableContainsCheckerBuilder<E, T, IterableContainsInAnyOrderSearchBehaviour>.`object`(expected: E): AssertionPlant<T>
    = this the Objects(expected)

/**
 * Finishes the specification of the sophisticated `contains` assertion where the expected [objects]
 * shall be searched within the iterable.
 *
 * Notice, that it does not search for unique matches. Meaning, if the iterable is `setOf('a', 'b')` and [Objects.expected] is
 * defined as `'a'` and one [Objects.otherExpected] is defined as `'a'` as well, then both match, even though they match the
 * same entry. Use an option such as [atLeast], [atMost] and [exactly] to control the number of occurrences you expect.
 *
 * Meaning you might want to use:
 *   `to contain inAny order exactly 2 `&#96;`object`&#96;` 'a'`
 * instead of:
 *   `to contain inAny order exactly 1 the Objects('a', 'a')`
 *
 * @param objects The objects which are expected to be contained within the [Iterable].
 *
 * @return The [AssertionPlant] for which the assertion was built to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
infix fun <E, T : Iterable<E>> IterableContainsCheckerBuilder<E, T, IterableContainsInAnyOrderSearchBehaviour>.the(objects: Objects<E>): AssertionPlant<T>
    = addAssertion(_containsObjectsInAnyOrder(this, objects.expected, objects.otherExpected))


/**
 * Finishes the specification of the sophisticated `contains` assertion where an entry shall be searched which holds
 * all assertions [assertionCreator] might create.
 *
 * Delegates to `the Entries(assertionCreator)`.
 *
 * @param assertionCreator The lambda function which creates the assertions which the entry we are looking for
 *   has to hold; or in other words, the function which defines whether an entry is the one we are looking for
 *   or not.
 *
 * @return The [AssertionPlant] for which the assertion was built to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
infix fun <E : Any, T : Iterable<E>> IterableContainsCheckerBuilder<E, T, IterableContainsInAnyOrderSearchBehaviour>.entry(assertionCreator: Assert<E>.() -> Unit): AssertionPlant<T>
    = this the Entries(assertionCreator)

/**
 * Finishes the specification of the sophisticated `contains` assertion where an entry shall be searched which holds
 * all assertions [Entries.assertionCreator] might create and search for entries which hold (one by one) the assertions
 * created by the [Entries.otherAssertionCreators].
 *
 * @param entries The method object which contains the identification lambdas.
 *
 * @return The [AssertionPlant] for which the assertion was built to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
infix fun <E : Any, T : Iterable<E>> IterableContainsCheckerBuilder<E, T, IterableContainsInAnyOrderSearchBehaviour>.the(entries: Entries<E, Assert<E>.() -> Unit>): AssertionPlant<T>
    = addAssertion(_containsEntriesInAnyOrder(this, entries.assertionCreator, entries.otherAssertionCreators))

/**
 * Finishes the specification of the sophisticated `contains` assertion where an entry shall be searched which either
 * holds all assertions [assertionCreator] might create or is `null` in case [assertionCreator] is null as well.
 *
 * Delegates to `entries(expected)`.
 *
 * @param assertionCreator The lambda function which creates the assertions which the entry we are looking for
 *   has to hold; or in other words, the function which defines whether an entry is the one we are looking for
 *   or not.
 *
 * @return The [AssertionPlant] for which the assertion was built to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
@JvmName("nullableEntry")
infix fun <E : Any, T : Iterable<E?>> IterableContainsCheckerBuilder<E?, T, IterableContainsInAnyOrderSearchBehaviour>.entry(assertionCreator: (Assert<E>.() -> Unit)?): AssertionPlant<T>
    = this the Entries(assertionCreator)

/**
 * Finishes the specification of the sophisticated `contains` assertion where an entry shall be searched which either
 * holds all assertions [Entries.assertionCreator] might create or is `null` in case [Entries.assertionCreator] is null as well --
 * likewise an entry (can be the same) is searched for each of the [Entries.otherAssertionCreators].
 *
 * @param entries The method object which contains the identification lambdas.
 *
 * @return The [AssertionPlant] for which the assertion was built to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
@JvmName("nullableEntries")
infix fun <E : Any, T : Iterable<E?>> IterableContainsCheckerBuilder<E?, T, IterableContainsInAnyOrderSearchBehaviour>.the(entries: Entries<E, (Assert<E>.() -> Unit)?>): AssertionPlant<T>
    = addAssertion(_containsEntriesInAnyOrder(this, entries.assertionCreator, entries.otherAssertionCreators))
