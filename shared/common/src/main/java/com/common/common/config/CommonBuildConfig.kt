package com.common.common.config

import android.util.Log
import java.lang.reflect.Modifier

class
CommonBuildConfig(private val configClassName: String) {

    private val commonConfigMap = mutableMapOf<String, Any?>()

    init {
        Log.e("CommonBuildConfig 🧐", "ini")
        initCommonConfigMap()
    }

    fun isDebug() = commonConfigMap["DEBUG"] as Boolean


    private fun getConfigClass(): Class<*>? {
        return try {
            Log.e("CommonBuildConfig 🧐", "Class Name: $configClassName")
            Class.forName(configClassName)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            null
        }
    }


    private fun initCommonConfigMap() {
        getConfigClass()?.let { cls ->
            val declaredFields = cls.declaredFields
            if (declaredFields.isNotEmpty()) {
                declaredFields.forEach { field ->
                    Log.e("CommonBuildConfig 🧐", "name $field")
                    if (Modifier.isStatic(field.modifiers) && field.isSynthetic.not()) {
                        try {
                            val name = field.name
                            val value = field.get(null)
                            commonConfigMap[name] = value
                            Log.e("CommonBuildConfig 🧐", "name $field , value $value")
                        } catch (e: IllegalAccessException) {
                            e.printStackTrace()
                        } catch (e: NoSuchFieldException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }

    fun isProductionBuild(): Boolean {

        return try {
            val releaseConfig: Boolean = getConfigFromMap(
                "RELEASE",
                false
            )
            val prodConfig: String = getConfigFromMap(
                FLAVOR_BUILD,
                "staging"
            )
            Log.e("CommonBuildConfig 🧐", "releaseConfig $releaseConfig , prodConfig $prodConfig")
            releaseConfig || (prodConfig.contains("prod", true))
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getConfigFromMap(configFieldName: String, defaultValue: T): T {
        return commonConfigMap[configFieldName] as? T ?: defaultValue
    }

    companion object {
        const val FLAVOR_BUILD = "FLAVOR"

    }

}