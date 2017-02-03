package com.maaya.azure.example.helper;

import org.springframework.beans.factory.annotation.Value;

import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

/**
 * Created by 4605851 on 2017/02/01.
 */
public class CVHelper {
    //変更前作業ファイル
    @Value("${cv.tempfile.name}")
    private String TEMP_FILE_NAME;

    /**
     * グレースケール画像を作成する
     *
     * @param targetPath
     * @return
     * @throws URISyntaxException
     */
    public String makeGrayScaleImage(String targetPath) throws URISyntaxException {
        IplImage grayScaleImage = cvLoadImage(targetPath, CV_LOAD_IMAGE_GRAYSCALE);

        if (grayScaleImage != null) {
            cvSaveImage(TEMP_FILE_NAME, grayScaleImage);
            return Paths.get(CVHelper.class.getResource(TEMP_FILE_NAME).toURI()).toString();
        }

        return null;
    }

}
