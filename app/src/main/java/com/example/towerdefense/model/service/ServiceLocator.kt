package com.example.towerdefense.model.service

object ServiceLocator {
    private val services = mutableMapOf<Class<*>, Service>()

    fun addService(instance: Service) {
        services[instance.getServiceClass()] = instance
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getService(serviceClass: Class<T>): T {
        return services[serviceClass] as T
    }
}