package ch.tutteli.atrium.spec.assertions

import ch.tutteli.atrium.AtriumFactory
import ch.tutteli.atrium.assertions.AssertionBuilder
import ch.tutteli.atrium.assertions.ExplanatoryAssertionGroup
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.creating.PlantHasNoSubjectException
import ch.tutteli.atrium.spec.AssertionVerb
import org.jetbrains.spek.api.Spek

abstract class SubjectLessAssertionSpec<T : Any>(
    groupPrefix: String,
    vararg assertionCreator: Pair<String, Assert<T>.() -> Unit>
) : Spek({

    group("${groupPrefix}assertion function can be used in ${ExplanatoryAssertionGroup::class.simpleName} and reported without failure") {
        assertionCreator.forEach { (name, createAssertion) ->
            test("fun `$name`") {
                val assertions = AtriumFactory.newCollectingPlant<T>({ throw PlantHasNoSubjectException("subject was accessed outside of the Assertion::holds scope") })
                    .addAssertionsCreatedBy(createAssertion)
                    .getAssertions()
                val plant = AtriumFactory.newReportingPlant(AssertionVerb.ASSERT, 1.0,
                    AtriumFactory.newOnlyFailureReporter(
                        AtriumFactory.newAssertionFormatterFacade(AtriumFactory.newAssertionFormatterController())
                    ))
                val explanatoryGroup = AssertionBuilder.explanatory.withDefault.create(assertions)
                plant.addAssertion(explanatoryGroup)
            }
        }
    }
})

/**
 * Helper function to map an arbitrary `IAssert<T>.(...) -> Unit` function to a parameter-less one.
 */
fun <T : Any> mapToCreateAssertion(createAssertion: Assert<T>.() -> Unit): Assert<T>.() -> Unit
    = createAssertion
