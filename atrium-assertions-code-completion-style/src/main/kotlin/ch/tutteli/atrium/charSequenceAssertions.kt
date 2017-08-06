package ch.tutteli.atrium

import ch.tutteli.atrium.assertions.*
import ch.tutteli.atrium.builders.charsequence.contains.CharSequenceContainsBuilder
import ch.tutteli.atrium.creating.IAssertionPlant
import ch.tutteli.atrium.reporting.translating.ITranslatable

/**
 * Creates an [CharSequenceContainsBuilder] based on this [IAssertionPlant] which allows to define
 * more sophisticated `contains` assertions.
 *
 * @return The newly created builder.
 */
val <T : CharSequence> IAssertionPlant<T>.contains get() = CharSequenceContainsBuilder(this)

/**
 * Makes the assertion that [IAssertionPlant.subject] contains [expected]'s [toString] representation
 * and the [toString] representation of the [otherExpected] (if defined).
 *
 * @return This plant to support a fluent-style API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct
 */
fun <T : CharSequence> IAssertionPlant<T>.contains(expected: Any, vararg otherExpected: Any): IAssertionPlant<T>
    = addAssertion(_contains(this, expected, *otherExpected))

/**
 * Makes the assertion that [IAssertionPlant.subject] does not contain [expected]'s [toString] representation
 * and neither one of the [otherExpected]'s [toString] representation (if defined).
 *
 * @return This plant to support a fluent-style API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct
 */
fun <T : CharSequence> IAssertionPlant<T>.containsNot(expected: Any, vararg otherExpected: Any): IAssertionPlant<T>
    = addAssertion(_containsNot(this, expected, *otherExpected))

/**
 * Makes the assertion that [IAssertionPlant.subject] contains [expected]'s [getDefault][ITranslatable.getDefault]
 * representation and the [getDefault][ITranslatable.getDefault] representations of the [otherExpected] (if defined).
 *
 * @return This plant to support a fluent-style API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct
 */
fun <T : CharSequence> IAssertionPlant<T>.containsDefaultTranslationOf(expected: ITranslatable, vararg otherExpected: ITranslatable): IAssertionPlant<T>
    = addAssertion(_containsDefaultTranslationOf(this, expected, *otherExpected))

/**
 * Makes the assertion that [IAssertionPlant.subject] does  not contain [expected]'s
 * [getDefault][ITranslatable.getDefault] representation and neither one of the [otherExpected]'s
 * [getDefault][ITranslatable.getDefault] representation (if defined).
 *
 * @return This plant to support a fluent-style API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct
 */
fun <T : CharSequence> IAssertionPlant<T>.containsNotDefaultTranslationOf(expected: ITranslatable, vararg otherExpected: ITranslatable): IAssertionPlant<T>
    = addAssertion(_containsNotDefaultTranslationOf(this, expected, *otherExpected))

/**
 * Makes the assertion that [IAssertionPlant.subject] starts with [expected].
 *
 * @return This plant to support a fluent-style API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct
 */
fun <T : CharSequence> IAssertionPlant<T>.startsWith(expected: CharSequence): IAssertionPlant<T>
    = addAssertion(_startsWith(this, expected))

/**
 * Makes the assertion that [IAssertionPlant.subject] does not start with [expected].
 *
 * @return This plant to support a fluent-style API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct
 */
fun <T : CharSequence> IAssertionPlant<T>.startsNotWith(expected: CharSequence): IAssertionPlant<T>
    = addAssertion(_startsNotWith(this, expected))


/**
 * Makes the assertion that [IAssertionPlant.subject] ends with [expected].
 *
 * @return This plant to support a fluent-style API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct
 */
fun <T : CharSequence> IAssertionPlant<T>.endsWith(expected: CharSequence): IAssertionPlant<T>
    = addAssertion(_endsWith(this, expected))

/**
 * Makes the assertion that [IAssertionPlant.subject] does not end with [expected].
 *
 * @return This plant to support a fluent-style API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct
 */
fun <T : CharSequence> IAssertionPlant<T>.endsNotWith(expected: CharSequence): IAssertionPlant<T>
    = addAssertion(_endsNotWith(this, expected))


/**
 * Makes the assertion that [IAssertionPlant.subject] [CharSequence].[kotlin.text.isEmpty].
 *
 * @return This plant to support a fluent-style API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct
 */
fun <T : CharSequence> IAssertionPlant<T>.isEmpty(): IAssertionPlant<T>
    = addAssertion(_isEmpty(this))

/**
 * Makes the assertion that [IAssertionPlant.subject] [CharSequence].[kotlin.text.isNotEmpty].
 *
 * @return This plant to support a fluent-style API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct
 */
fun <T : CharSequence> IAssertionPlant<T>.isNotEmpty(): IAssertionPlant<T>
    = addAssertion(_isNotEmpty(this))
