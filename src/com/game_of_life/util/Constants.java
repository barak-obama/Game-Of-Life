package com.game_of_life.util;

import javax.swing.*;
import java.awt.*;

/**
 * Created by obama on 13/10/2016.
 */
public class Constants {


    public static class Menu {

        public static final String FILE_MENU_TITLE = "File";
        public static final String GAME_MENU_TITLE = "Game";

        public static final String START_ITEM_TITLE = "Start";
        public static final String START_ITEM_COMMAND = "start";

        public static final String PAUSE_ITEM_TITLE = "Pause";
        public static final String PAUSE_ITEM_COMMAND = "pause";

        public static final String SAVE_ITEM_TITLE = "Save";
        public static final String SAVE_ITEM_COMMAND = "fave";

        public static final String LOAD_ITEM_TITLE = "Load";
        public static final String LOAD_ITEM_COMMAND = "Load";

        public static final String NEW_ITEM_TITLE = "New Game";
        public static final String NEW_ITEM_COMMAND = "new_game";

        public static final String LOAD_FUNCTION_ITEM_TITLE = "Load new function";
        public static final String LOAD_FUNCTION_ITEM_COMMAND = "load_function";

        public static final String USE_FUNCTION_ITEM_TITLE = "Use saved function";
        public static final String USE_FUNCTION_ITEM_COMMAND = "use_function";

        public static final String NUMBERS_ITEM_TITLE = "Numbers";

        public static final String SAVING_ERROR_MESSAGE = "Coping file failed";


        public static final Dimension CHOOSE_FUNCTION_DIMENSION = new Dimension(200, 300);
        public static final ImageIcon EMPTY = new ImageIcon(Manifest.getImagesDirectory() + "empty.png");
        public static final ImageIcon V = new ImageIcon(Manifest.getImagesDirectory() + "v.png");

    }

}
