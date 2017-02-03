package com.maaya.azure.example.helper;

import com.maaya.azure.example.config.CommonConfig;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URISyntaxException;

import static org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

/**
 * Created by 4605851 on 2017/02/01.
 */
public class CVHelper {
    @Autowired
    private static CommonConfig commonConfig;

    /**
     * グレースケール画像を作成する
     *
     * @param targetPath
     * @return
     * @throws URISyntaxException
     */
    public static String makeGrayScaleImage(String targetPath) throws URISyntaxException {
        IplImage grayScaleImage = cvLoadImage(targetPath, CV_LOAD_IMAGE_GRAYSCALE);

        if (grayScaleImage != null) {
            cvSaveImage(commonConfig.getTempFileName(), grayScaleImage);
            return commonConfig.getTempFileName();
        }

        return null;
    }

}
