package ch.tutteli.atrium.reporting


import ch.tutteli.atrium.AtriumFactory
import ch.tutteli.atrium.assertions.IBulletPointIdentifier
import ch.tutteli.atrium.reporting.translating.*
import java.util.*

/**
 * A builder to create an [Reporter] consisting of several components.
 */
class ReporterBuilder(private val assertionFormatterFacade: AssertionFormatterFacade) {

    /**
     * Uses [AtriumFactory.newOnlyFailureReporter] as [Reporter].
     */
    fun buildOnlyFailureReporter(): Reporter
        = AtriumFactory.newOnlyFailureReporter(assertionFormatterFacade)

    /**
     * Uses the given [factory] to build a custom [Reporter].
     */
    fun buildCustomReporter(factory: (AssertionFormatterFacade) -> Reporter): Reporter
        = factory(assertionFormatterFacade)

    /**
     * Provides options to create an [ITranslator] or [ITranslationSupplier].
     */
    companion object {

        /**
         * Uses [UsingDefaultTranslator] as [ITranslator] where the given [primaryLocale] is used to format arguments
         * of [ITranslatableWithArgs].
         *
         * [UsingDefaultTranslator] does not require an [ITranslationSupplier] nor an [ILocaleOrderDecider] and thus
         * the options to specify implementations of them are skipped.
         *
         * Notice that [UsingDefaultTranslator] does not translate but uses what [ITranslatable.getDefault] returns.
         * Also notice, that if you omit the [primaryLocale] then [Locale.getDefault] is used.
         *
         * @param primaryLocale The [Locale] used to format arguments of [ITranslatableWithArgs].
         */
        fun withoutTranslations(primaryLocale: Locale = Locale.getDefault())
            = ObjectFormatterOptions(UsingDefaultTranslator(primaryLocale))

        /**
         * Uses the given [translator] as [ITranslator] skipping the options for [ITranslationSupplier] and
         * [ILocaleOrderDecider] assuming the given [translator] is implemented differently -- use
         * [withDefaultTranslationSupplier] or [withTranslationSupplier] in case the given [translator] requires
         * an [ITranslationSupplier] or an [ILocaleOrderDecider].
         */
        fun withTranslator(translator: ITranslator)
            = ObjectFormatterOptions(translator)

        /**
         * Uses [AtriumFactory.newPropertiesBasedTranslationSupplier] as [ITranslationSupplier].
         */
        fun withDefaultTranslationSupplier()
            = LocaleOrderDeciderOptions(AtriumFactory.newPropertiesBasedTranslationSupplier())

        /**
         * Uses the given [translationSupplier] as [ITranslationSupplier].
         */
        fun withTranslationSupplier(translationSupplier: ITranslationSupplier)
            = LocaleOrderDeciderOptions(translationSupplier)

        /**
         * Deprecated do not use it any longer and replace it with suggestion instead.
         */
        @Deprecated("will be removed in 0.6.0", ReplaceWith("ReporterBuilder\n" +
            "    .withoutTranslations()\n" +
            "    .withDetailedObjectFormatter()\n" +
            "    .withDefaultAssertionFormatterController()\n" +
            "    .withDefaultAssertionFormatterFacade()"))
        fun withDetailedObjectFormatter()
            = withoutTranslations()
            .withDetailedObjectFormatter()
            .withDefaultAssertionFormatterController()
            .withDefaultAssertionFormatterFacade()
    }

    class LocaleOrderDeciderOptions(private val translationSupplier: ITranslationSupplier) {

        /**
         * Uses [AtriumFactory.newLocaleOrderDecider] as [ILocaleOrderDecider].
         */
        fun withDefaultLocaleOrderDecider()
            = TranslatorOptions(translationSupplier, AtriumFactory.newLocaleOrderDecider())

        /**
         * Uses [localeOrderDecider] as [ILocaleOrderDecider].
         */
        fun withLocaleOrderDecider(localeOrderDecider: ILocaleOrderDecider)
            = TranslatorOptions(translationSupplier, localeOrderDecider)
    }

    class TranslatorOptions(private val translationSupplier: ITranslationSupplier, private val localeOrderDecider: ILocaleOrderDecider) {

