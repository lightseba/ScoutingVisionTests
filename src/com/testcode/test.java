package com.testcode;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

public class test {

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String imageName = "Scan1.jpg";

        Mat image;

        image = Imgcodecs.imread(imageName, Imgcodecs.IMREAD_COLOR);

        if(image.empty()){
            System.out.println("ded");
            return;
        }

        displayImage(Mat2Image(image));


    }

    public static BufferedImage Mat2Image(Mat m){

        int type = BufferedImage.TYPE_BYTE_GRAY;

        if(m.channels() > 1) type = BufferedImage.TYPE_3BYTE_BGR;

        int bufferSize = m.channels()*m.cols()*m.rows();
        byte[] b = new byte[bufferSize];
        m.get(0,0,b);
        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b,0,targetPixels,0,b.length);

        return image;
    }

    public static void displayImage(Image img) {
        ImageIcon icon = new ImageIcon(img);
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(img.getWidth(null)+50, img.getHeight(null)+50);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

}
