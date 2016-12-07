package game_of_life.util.osBasedUtil;

/**
 * Created by obama on 13/10/2016.
 */
class MacBasedUtil extends OSBasedUtil {

    private static MacBasedUtil macBasedUtil = null;

    public static MacBasedUtil getInstance(){
        if (macBasedUtil == null){
            macBasedUtil = new MacBasedUtil();
        }
        return macBasedUtil;
    }

    @Override
    public String getPythonPath() {
        return "python";
    }

    private MacBasedUtil(){};


    @Override
    protected void setMenuProperties() {
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Test");
    }
}
