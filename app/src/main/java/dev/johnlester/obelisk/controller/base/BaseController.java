package dev.johnlester.obelisk.controller.base;

import dev.johnlester.obelisk.view.base.BaseView;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseController {
    protected final Map<String, BaseView> views;
    
    protected BaseController() {
        this.views = new HashMap<>();
    }
    
    /**
     * Register a view with this controller
     */
    protected void registerView(BaseView view) {
        views.put(view.getViewId(), view);
    }
    
    /**
     * Get a view by its ID
     */
    protected BaseView getView(String viewId) {
        return views.get(viewId);
    }
    
    /**
     * Initialize all registered views
     */
    protected void initializeViews() {
        views.values().forEach(BaseView::initialize);
    }
    
    /**
     * Clean up all views
     */
    protected void disposeViews() {
        views.values().forEach(BaseView::dispose);
    }
} 