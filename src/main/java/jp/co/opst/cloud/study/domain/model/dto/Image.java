package jp.co.opst.cloud.study.domain.model.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by user on 2016/10/07.
 */
@Data
public class Image {
    /** フル画像のURL */
    private String full;

    /** サムネイル画像のURL */
    private String thumb;

    public Image(String url, String thumbUrl) {
        setFull(url);
        setThumb(thumbUrl);
    }
}
