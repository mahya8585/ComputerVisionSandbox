package jp.co.opst.cloud.study.controller;


import jp.co.opst.cloud.study.domain.helper.AzureComputerVisionHelper;
import jp.co.opst.cloud.study.domain.helper.AzureStorageHelper;
import jp.co.opst.cloud.study.domain.helper.DisplayHostHelper;
import jp.co.opst.cloud.study.domain.model.dto.Image;
import jp.co.opst.cloud.study.domain.model.form.FileUploadForm;
import jp.co.opst.cloud.study.domain.service.FileUploadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

/**
 * ファイルアップロード機能
 * Created by maaya on 2016/12/12.
 */
@Controller
@RequestMapping("/")
@Slf4j
public class FileUploadController {

    @Autowired
    private DisplayHostHelper displayHostHelper;
    @Autowired
    private AzureStorageHelper azureStorageHelper;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private AzureComputerVisionHelper azureComputerVisionHelper;


    /**
     * 現在表示可能な画像の一覧を表示する画面を返却する
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/gallery")
    public String gallery(Model model) {
        log.debug("写真一覧表示API");

        //現在使用できる写真を取得する
        Set<String> sourceUrls = null;
        Set<String> thumbnailUrls = null;
        try {
            sourceUrls = azureStorageHelper.selectAll(AzureStorageHelper.Containar.SOURCE.name().toLowerCase());
            thumbnailUrls = azureStorageHelper.selectAll(AzureStorageHelper.Containar.THUMBNAIL.name().toLowerCase());
        } catch (Exception e) {
            e.printStackTrace();
        }

        //サムネイルリストとフルサイズリストのデータセットを作成する
        List<Image> images = fileUploadService.createImageUrls(sourceUrls, thumbnailUrls);

        //画面設定
        model.addAttribute("images", images);
        model.addAttribute("dispIp", displayHostHelper.createIp());
        model.addAttribute("fileUploadForm", new FileUploadForm());
        return "index";
    }

    /**
     * ファイルアップロード処理を行う
     *
     * @param fileData アップロードされた画像
     * @param model    表示に必要なデータモデル
     * @return 画面表示
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam MultipartFile fileData, Model model) {
        log.debug("ファイルアップロード");

        //TODO Azure Comuputer Visionはファイルサイズが4MBまでなので、ファイルサイズのチェック処理を追加するべきです

        try {
            //formで受け取ったfileをBLOBへ
            final String sourceContainer = AzureStorageHelper.Containar.SOURCE.name().toLowerCase();
            azureStorageHelper.upload(fileData.getInputStream(), sourceContainer,
                    fileData.getOriginalFilename(), fileData.getBytes().length);

            //サムネイル画像の作成
            InputStream imgStream = azureComputerVisionHelper.createThumbnail(azureStorageHelper.selectBlobUri(sourceContainer, fileData.getOriginalFilename()));

            //サムネイルのアップロード
            String thumbnailContainer = AzureStorageHelper.Containar.THUMBNAIL.name().toLowerCase();
            String thumbnailName = fileUploadService.createThumbnailFileName(fileData.getOriginalFilename());
            azureStorageHelper.upload(imgStream, thumbnailContainer, thumbnailName, imgStream.available());

        } catch (Exception e) {
            e.printStackTrace();
        }

        //一覧データの取得と表示
        return gallery(model);
    }


}
