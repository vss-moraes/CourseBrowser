package ca.mohawkcollege.da_silva_moraes.project;

import android.content.Context;

/**
 * Created by vsant on 11-Apr-17.
 */

public class Project extends android.app.Application {

    private static Project instance;

    public Project(){
        instance = this;
    }

    public static Context getContext(){
        return instance;
    }
}