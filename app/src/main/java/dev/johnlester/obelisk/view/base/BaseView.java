package dev.johnlester.obelisk.view.base;

import javax.swing.JPanel;

public interface BaseView {
    /**
     * Get the main panel of this view
     */
    JPanel getPanel();
    
    /**
     * Initialize the view components
     */
    void initialize();
    
    /**
     * Clean up resources when view is disposed
     */
    void dispose();
    
    /**
     * Get the unique identifier for this view
     */
    String getViewId();
} 