package com.game_of_life.gui.guiEngine.menu.gameMenu;

import com.game_of_life.networking.NetworkingEngine;
import com.game_of_life.util.Constants;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created by obama on 19/10/2016.
 */
public class ChooseFunction extends JFrame implements ListSelectionListener {

    private JList<String> functionsJList;
    private NetworkingEngine engine;

    public ChooseFunction(String[] functions, NetworkingEngine engine) {
        this.functionsJList = new JList(functions);
        this.engine = engine;

        setSize(Constants.Menu.CHOOSE_FUNCTION_DIMENSION);

        add(functionsJList);

        functionsJList.addListSelectionListener(this);

        setVisible(true);
    }


    @Override
    public void valueChanged(ListSelectionEvent e) {
        engine.sendFunction(functionsJList.getSelectedValue());
        dispose();
    }
}
