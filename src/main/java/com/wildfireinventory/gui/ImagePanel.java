package com.wildfireinventory.gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

public class ImagePanel extends JPanel {
    private final Image backgroundImage;

    public ImagePanel(String filePath) {
        File file = new File(filePath);
        System.out.println("Trying to load image from: " + file.getAbsolutePath());
        System.out.println("Image exists? " + file.exists());

        this.backgroundImage = new ImageIcon(filePath).getImage();
        setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}