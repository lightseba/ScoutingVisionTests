package com.testcode;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import static org.opencv.core.Core.FONT_HERSHEY_PLAIN;

public class squaretester {

    public Mat run(Mat img){

        Size dims = new Size(20,5);


        Mat gray = new Mat();
        Mat thresh = new Mat();

//convert the image to black and white
        Imgproc.cvtColor(img, gray, Imgproc.COLOR_BGR2GRAY);

//convert the image to black and white does (8 bit)
        Imgproc.threshold(gray, thresh, 0, 255, Imgproc.THRESH_BINARY_INV + Imgproc.THRESH_OTSU);
        Mat temp = thresh.clone();
//find the contours
        Mat hierarchy = new Mat();

        Mat corners = new Mat(4,1,CvType.CV_32FC2);
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
        Imgproc.findContours(temp, contours,hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        hierarchy.release();

        for (MatOfPoint contour : contours)
        {
            MatOfPoint2f contour_points = new MatOfPoint2f(contour.toArray());
            RotatedRect minRect = Imgproc.minAreaRect( contour_points );
            Point[] rect_points = new Point[4];
            minRect.points( rect_points );


            if(minRect.size.height > img.width() / 2)
            {
                List<Point> srcPoints = new ArrayList<Point>(4);
                srcPoints.add(rect_points[2]);
                srcPoints.add(rect_points[3]);
                srcPoints.add(rect_points[0]);
                srcPoints.add(rect_points[1]);

                corners = Converters.vector_Point_to_Mat(
                        srcPoints, CvType.CV_32F);
            }

        }
        Imgproc.erode(thresh, thresh, new Mat(), new Point(-1,-1), 10);
        Imgproc.dilate(thresh, thresh, new Mat(), new Point(-1,-1), 5);
        Mat results = new Mat(1000,250,CvType.CV_8UC3);
        Mat quad = new Mat(1000,250,CvType.CV_8UC1);

        List<Point> dstPoints = new ArrayList<Point>(4);
        dstPoints.add(new Point(0, 0));
        dstPoints.add(new Point(1000, 0));
        dstPoints.add(new Point(1000, 250));
        dstPoints.add(new Point(0, 250));
        Mat quad_pts = Converters.vector_Point_to_Mat(dstPoints, CvType.CV_32F);

        Mat transmtx = Imgproc.getPerspectiveTransform(corners, quad_pts);
        Imgproc.warpPerspective( img, results, transmtx, new Size(1000,250));
        Imgproc.warpPerspective( thresh, quad, transmtx, new Size(1000,250));



        Imgproc.resize(quad,quad,new Size(20,5));


        System.out.println(quad.dump());


        return quad;
    }
}

class Thing{

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        test.displayImage(test.Mat2Image(new squaretester().run(Imgcodecs.imread("testingSquares.jpg"))));
    }
}
