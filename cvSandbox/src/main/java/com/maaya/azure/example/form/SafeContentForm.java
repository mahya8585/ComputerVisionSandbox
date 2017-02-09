package com.maaya.azure.example.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by maaya on 2016/12/27.
 */
@Data
public class SafeContentForm {
    //private MultipartFile adultTarget;
    private String contentUrl;
}
