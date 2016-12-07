package game_of_life.util.osBasedUtil;

/**
 * Created by obama on 13/10/2016.
 */
class WindowsBasedUtil extends OSBasedUtil {

    private static WindowsBasedUtil macBasedUtil = null;

    public static WindowsBasedUtil getInstance(){
        if (macBasedUtil == null){
            macBasedUtil = new WindowsBasedUtil();
        }
        return macBasedUtil;
    }

    @Override
    public String getPythonPath() {
        return "C:\\Python27\\python";
    }

    @Override
    protected void setMenuProperties() {

    }

    private WindowsBasedUtil(){};



}
