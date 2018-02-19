package com.testcode;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceRecTest {


    public void run() {



        String base = "/data/lbpcascades/lbpcascade_frontalface.xml";

        //System.out.println(getClass().getResource("test.class").getFile());
        //System.out.println(getClass().getResource("../../Scan1.jpg").getPath());
   // System.out.println(getClass().getResource("lena.png").getPath());
        //System.out.println(getClass().getResource("../resources/lbpcascade_frontalface.xml").getPath());



        CascadeClassifier faceDetector = new CascadeClassifier("lbpcascade_frontalface.xml");

        Mat image = Imgcodecs.imread("lena.png");

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(faceDetections.toArray().length);

        for(Rect rect : faceDetections.toArray()){
            Imgproc.rectangle(image, new Point(rect.x,rect.y), new Point(rect.x+ rect.width, rect.y+rect.height), new Scalar(0,255,0));
        }

        test.displayImage(test.Mat2Image(image));
    }
}

class HelloOpenCV {
    public static void main(String[] args) {
        System.out.println("Hello, OpenCV");
        // Load the native library.
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new FaceRecTest().run();
    }
}
