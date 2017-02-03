package com.maaya.azure.example.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.nio.file.Paths;

import static org.bytedeco.javacpp.opencv_core.IplImage;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

/**
 * Created by 4605851 on 2017/02/01.
 */
@Component
public class CVHelper {
    //変更前作業ファイル
    @Value("${cv.tempfile.dir}")
    private String TEMP_FILE_DIR;
    @Value("${cv.tempfile.after}")
    private String TEMP_FILE_NAME;
    @Value("${cv.tempfile.before}")
    private String TEMP_SOUCE_FILE_NAME;

    /**
     * グレースケール画像を作成する
     *
     * @param targetPath
     * @return
     * @throws URISyntaxException
     */
    public String makeGrayScaleImage(String targetPath) throws Exception {
        IplImage grayScaleImage = cvLoadImage(targetPath, CV_LOAD_IMAGE_GRAYSCALE);

        if (grayScaleImage != null) {
            //格納ディレクトリの取得
            String tempFileDir = Paths.get(CVHelper.class.getResource(TEMP_FILE_DIR + TEMP_SOUCE_FILE_NAME).toURI()).getParent().toString();
            //String tempFile = Paths.get(tempFileDir, TEMP_FILE_NAME).toString();
            cvSaveImage(TEMP_FILE_NAME, grayScaleImage);

            return  Paths.get(CVHelper.class.getResource(TEMP_FILE_DIR + TEMP_FILE_NAME).toURI()).getParent().getParent().getParent().toString();
        }
        return null;
    }

}
