package ch.tutteli.atrium.spec.reporting

import ch.tutteli.atrium.AtriumFactory
import ch.tutteli.atrium.api.cc.en_UK.isTrue
import ch.tutteli.atrium.api.cc.en_UK.toBe
import ch.tutteli.atrium.assertions.*
import ch.tutteli.atrium.reporting.AssertionFormatter
import ch.tutteli.atrium.reporting.AssertionFormatterController
import ch.tutteli.atrium.reporting.ObjectFormatter
import ch.tutteli.atrium.reporting.translating.Translator
import ch.tutteli.atrium.reporting.translating.Untranslatable
import ch.tutteli.atrium.reporting.translating.UsingDefaultTranslator
import ch.tutteli.atrium.spec.AssertionVerb
import ch.tutteli.atrium.spec.IAssertionVerbFactory
import ch.tutteli.atrium.spec.describeFun
import ch.tutteli.atrium.spec.reporting.translating.TranslatorIntSpec
import org.jetbrains.spek.api.dsl.SpecBody
import org.jetbrains.spek.api.dsl.context
import org.jetbrains.spek.api.dsl.it

abstract class TextListBasedAssertionGroupFormatterSpec<T : AssertionGroupType>(
    verbs: IAssertionVerbFactory,
    testeeFactory: (Map<Class<out BulletPointIdentifier>, String>, AssertionFormatterController, ObjectFormatter, Translator) -> AssertionFormatter,
    assertionGroupClass: Class<T>,
    assertionGroupType: T,
    anonymousAssertionGroupType: T,
    describePrefix: String = "[Atrium] "
) : AssertionFormatterSpecBase({

    fun describeFun(vararg funName: String, body: SpecBody.() -> Unit)
        = describeFun(describePrefix, funName, body = body)

    val assertions = listOf(
        BasicDescriptiveAssertion(AssertionVerb.ASSERT, 1, true),
        BasicDescriptiveAssertion(AssertionVerb.EXPECT_THROWN, 2, true)
    )
    val listAssertionGroup = AssertionGroup.Builder.withType(anonymousAssertionGroupType).create( TranslatorIntSpec.TestTranslatable.PLACEHOLDER, 2, assertions)

    describeFun(AssertionFormatter::canFormat.name) {
        val testee = testeeFactory(bulletPoints, AtriumFactory.newAssertionFormatterController(), ToStringObjectFormatter, UsingDefaultTranslator())
        it("returns true for an ${AssertionGroup::class.simpleName} with type object: ${assertionGroupClass.simpleName}") {
            val result = testee.canFormat(AssertionGroup.Builder.withType(anonymousAssertionGroupType).create( Untranslatable.EMPTY, 1, listOf()))
            verbs.checkImmediately(result).isTrue()
        }
    }

    describeFun(AssertionFormatter::formatGroup.name) {

        mapOf(
            "•" to "▪",
            "[]" to "{}").forEach { (listBulletPoint, bulletPoint) ->
            val listIndent = " ".repeat(listBulletPoint.length + 1)
            val indent = " ".repeat(bulletPoint.length + 1)
            context("listBulletPoint: $listBulletPoint, bulletPoint: $bulletPoint") {
                val bulletPoints = mapOf(
                    RootAssertionGroupType::class.java to "$bulletPoint ",
                    ListAssertionGroupType::class.java to "$listBulletPoint ",
                    PrefixFeatureAssertionGroupHeader::class.java to "$arrow ",
                    FeatureAssertionGroupType::class.java to "$bulletPoint "
                )
                val facade = createFacade()
                facade.register({ testeeFactory(bulletPoints, it, ToStringObjectFormatter, UsingDefaultTranslator()) })
                facade.register({ AtriumFactory.newTextFeatureAssertionGroupFormatter(bulletPoints, it, ToStringObjectFormatter, UsingDefaultTranslator()) })
                facade.register({ AtriumFactory.newTextFallbackAssertionFormatter(bulletPoints, it, ToStringObjectFormatter, UsingDefaultTranslator()) })

                context("${AssertionGroup::class.simpleName} of type object: ${assertionGroupClass.simpleName}") {
                    context("format directly the group") {
                        it("includes the group ${AssertionGroup::name.name}, its ${AssertionGroup::subject.name} as well as the ${AssertionGroup::assertions.name} which are prepended with a `$listBulletPoint` as bullet point") {
                            facade.format(listAssertionGroup, sb, alwaysTrueAssertionFilter)
                            verbs.checkImmediately(sb.toString()).toBe(separator
                                + "placeholder %s: 2$separator"
                                + "$listBulletPoint ${AssertionVerb.ASSERT.getDefault()}: 1$separator"
                                + "$listBulletPoint ${AssertionVerb.EXPECT_THROWN.getDefault()}: 2")
                        }
                    }

                    context("in an ${AssertionGroup::class.simpleName} of type ${DefaultFeatureAssertionGroupType::class.simpleName}") {
                        val featureAssertions = listOf(listAssertionGroup, BasicDescriptiveAssertion(AssertionVerb.ASSERT, 20, false))
                        val featureAssertionGroup = AssertionGroup.Builder.feature.create(AssertionVerb.ASSERT, 10, featureAssertions)
                        it("indents the group ${AssertionGroup::name.name} as well as the ${AssertionGroup::assertions.name} accordingly - uses `$listBulletPoint` for each assertion and `$bulletPoint` for each element in the list group") {
                            facade.format(featureAssertionGroup, sb, alwaysTrueAssertionFilter)
                            verbs.checkImmediately(sb.toString()).toBe(separator
                                + "$arrow ${AssertionVerb.ASSERT.getDefault()}: 10$separator"
                                + "$indentArrow$bulletPoint placeholder %s: 2$separator"
                                + "$indentArrow$indent$listBulletPoint ${AssertionVerb.ASSERT.getDefault()}: 1$separator"
                                + "$indentArrow$indent$listBulletPoint ${AssertionVerb.EXPECT_THROWN.getDefault()}: 2$separator"
                                + "$indentArrow$bulletPoint ${AssertionVerb.ASSERT.getDefault()}: 20")
                        }
                        context("in another ${AssertionGroup::class.simpleName} of type ${assertionGroupType::class.simpleName}") {
                            it("indents the group ${AssertionGroup::name.name} as well as the ${AssertionGroup::assertions.name} accordingly - uses `$listBulletPoint` for each assertion and `$bulletPoint` for each element in the list group") {
                                val listAssertions = listOf(BasicDescriptiveAssertion(AssertionVerb.ASSERT, 5, false), featureAssertionGroup, BasicDescriptiveAssertion(AssertionVerb.ASSERT, 30, false))
                                val listAssertionGroup2 = AssertionGroup.Builder.withType(assertionGroupType).create(AssertionVerb.EXPECT_THROWN, 10, listAssertions)
                                facade.format(listAssertionGroup2, sb, alwaysTrueAssertionFilter)
                                verbs.checkImmediately(sb.toString()).toBe(separator
                                    + "${AssertionVerb.EXPECT_THROWN.getDefault()}: 10$separator"
                                    + "$listBulletPoint ${AssertionVerb.ASSERT.getDefault()}: 5$separator"
                                    + "$listBulletPoint $arrow ${AssertionVerb.ASSERT.getDefault()}: 10$separator"
                                    + "$listIndent$indentArrow$bulletPoint placeholder %s: 2$separator"
                                    + "$listIndent$indentArrow$indent$listBulletPoint ${AssertionVerb.ASSERT.getDefault()}: 1$separator"
                                    + "$listIndent$indentArrow$indent$listBulletPoint ${AssertionVerb.EXPECT_THROWN.getDefault()}: 2$separator"
                                    + "$listIndent$indentArrow$bulletPoint ${AssertionVerb.ASSERT.getDefault()}: 20$separator"
                                    + "$listBulletPoint ${AssertionVerb.ASSERT.getDefault()}: 30")
                            }
                        }
                    }
                    context("in another ${AssertionGroup::class.simpleName} of type ${assertionGroupClass.simpleName}") {
                        it("indents the group ${AssertionGroup::name.name} as well as the ${AssertionGroup::assertions.name} accordingly - uses `$listBulletPoint` for each assertion and `$bulletPoint` for each element in the list group") {
                            val listAssertions = listOf(BasicDescriptiveAssertion(AssertionVerb.ASSERT, 5, false), listAssertionGroup, BasicDescriptiveAssertion(AssertionVerb.ASSERT, 30, false))
                            val listAssertionGroup2 = AssertionGroup.Builder.withType(anonymousAssertionGroupType).create(AssertionVerb.EXPECT_THROWN, 10, listAssertions)
                            facade.format(listAssertionGroup2, sb, alwaysTrueAssertionFilter)
                            verbs.checkImmediately(sb.toString()).toBe(separator
                                + "${AssertionVerb.EXPECT_THROWN.getDefault()}: 10$separator"
                                + "$listBulletPoint ${AssertionVerb.ASSERT.getDefault()}: 5$separator"
                                + "$listBulletPoint placeholder %s: 2$separator"
                                + "$listIndent$listBulletPoint ${AssertionVerb.ASSERT.getDefault()}: 1$separator"
                                + "$listIndent$listBulletPoint ${AssertionVerb.EXPECT_THROWN.getDefault()}: 2$separator"
                                + "$listBulletPoint ${AssertionVerb.ASSERT.getDefault()}: 30")
                        }
                    }
                }
            }
        }
    }

})
