package ch.tutteli.atrium.assertions.charsequence.contains.searchers

import ch.tutteli.atrium.assertions.charsequence.contains.CharSequenceContains.Searcher
import ch.tutteli.atrium.assertions.charsequence.contains.searchbehaviours.CharSequenceContainsNoOpSearchBehaviour

/**
 * Represents a [Searcher] which uses [CharSequence.indexOf] to find expected objects.
 */
class CharSequenceContainsIndexSearcher : Searcher<CharSequenceContainsNoOpSearchBehaviour> {
    override fun search(searchIn: CharSequence, searchFor: Any): Int {
        val expected = searchFor.toString()
        var index = searchIn.indexOf(expected)
        var counter = 0
        while (index >= 0) {
            index = searchIn.indexOf(expected, index + 1)
            ++counter
        }
        return counter
    }
}
