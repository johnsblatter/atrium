package ch.tutteli.atrium.verbs.expect

import ch.tutteli.atrium.AtriumFactory
import ch.tutteli.atrium.IAtriumFactory
import ch.tutteli.atrium.assertions.Assertion
import ch.tutteli.atrium.assertions.throwable.thrown.builders.ThrowableThrownBuilder
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.creating.AssertionPlant
import ch.tutteli.atrium.creating.AssertionPlantNullable
import ch.tutteli.atrium.reporting.Reporter
import ch.tutteli.atrium.verbs.AssertionVerb.EXPECT
import ch.tutteli.atrium.verbs.AssertionVerb.EXPECT_THROWN
import ch.tutteli.atrium.verbs.AtriumReporterSupplier

/**
 * Creates an [AssertionPlant] for the given [subject].
 *
 * @return The newly created plant.
 *
 * @see AtriumFactory.newReportingPlant
 */
fun <T : Any> expect(subject: T)
    = AtriumFactory.newReportingPlant(EXPECT, subject, AtriumReporterSupplier.REPORTER)

/**
 * Creates an [AssertionPlant] for the given [subject] and [AssertionPlant.addAssertionsCreatedBy] the
 * given [assertionCreator] lambda where the created [Assertion]s are added as a group and usually (depending on
 * the configured [Reporter]) reported as a whole.
 *
 * @return The newly created plant.
 *
 * @see IAtriumFactory.newReportingPlantAndAddAssertionsCreatedBy
 */
fun <T : Any> expect(subject: T, assertionCreator: Assert<T>.() -> Unit)
    = AtriumFactory.newReportingPlantAndAddAssertionsCreatedBy(EXPECT, subject, AtriumReporterSupplier.REPORTER, assertionCreator)

/**
 * Creates an [AssertionPlantNullable] for the given [subject] which might be `null`.
 *
 * @return The newly created plant.
 *
 * @see AtriumFactory.newReportingPlantNullable
 */
fun <T : Any?> expect(subject: T)
    = AtriumFactory.newReportingPlantNullable(EXPECT, subject, AtriumReporterSupplier.REPORTER)

/**
 * Creates a [ThrowableThrownBuilder] for the given function [act] which is expected to throw a [Throwable].
 *
 * @return The newly created [ThrowableThrownBuilder].
 */
fun expect(act: () -> Unit)
    = ThrowableThrownBuilder(EXPECT_THROWN, act, AtriumReporterSupplier.REPORTER)
