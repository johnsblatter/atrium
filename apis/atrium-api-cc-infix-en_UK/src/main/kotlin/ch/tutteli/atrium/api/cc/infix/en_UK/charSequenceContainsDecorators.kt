package ch.tutteli.atrium.api.cc.infix.en_UK

import ch.tutteli.atrium.api.cc.infix.en_UK.assertions.charsequence.contains.builders.CharSequenceContainsNotCheckerBuilder
import ch.tutteli.atrium.assertions.charsequence.contains.builders.CharSequenceContainsBuilder
import ch.tutteli.atrium.assertions.charsequence.contains.searchbehaviours.CharSequenceContainsNoOpSearchBehaviour
import ch.tutteli.atrium.assertions.charsequence.contains.searchbehaviours.CharSequenceContainsNotSearchBehaviour
import ch.tutteli.atrium.assertions.charsequence.contains.searchbehaviours._containsIgnoringCase

/**
 * Defines that the decoration behaviour `ignore case` shall be applied to this sophisticated `contains` assertion.
 *
 * @param case Has to be `case`.
 *
 * @return The newly created builder.
 */
infix fun <T : CharSequence> CharSequenceContainsBuilder<T, CharSequenceContainsNoOpSearchBehaviour>.ignoring(@Suppress("UNUSED_PARAMETER") case: case)
    = _containsIgnoringCase(this)

/**
 * Defines that the decoration behaviour `ignore case` shall be applied to this sophisticated `contains not` assertion.
 *
 * @param case Has to be `case`.
 *
 * @return The newly created builder.
 */
infix fun <T : CharSequence> CharSequenceContainsNotCheckerBuilder<T, CharSequenceContainsNotSearchBehaviour>.ignoring(@Suppress("UNUSED_PARAMETER") case: case)
    = CharSequenceContainsNotCheckerBuilder(containsBuilder ignoring case)
