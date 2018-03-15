package com.maaya.azure.example.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by maaya on 2017/06/24.
 */
@Data
public class KozakeForm {
    private MultipartFile targetFile;
    private String targetUrl;
}