        /**
         * Uses [AtriumFactory.newTranslator] as [ITranslator] where the specified [translationSupplier] is used to
         * retrieve translations, the specified [localeOrderDecider] to determine candidate [Locale]s and
         * [primaryLocale] is used as primary [Locale] and the optional [fallbackLocales] as fallback [Locale]s.
         *
         * @param primaryLocale The [Locale] for which the [ITranslator] will first search translations --
         *        it will also be used to format arguments of [ITranslatableWithArgs].
         * @param fallbackLocales One [Locale] after another (in the given order) will be considered as primary Locale
         *        in case no translation was found the previous primary Locale.
         */
        fun withDefaultTranslator(primaryLocale: Locale, vararg fallbackLocales: Locale)
            = ObjectFormatterOptions(AtriumFactory.newTranslator(translationSupplier, localeOrderDecider, primaryLocale, *fallbackLocales))

        /**
         * Uses the given [factory] to build a [ITranslator].
         */
        fun withTranslator(factory: (ITranslationSupplier, ILocaleOrderDecider) -> ITranslator)
            = ObjectFormatterOptions(factory(translationSupplier, localeOrderDecider))
    }

    /**
     * Provides options to create an [ObjectFormatter].
     */
    class ObjectFormatterOptions(private val translator: ITranslator) {
        /**
         * Uses [AtriumFactory.newDetailedObjectFormatter] as [ObjectFormatter].
         */
        fun withDetailedObjectFormatter()
            = AssertionFormatterControllerOptions(AtriumFactory.newDetailedObjectFormatter(translator), translator)

        /**
         * Uses the given [factory] to build a custom [ObjectFormatter].
         */
        fun withObjectFormatter(factory: (ITranslator) -> ObjectFormatter)
            = AssertionFormatterControllerOptions(factory(translator), translator)
    }

    /**
     * Provides options to create an [AssertionFormatterController].
     */
    class AssertionFormatterControllerOptions(private val objectFormatter: ObjectFormatter, private val translator: ITranslator) {
        /**
         * Uses [AtriumFactory.newAssertionFormatterController] as [AssertionFormatterController].
         */
        fun withDefaultAssertionFormatterController()
            = AssertionFormatterFacadeOptions(AtriumFactory.newAssertionFormatterController(), objectFormatter, translator)

        /**
         * Uses the given [assertionFormatterController] a custom [AssertionFormatterController].
         */
        fun withAssertionFormatterController(assertionFormatterController: AssertionFormatterController)
            = AssertionFormatterFacadeOptions(assertionFormatterController, objectFormatter, translator)
    }

    /**
     * Provides options to create an [AssertionFormatterFacade].
     */
    class AssertionFormatterFacadeOptions(private val assertionFormatterController: AssertionFormatterController, private val objectFormatter: ObjectFormatter, private val translator: ITranslator) {
        /**
         * Uses [AtriumFactory.newAssertionFormatterFacade] as [AssertionFormatterFacade].
         */
        fun withDefaultAssertionFormatterFacade()
            = AssertionFormatterOptions(AtriumFactory.newAssertionFormatterFacade(assertionFormatterController), objectFormatter, translator)

        /**
         * Uses the given [factory] to build a custom [AssertionFormatterFacade].
         */
        fun withAssertionFormatterFacade(factory: (AssertionFormatterController) -> AssertionFormatterFacade)
            = AssertionFormatterOptions(factory(assertionFormatterController), objectFormatter, translator)
    }

    /**
     * Provides options to register [AssertionFormatter]s to the chosen [AssertionFormatterFacade].
     *
     * @see AssertionFormatterFacadeOptions
     */
    class AssertionFormatterOptions(private val assertionFormatterFacade: AssertionFormatterFacade, private val objectFormatter: ObjectFormatter, private val translator: ITranslator) {

        /**
         * Uses [AtriumFactory.registerSameLineTextAssertionFormatterCapabilities] to register [AssertionFormatter] to
         * the [assertionFormatterFacade] where the given [bulletPoints] can be used to customise the predefined bullet
         * points.
         *
         * Have a look at the sub types of [IBulletPointIdentifier] to get a feel for what and how you can customise
         * bullet points.
         */
        fun withSameLineTextAssertionFormatter(vararg bulletPoints: Pair<Class<out IBulletPointIdentifier>, String>): ReporterBuilder {
            AtriumFactory.registerSameLineTextAssertionFormatterCapabilities(
                bulletPoints.toMap(), assertionFormatterFacade, objectFormatter, translator)
            return ReporterBuilder(assertionFormatterFacade)
        }

        /**
         * Uses the given [assertionFormatterFactory] to create and register an [AssertionFormatter] to the
         * [assertionFormatterFacade].
         */
        fun withAssertionFormatter(assertionFormatterFactory: (AssertionFormatterController) -> AssertionFormatter): ReporterBuilder {
            assertionFormatterFacade.register(assertionFormatterFactory)
            return ReporterBuilder(assertionFormatterFacade)
        }
    }
}
