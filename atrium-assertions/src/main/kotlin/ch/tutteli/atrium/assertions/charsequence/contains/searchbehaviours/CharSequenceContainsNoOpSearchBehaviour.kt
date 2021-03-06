package ch.tutteli.atrium.assertions.charsequence.contains.searchbehaviours

import ch.tutteli.atrium.assertions.charsequence.contains.CharSequenceContains
import ch.tutteli.atrium.reporting.translating.Translatable

/**
 * Represents the default search behaviour or rather does not define a search behaviour at all.
 *
 * It furthermore implements a no operation for [decorateDescription], meaning it passes back the description which
 * is passed in (identity function).
 */
open class CharSequenceContainsNoOpSearchBehaviour : CharSequenceContains.SearchBehaviour {
    /**
     * Returns the given [description].
     * @return the given [description].
     */
    override fun decorateDescription(description: Translatable) = description
}
