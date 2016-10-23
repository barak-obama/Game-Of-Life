package com.game_of_life.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by obama on 07/10/2016.
 */
public class Default {

    private Map<Integer, Color> colorMap;
    private int height, width;
    private int delay;
    private String functionName;
    private int[][] matrix;

    public Default(File save) throws IOException, SAXException, ParserConfigurationException {
        colorMap = new Hashtable<>();
        load(save);
    }

    private void load(File save) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(save);
        Element root = doc.getDocumentElement();
        height = Integer.parseInt(root.getAttribute("height"));
        width = Integer.parseInt(root.getAttribute("width"));
        delay = Integer.parseInt(root.getAttribute("delay"));
        functionName = root.getAttribute("function");

        Element colors = (Element) root.getElementsByTagName("colors").item(0);
        NodeList colorsNodeList = colors.getElementsByTagName("color");
        for (int i = 0; i < colorsNodeList.getLength(); i++) {
            Element color = (Element) colorsNodeList.item(i);
            int num = Integer.parseInt(color.getAttribute("num"));
            int rgb = Integer.parseInt(color.getAttribute("rgb"));
            colorMap.put(num, new Color(rgb));
        }


        matrix = new int[height][width];
        Element board = (Element) root.getElementsByTagName("board").item(0);
        NodeList cells = board.getElementsByTagName("cell");
        for (int i = 0; i < cells.getLength(); i++) {
            Element cell = (Element) cells.item(i);
            int x = Integer.parseInt(cell.getAttribute("x"));
            int y = Integer.parseInt(cell.getAttribute("y"));
            int val = Integer.parseInt(cell.getAttribute("val"));
            matrix[y][x] = val;
        }

    }

    public static boolean save(String name, int[][] matrix, Map<Integer, Color> colorMap, Integer delay, String functionName){
        DocumentBuilderFactory dbFactory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder =
                null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return false;
        }
        Document doc = dBuilder.newDocument();

        // root element
        Element rootElement = doc.createElement("game_of_life");

        if(matrix != null){
            saveMatrix(doc,rootElement, matrix);
        }
        if(colorMap != null){
            saveColorMap(doc,rootElement, colorMap);
        }

        if(functionName != null){
            rootElement.setAttribute("function", functionName);
        }

        if(delay != null){
            rootElement.setAttribute("delay", delay.toString());
        }

        doc.appendChild(rootElement);

        TransformerFactory transformerFactory =
                TransformerFactory.newInstance();
        Transformer transformer =
                null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
            return false;
        }
        DOMSource source = new DOMSource(doc);
        File saveTo = new File(Manifest.getSavingDirectory() + name + ".xml");
        if(!saveTo.exists()){
            try {
                saveTo.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        StreamResult result =
                new StreamResult(saveTo);
        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static void saveColorMap(Document doc, Element rootElement, Map<Integer, Color> colorMap) {
        Element colors = doc.createElement("colors");

        colorMap.forEach((n, c)->{
            Element color = doc.createElement("color");
            color.setAttribute("num", n.toString());
            color.setAttribute("rgb", ((Integer) c.getRGB()).toString());

            colors.appendChild(color);
        });

        rootElement.appendChild(colors);
    }

    private static void saveMatrix(Document doc, Element rootElement, int[][] matrix) {
        Element board = doc.createElement("board");

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                Element cell = doc.createElement("cell");
                cell.setAttribute("x", ((Integer) j ).toString());
                cell.setAttribute("y", ((Integer) i ).toString());
                cell.setAttribute("val", ((Integer) matrix[i][j] ).toString());
                board.appendChild(cell);
            }
        }

        rootElement.setAttribute("height", ((Integer) matrix.length ).toString());
        rootElement.setAttribute("width", ((Integer) matrix[0].length ).toString());

        rootElement.appendChild(board);
    }


    public Map<Integer,Color> getColorMap() {
        return colorMap;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getDelay() {
        return delay;
    }

    public String getFunctionName(){
        return functionName;
    }

    public int[][] getMatrix() {
        return matrix;
    }
}
