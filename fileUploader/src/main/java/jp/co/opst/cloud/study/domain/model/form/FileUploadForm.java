package jp.co.opst.cloud.study.domain.model.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by maaya on 2016/12/27.
 */
@Data
public class FileUploadForm {
    private MultipartFile fileData;
}
