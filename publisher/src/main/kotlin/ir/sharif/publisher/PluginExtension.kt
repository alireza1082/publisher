package ir.sharif.publisher

import org.gradle.api.provider.Property

interface PluginExtension {
    val artifact: Property<String>
}