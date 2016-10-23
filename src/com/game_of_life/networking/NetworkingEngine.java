package com.game_of_life.networking;


import com.game_of_life.gui.UIEngine;
import com.game_of_life.util.Default;
import com.game_of_life.util.Manifest;
import com.game_of_life.util.Util;
import com.game_of_life.util.osBasedUtil.OSBasedUtil;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;


public class NetworkingEngine {

    private Thread inputFromPythonThread, errorFromPythonThread, outputtoThread, paintThread;

    private Process runtimeProgress;
    private PrintStream out;
    private Scanner in_scanner;
    private Scanner err_scanner;
    private Scanner key_scanner = new Scanner(System.in);


    private UIEngine uiEngine;
    private int environmentMatrixValue = 1;
    private boolean running;

    //save variables
    private Map<Integer, Color> colorMap;
    private int[][] matrix;
    private int delay;
    private String functionName;




    public NetworkingEngine(UIEngine uiEngine) {

        this.uiEngine = uiEngine;
        this.uiEngine.setEngine(this);


        this.outputtoThread = new Thread(() -> {
            while (true) {
                String s = key_scanner.nextLine();
                sendCommand(s);
            }
        });

        this.inputFromPythonThread = new Thread(() -> {
            String s;
            String start = "VALUE&&board&&";
            while (true) {
                s = in_scanner.nextLine();
                if (s.startsWith(start)) {
                    s = s.substring(start.length()).substring(2);
                    s = s.substring(0, s.length() - 2);
                    s = s.replaceAll("\\]\\[", "\n");

//                    System.out.println(s);
                    matrix = Util.parseMatrix(s);

                    if (!this.paintThread.isAlive()) {
                        this.paintThread.start();
                    }
                } else {
                    System.out.println(s);
                }
            }
        });

        this.paintThread = new Thread(() -> {
            while (true) {
                uiEngine.setMatrix(matrix, getColorMap());
                try {
                    Thread.sleep(1000 / 30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        this.errorFromPythonThread = new Thread(() -> {
            String s;

            while (true) {
                s = err_scanner.nextLine();
                System.err.println(s);
            }

        });

    }

    public void start() throws IOException, InterruptedException {

        // create the python process
        this.runtimeProgress = Runtime.getRuntime().exec(OSBasedUtil.getInstance().getPythonPath() + " " + Manifest.getBackendDirectory()) ;

        // create the output and input (the python's standard output and error) objects
        this.out = new PrintStream(this.runtimeProgress.getOutputStream(), true);
        this.in_scanner = new Scanner(this.runtimeProgress.getInputStream());
        this.err_scanner = new Scanner(this.runtimeProgress.getErrorStream());

        Thread.sleep(1000);
        if(!outputtoThread.isAlive()) {
            this.outputtoThread.start();
        }
        if(!inputFromPythonThread.isAlive()) {
            this.inputFromPythonThread.start();
        }
        if(!errorFromPythonThread.isAlive()) {
            this.errorFromPythonThread.start();
        }
    }

    public void newGame() {
        loadSaving(new File(Manifest.getDefaultSaveFile()));
    }

    public void loadSaving(File f) {
//        try {
//            start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        applySave(f);
    }


    private void applySave(File f) {
        Default save = null;
        try {
            save = new Default(f);
            sendDelay(save.getDelay());
            sendSize(save.getHeight(), save.getWidth());
            sendFunction(save.getFunctionName());
            sendMatrix(save.getMatrix());
            this.colorMap = save.getColorMap();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

    public boolean save(String name) {
        return Default.save(name, matrix, colorMap, delay, functionName);
    }

    private void sendCommand(String name, Object... params) {
        String string = name + " " + Util.join(params, " ");
        out.println(string);
    }

    public Map<Integer, Color> getColorMap() {
        return colorMap;
    }

    public void sendMatrix(int[][] matrix) {
        this.matrix = matrix;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                sendValue(i, j, matrix[i][j]);
            }
        }
    }

    public void sendValue(int i, int j, int val) {
        sendCommand("set", i, j, val);
    }

    public void startGame() {
        this.running = true;
        sendCommand("start");
    }

    public void pauseGame() {
        this.running = false;
        sendCommand("pause");
    }


    public void sendDelay(int delay) {
        this.delay = delay;
        sendCommand("delay", delay);
    }

    public void sendSize(int height, int width) {
        sendCommand("size", height, width);
    }

    public void sendFunction(String name) {
        this.functionName = name;
        sendCommand("use", name);
    }

    public int getEnvironmentMatrixValue() {
        return environmentMatrixValue;
    }

    public void setEnvironmentMatrixValue(int environmentMatrixValue) {
        this.environmentMatrixValue = environmentMatrixValue;
        if(!colorMap.keySet().contains(environmentMatrixValue)){
           addNumber(environmentMatrixValue);
        }
    }



    public boolean isRunning() {
        return running;
    }

    public void killPython(int a) {
        if (runtimeProgress != null)
            runtimeProgress.destroy();

    }

    public Color addNumber(int val) {
        // get random RGB
        Random r = new Random();
        int colorRGB = r.nextInt(0xFFFFFF);
        Collection<Integer> RGBs = new ArrayList<>();
        colorMap.forEach((i, color) -> {
            RGBs.add(color.getRGB());
        });
        while(RGBs.contains(colorRGB)){
            colorRGB = r.nextInt(0xFFFFFF);
        }
        Color color = new Color(colorRGB);
        colorMap.put(val, color);
        return color;
    }
}