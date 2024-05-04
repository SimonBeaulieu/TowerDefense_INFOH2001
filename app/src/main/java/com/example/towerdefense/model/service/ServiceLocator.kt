package com.example.towerdefense.model.service

object ServiceLocator {
    private val services = mutableMapOf<Class<*>, Service>()

    fun addService(instance: Service) {
        services[instance.getServiceClass()] = instance
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getService(serviceClass: Class<T>): Service {
        return services[serviceClass] as Service
    }
}