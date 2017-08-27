package ch.tutteli.atrium.spec.reporting

import ch.tutteli.atrium.AtriumFactory
import ch.tutteli.atrium.api.cc.en_UK.toBe
import ch.tutteli.atrium.assertions.*
import ch.tutteli.atrium.reporting.IAssertionFormatter
import ch.tutteli.atrium.reporting.IAssertionFormatterController
import ch.tutteli.atrium.reporting.translating.UsingDefaultTranslator
import ch.tutteli.atrium.spec.AssertionVerb
import ch.tutteli.atrium.spec.IAssertionVerbFactory
import ch.tutteli.atrium.spec.prefixedDescribe
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.SpecBody
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.it

abstract class InvisibleAssertionGroupFormatterSpec(
    verbs: IAssertionVerbFactory,
    testeeFactory: (IAssertionFormatterController) -> IAssertionFormatter,
    describePrefix: String = "[Atrium] "
) : Spek({

    fun prefixedDescribe(description: String, body: SpecBody.() -> Unit) {
        prefixedDescribe(describePrefix, description, body)
    }

    val bulletPoint = "▪"
    val facade = AtriumFactory.newAssertionFormatterFacade(AtriumFactory.newAssertionFormatterController())
    facade.register(testeeFactory)
    facade.register { AtriumFactory.newTextSameLineAssertionFormatter(bulletPoint, it, ToStringObjectFormatter, UsingDefaultTranslator()) }

    var sb = StringBuilder()
    afterEachTest {
        sb = StringBuilder()
    }

    val assertions = listOf(
        BasicAssertion(AssertionVerb.ASSERT, 1, true),
        BasicAssertion(AssertionVerb.EXPECT_THROWN, 2, true)
    )
    val invisibleAssertionGroup = InvisibleAssertionGroup(assertions)

    val separator = System.getProperty("line.separator")!!

    prefixedDescribe("fun ${IAssertionFormatter::formatGroup.name}") {
        context("${IAssertionGroup::class.simpleName} of type ${IInvisibleAssertionGroupType::class.simpleName}") {
            context("format directly the group (no prefix given)") {
                it("puts the assertions one under the others without indentation and without prefix") {
                    facade.format(invisibleAssertionGroup, sb, alwaysTrueAssertionFilter)
                    verbs.checkImmediately(sb.toString()).toBe("${AssertionVerb.ASSERT.getDefault()}: 1$separator"
                        + "${AssertionVerb.EXPECT_THROWN.getDefault()}: 2")
                }
            }

            context("has ${IAssertionGroup::name.name} and ${IAssertionGroup::subject.name}") {
                it("still puts the assertions one under the others without indentation and does not include ${IAssertionGroup::name.name} or ${IAssertionGroup::subject.name}") {
                    facade.format(AssertionGroup(object : IInvisibleAssertionGroupType {}, AssertionVerb.ASSERT, 2, assertions), sb, alwaysTrueAssertionFilter)
                    verbs.checkImmediately(sb.toString()).toBe("${AssertionVerb.ASSERT.getDefault()}: 1$separator"
                        + "${AssertionVerb.EXPECT_THROWN.getDefault()}: 2")
                }
            }

            val arrow = "->"
            val arrowIndent = " ".repeat(arrow.length + 1)

            context("in an ${IAssertionGroup::class.simpleName} of type ${IFeatureAssertionGroupType::class.simpleName}") {
                it("puts the assertions one under the others and indents as the other assertions and puts a prefix as well") {
                    val featureAssertions = listOf(invisibleAssertionGroup, BasicAssertion(AssertionVerb.ASSERT, 20, false))
                    val featureAssertionGroup = AssertionGroup(FeatureAssertionGroupType, AssertionVerb.ASSERT, 10, featureAssertions)
                    facade.format(featureAssertionGroup, sb, alwaysTrueAssertionFilter)
                    verbs.checkImmediately(sb.toString()).toBe(
                          "$arrow ${AssertionVerb.ASSERT.getDefault()}: 10$separator"
                        + "$arrowIndent$bulletPoint ${AssertionVerb.ASSERT.getDefault()}: 1$separator"
                        + "$arrowIndent$bulletPoint ${AssertionVerb.EXPECT_THROWN.getDefault()}: 2$separator"
                        + "$arrowIndent$bulletPoint ${AssertionVerb.ASSERT.getDefault()}: 20")
                }
            }
        }
    }
})
