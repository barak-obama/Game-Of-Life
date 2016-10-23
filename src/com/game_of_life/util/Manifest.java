package com.game_of_life.util;

/**
 * Created by obama on 15/10/2016.
 */
public class Manifest {


    public static String getSavingDirectory(){
        return getRootDirectory() + "savings/";
    }

    public static String getFunctionDirectory() {
        return getBackendRootDirectory() + "step_functions/";
    }

    public static String getDefaultSaveFile(){
        return getSavingDirectory() + "default.xml";
    }

    public static String getBackendDirectory(){
        return getBackendRootDirectory() + "server.py";
    }

    public static String getBackendRootDirectory(){
        return getRootDirectory() + "backend/";
    }

    public static String getRootDirectory() {
        return "./";
    }

    public static String getImagesDirectory() {
        return getRootDirectory() + "images/";
    }
}
