package com.game_of_life.gui.guiEngine.menu.fileMenu;

import com.game_of_life.networking.NetworkingEngine;
import com.game_of_life.util.Constants;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by obama on 13/10/2016.
 */
public class FileMenu extends JMenu implements ActionListener{
    JMenuItem save, load, newGame;
    NetworkingEngine engine;


    public FileMenu(NetworkingEngine engine){
        super(Constants.Menu.FILE_MENU_TITLE);
        this.engine = engine;

        this.save = new JMenuItem(Constants.Menu.SAVE_ITEM_TITLE);
        save.setActionCommand(Constants.Menu.SAVE_ITEM_COMMAND);
        save.addActionListener(this);

        this.load = new JMenuItem(Constants.Menu.LOAD_ITEM_TITLE);
        load.setActionCommand(Constants.Menu.LOAD_ITEM_COMMAND);
        load.addActionListener(this);

        this.newGame = new JMenuItem(Constants.Menu.NEW_ITEM_TITLE);
        newGame.setActionCommand(Constants.Menu.NEW_ITEM_COMMAND);
        newGame.addActionListener(this);

        add(load);
        add(save);
        add(newGame);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case Constants.Menu.SAVE_ITEM_COMMAND:
                String fileName = JOptionPane.showInputDialog("Enter saving name");
                engine.save(fileName);
                break;
            case Constants.Menu.LOAD_ITEM_COMMAND:
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
                int returnVal = jFileChooser.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    engine.loadSaving(jFileChooser.getSelectedFile());
                }
                break;
            case Constants.Menu.NEW_ITEM_COMMAND:
                engine.newGame();
                break;
        }
    }
}
