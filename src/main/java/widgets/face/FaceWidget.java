package widgets.face;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;

import static org.bytedeco.opencv.global.opencv_imgproc.*;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.nio.IntBuffer;

import org.bytedeco.javacv.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.indexer.*;
import org.bytedeco.javacv.Frame;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_imgproc.*;
import org.bytedeco.opencv.opencv_calib3d.*;
import org.bytedeco.opencv.opencv_objdetect.*;
import org.bytedeco.opencv.opencv_face.*;
import utils.BaseUrl;
import utils.ClientHttp;


import javax.swing.*;

import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgproc.*;
import static org.bytedeco.opencv.global.opencv_face.*;
import static org.bytedeco.opencv.global.opencv_calib3d.*;
import static org.bytedeco.opencv.global.opencv_objdetect.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

import static org.bytedeco.opencv.global.opencv_core.CV_8UC1;
import static org.bytedeco.opencv.global.opencv_imgproc.resize;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_core.*;

public class FaceWidget {

    /**
     * @param name 用户id
     * @param mode 0: 人脸识别 1: 人脸注册
     * @throws Exception 同login接口
     */
    public FaceWidget(String name, int mode) throws Exception {

        String classifierName = "haarcascade_frontalface_alt.xml";

        CascadeClassifier classifier = new CascadeClassifier(classifierName);

        FrameGrabber grabber = FrameGrabber.createDefault(0);
        grabber.start();

        OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();

        Mat grabbedImage = converter.convert(grabber.grab());
        int height = grabbedImage.rows();
        int width = grabbedImage.cols();

        Mat grayImage = new Mat(height, width, CV_8UC1);
        CanvasFrame frame = new CanvasFrame("Vides", CanvasFrame.getDefaultGamma() / grabber.getGamma());

        Point hatPoints = new Point(3);

        JPanel mainPanel = new JPanel();
        GridBagLayout layout = new GridBagLayout();
        mainPanel.setLayout(layout);
        CanvasFrame frame2 = new CanvasFrame("Image 2");
        frame2.setVisible(false);
        addItem(mainPanel, frame.getCanvas(), 0, 0, 1, 1, 1, 0.5);
        addItem(mainPanel, frame2.getCanvas(), 0, 1, 1, GridBagConstraints.REMAINDER, 1, 0.5);
        frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JFrame mainFrame = new JFrame("My Canvas Frame");

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().add(mainPanel);
        mainFrame.setSize(600, 800);
        mainFrame.setVisible(true);
        frame.setVisible(false);

        Size newSize = new Size(600, 400);
        Mat t = converter.convert(grabber.grab());
        Mat buffer = new Mat(newSize);
        org.bytedeco.opencv.global.opencv_imgproc.resize(t, buffer, newSize);


        int count = 0;
        ArrayList<String> facesEncode = new ArrayList<String>();
        int interval = 0;
        while ((grabbedImage = converter.convert(grabber.grab())) != null && count < 20) {
            cvtColor(grabbedImage, grayImage, CV_BGR2GRAY);
            RectVector faces = new RectVector();
            classifier.detectMultiScale(grayImage, faces);
            long total = faces.size();
            Rect rBuf = null;
            for (long i = 0; i < total; i++) {
                Rect r = faces.get(i);
                if (r.area() > 50000) {
                    rBuf = r;
                }
                int x = r.x(), y = r.y(), w = r.width(), h = r.height();
                rectangle(grabbedImage, new Point(x, y), new Point(x + w, y + h), Scalar.RED, 1, CV_AA, 0);
                // 绿色帽子
                // To access or pass as argument the elements of a native array, call position() before.
                hatPoints.position(0).x(x - w / 10).y(y - h / 10);
                hatPoints.position(1).x(x + w * 11 / 10).y(y - h / 10);
                hatPoints.position(2).x(x + w / 2).y(y - h / 2);
                fillConvexPoly(grabbedImage, hatPoints.position(0), 3, Scalar.GREEN, CV_AA, 0);
            }
            threshold(grayImage, grayImage, 64, 255, CV_THRESH_BINARY);
            // To check if an output argument is null we may call either isNull() or equals(null).
            MatVector contours = new MatVector();
            findContours(grayImage, contours, CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
            long n = contours.size();

            Mat linedImage = grabbedImage;


            if (rBuf != null) {
                Mat faceImg = new Mat(grabbedImage, rBuf);
                org.bytedeco.opencv.global.opencv_imgproc.resize(faceImg, faceImg, newSize);
                frame2.showImage(converter.convert(faceImg));
                buffer = faceImg;
                cvtColor(faceImg, faceImg, CV_BGR2GRAY);


                if ((interval == 3 && mode == 1) || (interval == 1 && mode == 0)) {
                    interval = 0;
                    count++;

                    BytePointer bytePointer = new BytePointer();
                    imencode(".jpg", faceImg, bytePointer);
                    byte[] bytes = new byte[(int) bytePointer.limit()];
                    bytePointer.get(bytes);
                    String base64 = Base64.getEncoder().encodeToString(bytes);
                    facesEncode.add(base64);
                    System.out.println(base64);
                } else {
                    interval++;
                }


            } else {
                frame2.showImage(converter.convert(buffer));
            }

            for (long i = 0; i < n; i++) {
                Mat contour = contours.get(i);
                Mat points = new Mat();
                approxPolyDP(contour, points, arcLength(contour, true) * 0.02, true);
                drawContours(linedImage, new MatVector(points), -1, Scalar.BLUE);
            }
            org.bytedeco.opencv.global.opencv_imgproc.resize(linedImage, linedImage, newSize);

            Frame rotatedFrame = converter.convert(linedImage);
            frame.showImage(rotatedFrame);

//            recorder.record(rotatedFrame);
            mainFrame.getContentPane().repaint();
        }

        HashMap<String, Object> res = new HashMap<>();
        System.out.println(facesEncode.size());
        res.put("name", name);
        res.put("faceImages", facesEncode);
        if (mode == 1) {
            ClientHttp.Post(BaseUrl.GetUrl("/face/register"), res, null);
        } else {
            ClientHttp.Post(BaseUrl.GetUrl("/face/login"), res, null);
        }
        mainFrame.dispose();
        grabber.stop();
    }

    public static void addItem(JPanel p, Component c, int x, int y, int width, int height, double weightx, double weighty) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        p.add(c, gbc);
    }
}
