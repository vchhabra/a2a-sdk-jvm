package plugins.extensions

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Custom extension for configuring the JaCoCo convention plugin.
 *
 * This allows individual projects to override the default test coverage threshold.
 *
 * @see plugins.conventions.JacocoConventionPlugin
 */
abstract class JacocoConventionExtension @Inject constructor(objects: ObjectFactory) {
    /**
     * The minimum required code coverage ratio. Defaults to 0.80 (80%).
     * Gradle's 'Property' type is used here to allow for lazy configuration.
     */
    val coverageThreshold: Property<BigDecimal> = objects.property(BigDecimal::class.java).convention(BigDecimal("0.80"))
}
