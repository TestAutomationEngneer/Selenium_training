package configuration;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    private static Map<String, Object> globals;
    private static Map<String, Map<String, Object>> environments;

    public Configuration(Map<String, Object> globals, Map<String, Map<String, Object>> environments) {
        this.globals = globals;
        if (environments == null) {
            this.environments = new HashMap<>();
        } else {
            this.environments = environments;
        }
    }

    public Map<String, Object> getEnvironment(String environmentName) {
        if (environmentName == null || environmentName.isEmpty()) {
            throw new IllegalArgumentException("Environment name cannot be null or empty");
        }

        Map<String, Object> environment = environments.get(environmentName);
        if (environment == null) {
            throw new IllegalArgumentException("No environment found with name: " + environmentName);
        }
        return environment;
    }

    public static Object getProperty(String property) {
        if (property == null || property.isEmpty()) {
            throw new IllegalArgumentException("Property name cannot be null or empty");
        }

        String envProperty = System.getProperty(property);
        if (envProperty != null) {
            return envProperty;
        }

        if (environments == null) {
            throw new IllegalStateException("Environments map is not initialized");
        }

        Map<String, Object> currentEnvironment = environments.get(globals.get("environment"));
        if (currentEnvironment == null) {
            throw new IllegalStateException("Current environment is not initialized");
        }

        Object currentEnvironmentProperty = currentEnvironment.get(property);
        if (currentEnvironmentProperty != null) {
            return currentEnvironmentProperty;
        }

        Object globalProperty = globals.get(property);
        if (globalProperty != null) {
            return globalProperty;
        }

        throw new IllegalArgumentException("No property found with name: " + property);
    }

    public boolean isSpecified(String property) {
        if (property == null || property.isEmpty()) {
            throw new IllegalArgumentException("Property name cannot be null or empty");
        }

        return System.getProperty(property) != null || globals.containsKey(property);
    }
}
