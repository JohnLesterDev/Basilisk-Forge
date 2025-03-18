package dev.johnlester.obelisk.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Properties;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResourceManager {
    // Singleton instance
    private static ResourceManager instance;
    
    // Resource caches
    private final ResourceCache<String> stringCache;
    private final ResourceCache<byte[]> binaryCache;
    
    private ResourceManager() {
        this.stringCache = new ResourceCache<>();
        this.binaryCache = new ResourceCache<>();
    }
    
    public static ResourceManager getInstance() {
        if (instance == null) {
            instance = new ResourceManager();
        }
        return instance;
    }
    
    // Load text resources
    public String loadText(String path) throws IOException {
        return stringCache.getOrLoad(path, () -> {
            try (InputStream is = getResourceStream(path)) {
                return new String(is.readAllBytes(), StandardCharsets.UTF_8);
            }
        });
    }
    
    // Load binary resources (images, etc.)
    public byte[] loadBinary(String path) throws IOException {
        return binaryCache.getOrLoad(path, () -> {
            try (InputStream is = getResourceStream(path)) {
                return is.readAllBytes();
            }
        });
    }
    
    // Load properties file
    public Properties loadProperties(String path) throws IOException {
        Properties props = new Properties();
        try (InputStream is = getResourceStream(path)) {
            props.load(is);
        }
        return props;
    }
    
    // Get resource URL
    public Optional<URL> getResourceUrl(String path) {
        return Optional.ofNullable(
            getClass().getClassLoader().getResource(path)
        );
    }
    
    // Get resource stream
    private InputStream getResourceStream(String path) {
        return getClass().getClassLoader()
            .getResourceAsStream(path);
    }
}

// ResourceCache.java - Generic caching for resources
class ResourceCache<T> {
    private final Map<String, T> cache = new ConcurrentHashMap<>();
    
    public T getOrLoad(String key, ResourceLoader<T> loader) throws IOException {
        return cache.computeIfAbsent(key, k -> {
            try {
                return loader.load();
            } catch (IOException e) {
                throw new RuntimeException("Failed to load resource: " + key, e);
            }
        });
    }
    
    @FunctionalInterface
    interface ResourceLoader<T> {
        T load() throws IOException;
    }
}
