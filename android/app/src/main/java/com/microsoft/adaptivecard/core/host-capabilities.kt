package com.microsoft.adaptivecard.core

// HostCapabilities.kt


class HostCapabilities : SerializableObject() {
    private val _capabilities: MutableMap<String, TargetVersion> = mutableMapOf()

    override fun getSchemaKey(): String {
        return "HostCapabilities"
    }

    override fun internalParse(source: Any?, context: BaseSerializationContext) {
        super.internalParse(source, context)

        if (source is Map<*, *>) {
            for ((name, jsonVersion) in source) {
                if (jsonVersion is String) {
                    if (jsonVersion == "*") {
                        addCapability(name as String, "*")
                    } else {
                        val version = Version.parse(jsonVersion, context)
                        if (version?.isValid == true) {
                            addCapability(name as String, version)
                        }
                    }
                }
            }
        }
    }

    override fun internalToJSON(target: MutableMap<String, Any>, context: BaseSerializationContext) {
        super.internalToJSON(target, context)

        for ((key, value) in _capabilities) {
            target[key] = value.toString()
        }
    }

    fun addCapability(name: String, version: TargetVersion) {
        _capabilities[name] = version
    }

    fun removeCapability(name: String) {
        _capabilities.remove(name)
    }

    fun clear() {
        _capabilities.clear()
    }

    fun hasCapability(name: String, version: TargetVersion): Boolean {
        val capability = _capabilities[name] ?: return false

        return when {
            version == "*" || capability == "*" -> true
            capability is Version && version is Version -> version <= capability
            else -> false
        }
    }

    fun areAllMet(hostCapabilities: HostCapabilities): Boolean {
        return _capabilities.all { (capabilityName, version) ->
            hostCapabilities.hasCapability(capabilityName, version)
        }
    }
}

// You might need to define these types if they're not already defined in your Kotlin project
typealias TargetVersion = Any // This can be either a Version object or a String "*"

data class Version(val major: Int, val minor: Int) : Comparable<Version> {
    val isValid: Boolean = true // You might want to implement a proper validation logic

    override fun compareTo(other: Version): Int {
        return when {
            major != other.major -> major.compareTo(other.major)
            else -> minor.compareTo(other.minor)
        }
    }

    override fun toString(): String {
        return "$major.$minor"
    }

    companion object {
        fun parse(versionString: String, context: BaseSerializationContext): Version? {
            val parts = versionString.split(".")
            return if (parts.size == 2) {
                try {
                    Version(parts[0].toInt(), parts[1].toInt())
                } catch (e: NumberFormatException) {
                    null
                }
            } else {
                null
            }
        }
    }
}

// You'll need to implement or import these classes/interfaces
abstract class SerializableObject {
    abstract fun getSchemaKey(): String
    open fun internalParse(source: Any?, context: BaseSerializationContext) {}
    open fun internalToJSON(target: MutableMap<String, Any>, context: BaseSerializationContext) {}
}

abstract class BaseSerializationContext