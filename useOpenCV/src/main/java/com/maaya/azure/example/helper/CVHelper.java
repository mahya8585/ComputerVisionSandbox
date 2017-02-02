package com.maaya.azure.example.helper;

import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

/**
 * Created by 4605851 on 2017/02/01.
 */
public class CVHelper {

    /**
     * グレースケール画像を作成する
     * @param targetUrl
     * @param grayScaleFileName
     * @return
     * @throws URISyntaxException
     */
    public static String makeGrayScaleImage(String targetUrl, String grayScaleFileName) throws URISyntaxException {
        //TODO
        //String grayScaleFilePath = makeTempFile(grayScaleFileName);
        String filepath = Paths.get(CVHelper.class.getResource("/sample.jpg").toURI()).toString();

        //IplImage grayScaleImage = cvLoadImage(targetUrl, CV_LOAD_IMAGE_GRAYSCALE);
        IplImage grayScaleImage = cvLoadImage(filepath, CV_LOAD_IMAGE_GRAYSCALE);
        if (grayScaleImage != null) {
            //cvSaveImage(grayScaleFilePath, grayScaleImage);
            cvSaveImage("test.jpg", grayScaleImage);
            return grayScaleFileName;
        }

        return null;
    }

    /**
     * ファイルパスの作成(ローカル)
     * @param fileName
     * @return
     * @throws URISyntaxException
     */
    private static String makeTempFile(String fileName) throws URISyntaxException {
        //TODO File処理Helperを作成し移動する
        return Paths.get(CVHelper.class.getResource("/" + fileName).toURI()).toString();
    }
}
