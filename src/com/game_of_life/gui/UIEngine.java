package com.game_of_life.gui;

import com.game_of_life.networking.NetworkingEngine;

import java.awt.*;
import java.util.Map;

/**
 * Created by obama on 07/10/2016.
 */
public interface UIEngine {



    void setMatrix(int[][] matrix, Map<Integer, Color> colorMap);

    void setEngine(NetworkingEngine engine);



}
