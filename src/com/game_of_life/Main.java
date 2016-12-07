package com.game_of_life;

import com.game_of_life.gui.guiEngine.GUIEngine;
import com.game_of_life.networking.NetworkingEngine;
import com.game_of_life.util.osBasedUtil.OSBasedUtil;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by obama on 07/10/2016.
 */
public class Main {

    public static void main(String[] args) {

        OSBasedUtil.getInstance().setUISettings();


        java.awt.EventQueue.invokeLater(() -> {
            try {
                start();
            } catch (IOException | InterruptedException | ParserConfigurationException | SAXException e) {
                e.printStackTrace();
            }

        });


    }


    private static void start() throws IOException, InterruptedException, ParserConfigurationException, SAXException {
        GUIEngine gui = new GUIEngine();
        new NetworkingEngine(gui).start();
    }
}
