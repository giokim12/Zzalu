package com.samsamoo.zzalu.infra.amazonS3.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@RequiredArgsConstructor
public class S3request {
    private MultipartFile data;

}
