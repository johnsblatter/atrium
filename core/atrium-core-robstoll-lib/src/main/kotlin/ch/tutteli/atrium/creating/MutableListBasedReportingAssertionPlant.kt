package ch.tutteli.atrium.creating

import ch.tutteli.atrium.assertions.Assertion

abstract class MutableListBasedReportingAssertionPlant<out T : Any?, out A : BaseAssertionPlant<T, A>>(
    override val commonFields: AssertionPlantWithCommonFields.CommonFields<T>
) : MutableListBasedAssertionPlant<T, A>(), BaseReportingAssertionPlant<T, A> {

    final override fun addAssertion(assertion: Assertion): A {
        super.addAssertion(assertion)
        checkAssertions()
        return self
    }

    private fun checkAssertions(): A {
        try {
            commonFields.check(getAssertions())
        } finally {
            clearAssertions()
        }
        return self
    }
}
