package ch.tutteli.atrium.verbs.assert

import ch.tutteli.atrium.CoreFactory
import ch.tutteli.atrium.ICoreFactory
import ch.tutteli.atrium.assertions.Assertion
import ch.tutteli.atrium.assertions.throwable.thrown.builders.ThrowableThrownBuilder
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.creating.AssertionPlant
import ch.tutteli.atrium.creating.AssertionPlantNullable
import ch.tutteli.atrium.reporting.Reporter
import ch.tutteli.atrium.verbs.AssertionVerb.ASSERT
import ch.tutteli.atrium.verbs.AssertionVerb.ASSERT_THROWN
import ch.tutteli.atrium.verbs.AtriumReporterSupplier

/**
 * Creates an [AssertionPlant] for the given [subject].
 *
 * @return The newly created plant.
 *
 * @see CoreFactory.newReportingPlant
 */
fun <T : Any> assert(subject: T)
    = CoreFactory.newReportingPlant(ASSERT, subject, AtriumReporterSupplier.REPORTER)

/**
 * Creates an [AssertionPlant] for the given [subject] and [AssertionPlant.addAssertionsCreatedBy] the
 * given [assertionCreator] lambda where the created [Assertion]s are added as a group and usually (depending on
 * the configured [Reporter]) reported as a whole.
 *
 * @return The newly created plant.
 *
 * @see ICoreFactory.newReportingPlantAndAddAssertionsCreatedBy
 */
fun <T : Any> assert(subject: T, assertionCreator: Assert<T>.() -> Unit)
    = CoreFactory.newReportingPlantAndAddAssertionsCreatedBy(ASSERT, subject, AtriumReporterSupplier.REPORTER, assertionCreator)

/**
 * Creates an [AssertionPlantNullable] for the given [subject] which might be `null`.
 *
 * @return The newly created plant.
 *
 * @see CoreFactory.newReportingPlantNullable
 */
fun <T : Any?> assert(subject: T)
    = CoreFactory.newReportingPlantNullable(ASSERT, subject, AtriumReporterSupplier.REPORTER)

/**
 * Creates a [ThrowableThrownBuilder] for the given function [act] which is expected to throw a [Throwable].
 *
 * @return The newly created [ThrowableThrownBuilder].
 */
fun assert(act: () -> Unit)
    = ThrowableThrownBuilder(ASSERT_THROWN, act, AtriumReporterSupplier.REPORTER)


