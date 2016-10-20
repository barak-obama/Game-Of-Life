package com.game_of_life.gui.guiEngine.menu;

import com.game_of_life.gui.guiEngine.menu.fileMenu.FileMenu;
import com.game_of_life.gui.guiEngine.menu.gameMenu.GameMenu;
import com.game_of_life.networking.NetworkingEngine;

import javax.swing.*;

/**
 * Created by obama on 13/10/2016.
 */
public class MenuBar extends JMenuBar {


    JMenu fileMenu, gameMenu;

    public MenuBar(NetworkingEngine engine){
        super();

        fileMenu = new FileMenu(engine);
        gameMenu = new GameMenu(engine);

        add(fileMenu);
        add(gameMenu);
    }


}
