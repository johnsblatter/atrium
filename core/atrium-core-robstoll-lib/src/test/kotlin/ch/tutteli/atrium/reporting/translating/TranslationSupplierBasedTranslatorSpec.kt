package ch.tutteli.atrium.reporting.translating

import ch.tutteli.atrium.AssertionVerbFactory
import ch.tutteli.atrium.CoreFactory
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.include

object TranslationSupplierBasedTranslatorSpec : Spek({
    include(AtriumsTranslationSupplierBasedTranslatorSpec)
    include(AtriumsTranslatorErrorCaseSpec)
}) {
    object AtriumsTranslationSupplierBasedTranslatorSpec : ch.tutteli.atrium.spec.reporting.translating.TranslationSupplierBasedTranslatorSpec(
        AssertionVerbFactory, ::TranslationSupplierBasedTranslator, "[Atrium's TranslatorSpec] ")

    object AtriumsTranslatorErrorCaseSpec : ch.tutteli.atrium.spec.reporting.translating.TranslatorErrorCaseSpec(
        AssertionVerbFactory,
        { p, f -> TranslationSupplierBasedTranslator(CoreFactory.newPropertiesBasedTranslationSupplier(), CoreFactory.newLocaleOrderDecider(), p, f) },
        "[Atrium's TranslatorErrorCaseSpec] ")
}
