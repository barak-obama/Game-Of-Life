package game_of_life.util;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

/**
 * Created by obama on 07/10/2016.
 */
public class Default {

    @Test
    private void loadTest() throws ParserConfigurationException, IOException, SAXException {
        Assert.assertNotEquals(1,1);
    }

    public static void save(String name, int[][] matrix, Map<Integer, Color> colorMap, Integer delay, String functionName){

    }

    private static void saveColorMap(Document doc, Element rootElement, Map<Integer, Color> colorMap) {
    }

    private static void saveMatrix(Document doc, Element rootElement, int[][] matrix) {
    }


}
