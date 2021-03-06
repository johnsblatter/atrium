package ch.tutteli.atrium.assertions.iterable.contains.searchbehaviours

import ch.tutteli.atrium.assertions.DescriptionIterableAssertion
import ch.tutteli.atrium.reporting.translating.Translatable

/**
 * Represents the search behaviour that expected entries might appear in any order within the [Iterable].
 */
open class IterableContainsNotSearchBehaviour : IterableContainsInAnyOrderSearchBehaviour() {
    override fun decorateDescription(description: Translatable): Translatable
        = DescriptionIterableAssertion.CONTAINS_NOT
}
