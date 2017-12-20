package ch.tutteli.atrium.assertions

import ch.tutteli.atrium.reporting.translating.StringBasedTranslatable

/**
 * Contains the [IBasicAssertion.description]s of the assertion functions which are applicable to [Collection].
 */
enum class DescriptionCollectionAssertion(override val value: String) : StringBasedTranslatable {
    HAS_SIZE("hat die Grösse"),
    EMPTY("leer")
}
