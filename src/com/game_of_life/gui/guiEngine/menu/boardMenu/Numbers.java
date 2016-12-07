package com.game_of_life.gui.guiEngine.menu.boardMenu;

import com.game_of_life.networking.NetworkingEngine;
import com.game_of_life.util.Constants;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by obama on 24/10/2016.
 */
public class Numbers extends JMenu implements MenuListener, ActionListener {

    private NetworkingEngine networkingEngine;

    private List<JMenuItem> numbersItem;
    private JMenuItem selected;

    public Numbers(NetworkingEngine networkingEngine, String title) {
        super(title);
        this.networkingEngine = networkingEngine;
        this.numbersItem = new ArrayList<>();
        addMenuListener(this);
    }


    @Override
    public void menuSelected(MenuEvent e) {


        numbersItem.forEach((item) -> {
            remove(item);
        });
        numbersItem = new ArrayList<>();
        Map<Integer, Color> colorMap = networkingEngine.getColorMap();
        colorMap.forEach((number, color) -> {
            JMenuItem menuItem = new JMenuItem(Integer.toString(number));
            if (number == networkingEngine.getEnvironmentMatrixValue()) {
                Numbers.this.selected = menuItem;
                Numbers.this.selected.setIcon(Constants.Menu.V);
            } else {
                menuItem.setIcon(Constants.Menu.EMPTY);
            }
            menuItem.setActionCommand(Integer.toString(number));
            menuItem.addActionListener(Numbers.this);
            numbersItem.add(menuItem);
            Numbers.this.add(menuItem);
        });

        System.console();

    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int number = Integer.parseInt(e.getActionCommand());
        selected.setIcon(Constants.Menu.EMPTY);
        selected = (JMenuItem) e.getSource();
        selected.setIcon(Constants.Menu.V);
        networkingEngine.setEnvironmentMatrixValue(number);

    }
}

