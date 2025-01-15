package org.example.swm.models;

import org.example.swm.views.ViewFactory;

public class ViewFactoryModel {
    private static ViewFactoryModel viewFactoryModel;
    private final ViewFactory viewFactory;

    private ViewFactoryModel() {
        this.viewFactory = new ViewFactory();
    }

    public static synchronized ViewFactoryModel getInstance() {
        if (viewFactoryModel == null) { viewFactoryModel = new ViewFactoryModel(); }
        return viewFactoryModel;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }
}

