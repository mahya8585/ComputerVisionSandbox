package com.maaya.azure.example.service;

import com.maaya.azure.example.dto.Image;
import com.maaya.azure.example.helper.AzureStorageHelper;
import com.maaya.azure.example.helper.CVHelper;
import com.microsoft.azure.storage.StorageException;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

/**
 * Created by 4605851 on 2017/02/01.
 */
@Component
public class BwImageService {
    private static final String BW_PREFIX = "bw_";

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
     * グレースケール画像を作成、AzureblobへアップロードしURLを取得する
     * @param sourceFilePath
     * @param fileName
     * @return
     */
    public String makeBWImageUrl(String sourceFilePath, String fileName) throws URISyntaxException, IOException, StorageException, InvalidKeyException {
        String bwImageName = BW_PREFIX + fileName;
        String bwImagePath =  CVHelper.makeGrayScaleImage(sourceFilePath, bwImageName);

        //fileをBLOBへアップロード
        File bwFile = new File(bwImagePath);
        FileInputStream bwInputStream = new FileInputStream(bwFile);
        AzureStorageHelper.upload(bwInputStream, bwImageName, bwInputStream.available());

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
}
