package com.game_of_life.gui;

import com.game_of_life.networking.NetworkingEngine;

import java.awt.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by obama on 07/10/2016.
 */
public interface UIEngine {



    void setMatrix(int[][] matrix, Hashtable<Integer, Color> colorMap);

    void setEngine(NetworkingEngine engine);

}
