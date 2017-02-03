package jp.co.opst.cloud.study.domain.service;

import jp.co.opst.cloud.study.domain.helper.AzureStorageHelper;
import jp.co.opst.cloud.study.domain.model.dto.Image;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by maaya on 2016/12/26.
 */
@Component
public class FileUploadService {

    private final String THUMBNAIL_SUFFIX = "_thumbnail";

    /**
     * 画像URLセット(元URLとサムネイルURL)の作成
     *
     * @param sourceUrls    元画像URL群
     * @param thumbnailUrls サムネイルURL群
     * @return
     */
    public List<Image> createImageUrls(Set<String> sourceUrls, Set<String> thumbnailUrls) {

        return sourceUrls.stream().map(url -> new Image(url, createThumbnailUrl(url, thumbnailUrls))).collect(Collectors.toList());

    }

    /**
     * サムネイルURLの作成
     *
     * @param url           元URL
     * @param thumbnailUrls サムネイルURL群
     * @return
     */
    public String createThumbnailUrl(String url, Set<String> thumbnailUrls) {
        final String dirSplit = "/";
        final String sourceDomain = dirSplit + AzureStorageHelper.Containar.SOURCE.name().toLowerCase() + dirSplit;
        final String thumbnailDomain = dirSplit + AzureStorageHelper.Containar.THUMBNAIL.name().toLowerCase() + dirSplit;
        final String extension = extractExtension(url);
        final String thumbnailUrl = url.replace(sourceDomain, thumbnailDomain).replace(extension, THUMBNAIL_SUFFIX + extension);

        if (thumbnailUrls.contains(thumbnailUrl)) {
            return thumbnailUrl;
        } else {
            return url;
        }

    }

    /**
     * サムネイル名の作成
     * urlからファイル名を切り出す
     *
     * @param sourceName ファイルパス
     * @return
     */
    public String createThumbnailFileName(String sourceName) {
        final String extension = extractExtension(sourceName);
        return sourceName.replace(extension, THUMBNAIL_SUFFIX + extension);
    }

    /**
     * 拡張子の切り出し
     *
     * @param url 画像urlもしくは画像名
     * @return
     */
    private String extractExtension(String url) {
        final String[] urlSplit = url.split("\\.");
        return "." + urlSplit[urlSplit.length - 1];
    }


}
