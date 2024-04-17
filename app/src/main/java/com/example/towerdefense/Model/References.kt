package com.example.towerdefense.model

object References {
    private val references = mutableMapOf<Class<*>, Any>()

    fun <T : Any> addRef(instance: T, type: Class<T>) {
        references[type] = instance
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getRef(interfaceClass: Class<T>): T {
        return references[interfaceClass] as T
    }
}