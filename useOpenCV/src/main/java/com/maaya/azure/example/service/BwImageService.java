package com.maaya.azure.example.service;

import com.maaya.azure.example.dto.Image;
import com.maaya.azure.example.helper.AzureStorageHelper;
import com.maaya.azure.example.helper.CVHelper;
import com.microsoft.azure.storage.StorageException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.security.InvalidKeyException;

/**
 * Created by 4605851 on 2017/02/01.
 */
@Component
public class BwImageService {
    //変換ファイルプレフィックス
    private static final String BW_PREFIX = "bw_";
    //作業ファイルパス
    private static final String BEFORE_TEMP_FILE_PATH = "/static/img/before.jpg";

    /**
     * Azureへ画像をアップロードし、画像URLを取得する
     *
     * @param target
     * @return
     * @throws IOException
     * @throws InvalidKeyException
     * @throws StorageException
     * @throws URISyntaxException
     */
    public String makeSourceImageUrl(MultipartFile target) throws IOException, InvalidKeyException, StorageException, URISyntaxException {
        final String originalFileName = target.getOriginalFilename();

        //fileをBLOBへアップロード
        AzureStorageHelper.upload(target.getInputStream(), originalFileName, target.getBytes().length);

        //URL取得
        return AzureStorageHelper.selectBlobUrl(originalFileName);
    }

    /**
     * 白黒画像を作成、AzureblobへアップロードしURLを取得する
     *
     * @param sourceFile
     * @param fileName
     * @return
     */
    public String makeBWImageUrl(MultipartFile sourceFile, String fileName) throws URISyntaxException, IOException, StorageException, InvalidKeyException {
        //multi-Part file をfile化
        String tempFilePath = Paths.get(CVHelper.class.getResource(BEFORE_TEMP_FILE_PATH).toURI()).toString();
        convertMultipartToFile(sourceFile, tempFilePath);

        String bwImagePath = CVHelper.makeGrayScaleImage(tempFilePath);
        if(StringUtils.isEmpty(bwImagePath)) {
            throw new IOException("グレースケール画像作成失敗");
        }

        //fileをBLOBへアップロード
        //TODO ファイルパスを正しいものに変更
        File bwFile = new File(bwImagePath);
        FileInputStream bwInputStream = new FileInputStream(bwFile);
        AzureStorageHelper.upload(bwInputStream, BW_PREFIX + fileName, bwInputStream.available());

        //URL取得
        return AzureStorageHelper.selectBlobUrl(fileName);
    }

    /**
     * 処理結果を表示用モデルに設定する
     *
     * @param model
     * @param beforeUri
     * @param afterUri
     * @return
     */
    public Model makeResultModel(Model model, String beforeUri, String afterUri) {
        Image image = new Image(beforeUri, afterUri);
        model.addAttribute("image", image);

        return model;
    }

    /**
     * 実ファイルの作成
     *
     * @param multipartFile
     * @param filePath
     * @throws IOException
     */
    private void convertMultipartToFile(MultipartFile multipartFile, String filePath) throws IOException {
        File source = new File(filePath);
        FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), source);
    }
}
