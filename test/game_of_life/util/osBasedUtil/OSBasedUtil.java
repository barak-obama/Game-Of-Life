package game_of_life.util.osBasedUtil;

import javax.swing.*;

/**
 * Created by obama on 13/10/2016.
 */
public abstract class OSBasedUtil {

    private static OSBasedUtil osBasedUtil = null;

    public static OSBasedUtil getInstance(){
        if(osBasedUtil == null){
            String OS = System.getProperty("os.name").toLowerCase();

            if (isWindows(OS)) {
                osBasedUtil = WindowsBasedUtil.getInstance();
            } else if (isMac(OS)) {
                osBasedUtil = MacBasedUtil.getInstance();
            } else if (isUnix(OS)) {
                osBasedUtil = WindowsBasedUtil.getInstance();
            } else {
                throw new IllegalStateException("The Operation System \'" + OS + "\' is not supported!");
            }
        }
        return osBasedUtil;
    }


    private static boolean isWindows(String OS) {

        return (OS.indexOf("win") >= 0);

    }

    private static boolean isMac(String OS) {

        return (OS.indexOf("mac") >= 0);

    }

    private static boolean isUnix(String OS) {

        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );

    }



    public void setUISettings(){
        setLookAndFeel();
        
        setMenuProperties();
    }

    public abstract String getPythonPath();

    protected abstract void setMenuProperties();

    private void setLookAndFeel(){
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

}

