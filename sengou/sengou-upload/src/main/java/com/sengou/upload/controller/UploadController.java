package com.sengou.upload.controller;

import com.sengou.upload.config.SegouFileConfiguration;
import com.sengou.upload.service.UploadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

@Controller
@RequestMapping("upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @Autowired
    private SegouFileConfiguration segouFileConfiguration;

    /**
     * 图片上传
     * @param file
     * @return
     */
    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file){
        String url = this.uploadService.upload(file);
        if (StringUtils.isBlank(url)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(url);
    }

    @RequestMapping(value="/images/{fileName}")
    public void showPic(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileName){

        String fileUrl= segouFileConfiguration.getPath()+fileName;
        try {
            File filePath = new File(fileUrl);
            if(filePath.exists()){
                //读图片
                FileInputStream inputStream = new FileInputStream(filePath);
                int available = inputStream.available();
                byte[] data = new byte[available];
                inputStream.read(data);
                inputStream.close();
                //写图片
                String ext = StringUtils.substringAfterLast(fileName, ".");
                response.setContentType("image/"+ext);
                response.setCharacterEncoding("UTF-8");
                OutputStream stream = new BufferedOutputStream(response.getOutputStream());
                stream.write(data);
                stream.flush();
                stream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}