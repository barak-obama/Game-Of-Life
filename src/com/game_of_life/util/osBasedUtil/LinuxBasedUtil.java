package com.game_of_life.util.osBasedUtil;

/**
 * Created by obama on 13/10/2016.
 */
public class LinuxBasedUtil extends OSBasedUtil{

    private static LinuxBasedUtil macBasedUtil = null;

    public static LinuxBasedUtil getInstance(){
        if (macBasedUtil == null){
            macBasedUtil = new LinuxBasedUtil();
        }
        return macBasedUtil;
    }

    @Override
    public String getPythonPath() {
        return "python";
    }

    private LinuxBasedUtil(){};

    @Override
    protected void setMenuProperties() {

    }
}
