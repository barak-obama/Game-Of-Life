package com.game_of_life.gui.guiEngine.menu.boardMenu;

import com.game_of_life.networking.NetworkingEngine;
import com.game_of_life.util.Constants;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by obama on 29/10/2016.
 */
public class BoardMenu extends JMenu implements ActionListener {
    JMenuItem set, numbers;
    NetworkingEngine engine;


    public BoardMenu(NetworkingEngine engine){
        super(Constants.Menu.BOARD_MENU_TITLE);

        this.engine = engine;

        this.set = new JMenuItem(Constants.Menu.SET_ITEM_TITLE);
        this.set.setActionCommand(Constants.Menu.SET_ITEM_COMMAND);
        this.set.addActionListener(this);

        this.numbers = new Numbers(engine, Constants.Menu.NUMBERS_ITEM_TITLE);



        add(numbers);
        add(set);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case Constants.Menu.SET_ITEM_COMMAND:
                setSize();
                break;
        }
    }

    private void setSize(){
        JTextField xField = new JTextField(5);
        JTextField yField = new JTextField(5);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Width:"));
        myPanel.add(xField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Height:"));
        myPanel.add(yField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please Enter height and width Values", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            engine.sendSize(Integer.parseInt(yField.getText()), Integer.parseInt(xField.getText()));
        }
    }
}
