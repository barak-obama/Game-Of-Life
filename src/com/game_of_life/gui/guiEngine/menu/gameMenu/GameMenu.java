package com.game_of_life.gui.guiEngine.menu.gameMenu;

import com.game_of_life.networking.NetworkingEngine;
import com.game_of_life.util.Constants;
import com.game_of_life.util.Manifest;
import com.game_of_life.util.Util;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;



/**
 * Created by obama on 13/10/2016.
 */
public class GameMenu extends JMenu implements ActionListener{
    JMenuItem start, pause, load_function, use_function;
    NetworkingEngine engine;


    public GameMenu(NetworkingEngine engine){
        super(Constants.Menu.GAME_MENU_TITLE);
        this.engine = engine;

        this.start = new JMenuItem(Constants.Menu.START_ITEM_TITLE);
        start.setActionCommand(Constants.Menu.START_ITEM_COMMAND);
        start.addActionListener(this);

        this.pause = new JMenuItem(Constants.Menu.PAUSE_ITEM_TITLE);
        pause.setActionCommand(Constants.Menu.PAUSE_ITEM_COMMAND);
        pause.addActionListener(this);

        this.load_function = new JMenuItem(Constants.Menu.LOAD_FUNCTION_ITEM_TITLE);
        load_function.setActionCommand(Constants.Menu.LOAD_FUNCTION_ITEM_COMMAND);
        load_function.addActionListener(this);

        this.use_function = new JMenuItem(Constants.Menu.USE_FUNCTION_ITEM_TITLE);
        use_function.setActionCommand(Constants.Menu.USE_FUNCTION_ITEM_COMMAND);
        use_function.addActionListener(this);


        add(start);
        add(pause);
        add(load_function);
        add(use_function);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case Constants.Menu.START_ITEM_COMMAND:
                engine.startGame();
                break;
            case Constants.Menu.PAUSE_ITEM_COMMAND:
                engine.pauseGame();
                break;
            case Constants.Menu.LOAD_FUNCTION_ITEM_COMMAND:
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setFileFilter(new FileNameExtensionFilter("py files", "py"));
                int returnVal = jFileChooser.showOpenDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION){
                    File function_file = jFileChooser.getSelectedFile();
                    File new_function_file = new File(Manifest.getFunctionDirectory() + function_file.getName());

                    try {
                        Util.copyFile(function_file, new_function_file);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, Constants.Menu.SAVING_ERROR_MESSAGE);
                    }
                }
                break;
            case Constants.Menu.USE_FUNCTION_ITEM_COMMAND:
                String extension = ".py";
                File functionDirectory = new File(Manifest.getFunctionDirectory());
                File[] functions = functionDirectory.listFiles((file, name) -> name.endsWith(extension));
                System.out.println(functions.length);
                String[] functionsName = new String[functions.length];
                for (int i = 0; i < functions.length; i++) {
                    String name = functions[i].getName();
                    functionsName[i] = name.substring(0, name.length() - extension.length());
                }
                ChooseFunction chooseFunction = new ChooseFunction(functionsName, engine);


        }
    }
}
