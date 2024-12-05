package com.garbage.controller;

import com.garbage.service.FileService;
import com.garbage.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 图片上传和下载入口
 */
@RestController
@RequestMapping("/files")
public class FileController {


    private final static String userFilePath = "G:\\成品项目\\2-SSM\\垃圾分类识别系统\\项目源代码\\service\\image";

    private final static String baseUrl = "http://localhost:8080/files";

    @Autowired
    private FileService fileService;

    /**
     * 图片上传
     * @param
     * @return
     */
    @PostMapping("/file")
    public R uploadFile(HttpServletRequest request, MultipartFile file) {
        String uuid= System.currentTimeMillis()+".";
        String originalFilename = file.getOriginalFilename();
        String fileName= uuid + originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        try {
            if (fileService.uploadFile(file,fileName)) {
                return R.ok().put("row",baseUrl+"/image?imageName="+fileName);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
        return R.error();
    }

    /**
     * 图片下载
     * @param imageName
     * @param response
     * @throws IOException
     */
    @GetMapping("/image")
    public void getImage(@RequestParam("imageName") String imageName,
                         HttpServletResponse response) throws IOException {
        File fileDir = new File(userFilePath);
        File image=new File(fileDir.getAbsolutePath() +"/"+imageName);
        if (image.exists()){
            FileInputStream fileInputStream=new FileInputStream(image);
            byte[] bytes=new byte[fileInputStream.available()];
            if (fileInputStream.read(bytes)>0){
                OutputStream outputStream=response.getOutputStream();
                outputStream.write(bytes);
                outputStream.close();
            }
            fileInputStream.close();
        }
    }

}
