package com.game_of_life.gui.guiEngine;

import com.game_of_life.gui.UIEngine;
import com.game_of_life.gui.guiEngine.menu.MenuBar;
import com.game_of_life.networking.NetworkingEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;


/**
 * Created by obama on 07/10/2016.
 */
public class GUIEngine extends WindowAdapter implements UIEngine{

    private JFrame mainFrame;
    private Panel mainPanel;
    private MenuBar menuBar;
    private NetworkingEngine engine;



    public GUIEngine(){
        this.mainFrame = new JFrame();
        mainFrame.setSize(500,500);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.mainPanel = new Panel();
        this.mainFrame.add(mainPanel);

        mainFrame.addWindowListener(this);
    }

    @Override
    public void setEngine(NetworkingEngine engine){
        this.engine = engine;
        this.menuBar = new MenuBar(engine);
        mainFrame.setJMenuBar(menuBar);
        mainPanel.setEngine(engine);
    }

    @Override
    public void setMatrix(int[][] matrix, Map<Integer, Color> colorMap) {
        mainPanel.update(matrix, colorMap);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        engine.killPython(0);
        //System.exit(0);
    }
}
