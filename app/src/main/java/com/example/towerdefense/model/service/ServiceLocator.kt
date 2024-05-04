package com.example.towerdefense.model.service

object ServiceLocator {
    private val services = mutableMapOf<Class<*>, Service>()

    fun addService(instance: Service) {
        // If service already in the list, remove previous one
        if (services.containsKey(instance.getServiceClass())) {
            services.remove(instance.getServiceClass())
        }

        services[instance.getServiceClass()] = instance
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getService(serviceClass: Class<T>): Service {
        return services[serviceClass] as Service
    }
}