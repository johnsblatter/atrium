package ch.tutteli.atrium.spec.reporting

import ch.tutteli.atrium.assertions.Assertion
import ch.tutteli.atrium.assertions.AssertionGroup
import ch.tutteli.atrium.assertions.BulletPointIdentifier
import ch.tutteli.atrium.assertions.ExplanatoryAssertionGroupType
import ch.tutteli.atrium.reporting.AssertionFormatter
import ch.tutteli.atrium.reporting.AssertionFormatterController
import ch.tutteli.atrium.spec.IAssertionVerbFactory
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.include

abstract class TextExplanatoryBasedAssertionGroupFormatterSpec<T : ExplanatoryAssertionGroupType>(
    verbs: IAssertionVerbFactory,
    testeeFactory: (Map<Class<out BulletPointIdentifier>, String>, AssertionFormatterController) -> AssertionFormatter,
    assertionGroupTypeClass: Class<T>,
    anonymousAssertionGroupType: T,
    groupFactory: (List<Assertion>) -> AssertionGroup,
    describePrefix: String = "[Atrium] "
) : Spek({

    include(object : ch.tutteli.atrium.spec.reporting.TextIndentBasedAssertionGroupFormatterSpec<T>(
        verbs,
        testeeFactory,
        assertionGroupTypeClass,
        anonymousAssertionGroupType,
        groupFactory,
        describePrefix
    ) {})
})
