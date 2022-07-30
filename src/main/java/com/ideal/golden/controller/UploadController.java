package com.ideal.golden.controller;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.ideal.golden.common.prop.GoldenProperties;
import com.ideal.golden.common.utils.ResultHelper;
import com.ideal.golden.model.result.ResultVo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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

    /**
    * @作者: Ideal
    * @说明: 上传图片接口
    * @时间: 2022/7/30 16:41
    * @param photo: 图片的File
    * @param name: 图片名称
    * @return com.ideal.golden.model.result.ResultVo
    */
    @PostMapping("/img")
    public ResultVo uploadImage(@RequestParam("imgFile") MultipartFile photo,
                                @RequestParam(value = "name", required = false) String name,
                                HttpServletRequest request) throws IOException {

        // 拿到扩展名
        String extension = FilenameUtils.getExtension(photo.getOriginalFilename());

        // 生成图片唯一名
        String uuid = UUID.randomUUID().toString();

        String imgFilename = uuid + "." + extension;

        // 拿到存储的路径
        GoldenProperties.Upload propertiesUpload = properties.getUpload();
        String absolutePath = propertiesUpload.getBasePath() + propertiesUpload.getImgPath();

        File file = new File(absolutePath + imgFilename);

        // 如果没有父级文件则进行创建
        FileUtils.forceMkdirParent(file);

        photo.transferTo(file);
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        StringBuilder origin = new StringBuilder();
        origin.append(scheme)
                .append("://")
                .append(serverName)
                .append(":")
                .append(serverPort);
        return ResultHelper.ok(origin + "/img/" + imgFilename);
    }
}
