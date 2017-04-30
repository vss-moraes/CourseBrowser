package ca.mohawkcollege.da_silva_moraes.project;

import android.content.Context;


public class Project extends android.app.Application {

    private static Project instance;

    public Project(){
        instance = this;
    }

    public static Context getContext(){
        return instance;
    }
}