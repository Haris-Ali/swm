package org.example.swm.models;

import org.example.swm.views.ViewFactory;

/**
 * The View factory model singleton class.
 */
public class ViewFactoryModel {
    private static ViewFactoryModel viewFactoryModel;
    private final ViewFactory viewFactory;

    private ViewFactoryModel() {
        this.viewFactory = new ViewFactory();
    }

    /**
     * Gets the view factory model singleton instance.
     *
     * @return the instance
     */
    public static synchronized ViewFactoryModel getInstance() {
        if (viewFactoryModel == null) { viewFactoryModel = new ViewFactoryModel(); }
        return viewFactoryModel;
    }

    /**
     * Gets view factory.
     *
     * @return the view factory
     */
    public ViewFactory getViewFactory() {
        return viewFactory;
    }
}

