package com.ideal.golden.controller;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ideal.golden.common.prop.GoldenProperties;
import com.ideal.golden.model.result.ResultVo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @作者 Ideal
 * @时间 2022-07-29 17:14
 * @类说明 实现文件上传
 */
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private GoldenProperties properties;

    @PostMapping("/img")
    public ResultVo uploadImage(@RequestParam("imgFile") MultipartFile photo, @RequestParam(value = "name", required = false) String name) throws IOException {

        String extension = FilenameUtils.getExtension(photo.getOriginalFilename());

        String uuid = UUID.randomUUID().toString();

        String imgFilename = uuid + "." + extension;

        GoldenProperties.Upload propertiesUpload = properties.getUpload();
        String absolutePath = propertiesUpload.getBasePath() + propertiesUpload.getImgPath();

        File file = new File(absolutePath + imgFilename);

        FileUtils.forceMkdirParent(file);

        photo.transferTo(file);
        return null;
    }
}
