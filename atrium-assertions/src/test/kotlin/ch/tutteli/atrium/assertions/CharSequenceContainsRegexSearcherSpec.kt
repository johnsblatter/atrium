package ch.tutteli.atrium.assertions

import ch.tutteli.atrium.api.cc.en_UK.contains
import ch.tutteli.atrium.api.cc.en_UK.exactly
import ch.tutteli.atrium.api.cc.en_UK.regex
import ch.tutteli.atrium.assert
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.context

object CharSequenceContainsRegexSearcherSpec : Spek({

    context("text 'aaaa'") {
        test("search for 'aa' finds 3 hits since we want non disjoint matches") {
            assert("aaaa").contains.exactly(3).regex("aa")
        }
        test("search for 'aa?' finds 4 hits since we want non disjoint matches") {
            assert("aaaa").contains.exactly(4).regex("aa?")
        }
    }

})
