package dev.johnlester.obelisk.service;

import dev.johnlester.obelisk.model.User;

public interface AuthService {
    /**
     * Authenticate a user
     * @return User object if authentication successful, null otherwise
     */
    User authenticate(String username, String password);
    
    /**
     * Register a new user
     * @return true if registration successful, false otherwise
     */
    boolean register(User user);
    
    /**
     * Logout current user
     */
    void logout();
    
    /**
     * Get current logged in user
     */
    User getCurrentUser();
} 