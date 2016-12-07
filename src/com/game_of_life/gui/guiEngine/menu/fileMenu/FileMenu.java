package com.game_of_life.gui.guiEngine.menu.fileMenu;

import com.game_of_life.networking.NetworkingEngine;
import com.game_of_life.util.Constants;
import com.game_of_life.util.Manifest;
import com.game_of_life.util.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Created by obama on 13/10/2016.
 */
public class FileMenu extends JMenu {
    private final JMenuItem save;
    private final JMenuItem load;
    private final JMenuItem newGame;
    private final NetworkingEngine engine;


    public FileMenu(NetworkingEngine engine){
        super(Constants.Menu.FILE_MENU_TITLE);
        this.engine = engine;

        this.save = new JMenuItem(Constants.Menu.SAVE_ITEM_TITLE);
        save.addActionListener(this::saveAction);

        this.load = new JMenuItem(Constants.Menu.LOAD_ITEM_TITLE);
        load.addActionListener(this::loadAction);

        this.newGame = new JMenuItem(Constants.Menu.NEW_ITEM_TITLE);
        newGame.addActionListener(this::newAction);

        add(load);
        add(save);
        add(newGame);
    }

    private void newAction(ActionEvent event) {
        engine.newGame();
    }

    private void loadAction(ActionEvent event) {
        File savingFile = Util.getFile(Manifest.getSavingDirectory(), ".xml");
        if(savingFile != null){
            engine.loadSaving(savingFile);
        }
    }

    private void saveAction(ActionEvent event) {
        String fileName = JOptionPane.showInputDialog("Enter saving name");
        engine.save(fileName);
    }
}
