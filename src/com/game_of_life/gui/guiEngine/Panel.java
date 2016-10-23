package com.game_of_life.gui.guiEngine;

import com.game_of_life.networking.NetworkingEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by obama on 07/10/2016.
 */
public class Panel extends JPanel implements MouseListener, MouseMotionListener {

    private int height, width, gap = 1;

    private CopyOnWriteArrayList<Point> overColors = new CopyOnWriteArrayList<>();

    private NetworkingEngine networkingEngine;

    private CopyOnWriteArrayList<PaintSlot> paintSlots = new CopyOnWriteArrayList<>();


    @Override
    protected void paintComponent(Graphics g) {
        fillBoard(g);
    }

    private void fillBoard(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        for (PaintSlot paintSlot : paintSlots) {
            fillCell(g2d, paintSlot);
        }


    }

    private void fillCell(Graphics2D g, PaintSlot paintSlot) {
        Color default_color = g.getColor();
        g.setColor(paintSlot.color);
        g.fillRect(paintSlot.x + gap, paintSlot.y + gap, paintSlot.width - 2 * gap, paintSlot.height - 2 * gap);
        g.setColor(default_color);
    }

    public void update(int[][] matrix, Map<Integer, Color> colorMap) {

        this.height = matrix.length;
        this.width = matrix[0].length;
        int slots = height * width;
        int heightDiff = this.getHeight() / height;
        int widthDiff = this.getWidth() / width;

        this.paintSlots = createPaintSlots(matrix, slots, heightDiff, widthDiff, colorMap);

        if (isDragging) {
            overColors.forEach(((point) -> {
                Color c = getColor(networkingEngine.getEnvironmentMatrixValue(), colorMap);
                paintSlots.add(new PaintSlot(point.x * widthDiff, point.y * heightDiff, heightDiff, widthDiff, c, point.x == width - 1, point.y == height - 1));
            }));
        }


        super.repaint();
    }

    private CopyOnWriteArrayList<PaintSlot> createPaintSlots(int[][] matrix, int slots, int heightDiff, int widthDiff, Map<Integer, Color> colorMap) {
        CopyOnWriteArrayList<PaintSlot> paintSlots = new CopyOnWriteArrayList<>();

        if (height == 0 || width == 0) {
            return null;
        }


        for (int i = 0; i < slots; i++) {

            int x = i % width;
            int y = i / width;


            Color c = getColor(matrix[y][x], colorMap);
            paintSlots.add(new PaintSlot(x * widthDiff, y * heightDiff, heightDiff, widthDiff, c, x == width - 1, y == height - 1));
        }

        return paintSlots;
    }

    private Color getColor(int val, Map<Integer, Color> colorMap) {

        if(colorMap.keySet().contains(val)){
            return colorMap.get(val);
        }

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

    private Point convertScreenLocation2MatrixLocation(double x, double y) {
        y = (y * height) / getHeight();
        x = (x * width) / getWidth();
        return new Point((int) x, (int) y);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point onMatrix = convertScreenLocation2MatrixLocation(e.getX(), e.getY());
        networkingEngine.sendValue(onMatrix.y, onMatrix.x, networkingEngine.getEnvironmentMatrixValue());

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        applyDragging();

        new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            isDragging = false;
            if (wasRunning) {
                networkingEngine.startGame();
            }
            overColors = new CopyOnWriteArrayList<>();
        }).start();
    }

    private void applyDragging() {
        overColors.forEach((p) -> {
            networkingEngine.sendValue(p.y, p.x, networkingEngine.getEnvironmentMatrixValue());
        });
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void setEngine(NetworkingEngine engien) {
        this.networkingEngine = engien;
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private int startX, startY;
    private boolean isDragging, wasRunning;

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!isDragging) {
            isDragging = true;
            wasRunning = networkingEngine.isRunning();
            if (wasRunning) {
                networkingEngine.pauseGame();
            }
            Point p = convertScreenLocation2MatrixLocation(e.getX(), e.getY());
            startX = p.x;
            startY = p.y;
        }

        Point p = convertScreenLocation2MatrixLocation(e.getX(), e.getY());
        int x = p.x, y = p.y;

        overColors = new CopyOnWriteArrayList<>();

        int min_x = Math.max(0, Math.min(x, startX));
        int max_x = Math.min(width - 1, Math.max(x, startX));
        int min_y = Math.max(0, Math.min(y, startY));
        int max_y = Math.min(height - 1, Math.max(y, startY));

        for (int i = min_x; i <= max_x; i++) {
            for (int j = min_y; j <= max_y; j++) {
                overColors.add(new Point(i, j));
            }
        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }


    private static class PaintSlot {

        public final int x, y, height, width;
        public final Color color;
        public final boolean xEdge, yEdge;

        public PaintSlot(int x, int y, int height, int width, Color color, boolean xEdge, boolean yEdge) {
            this.x = x;
            this.y = y;
            this.height = height;
            this.width = width;
            this.color = color;
            this.xEdge = xEdge;
            this.yEdge = yEdge;
        }

        @Override
        public String toString() {
            return "PaintSlot{" +
                    "x=" + x +
                    ", y=" + y +
                    ", height=" + height +
                    ", width=" + width +
                    ", color=" + color +
                    '}';
        }
    }
}
