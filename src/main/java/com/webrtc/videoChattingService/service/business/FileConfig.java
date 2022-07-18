package com.webrtc.videoChattingService.service.business;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Random;

@Service
public class FileConfig {
    
    public String base64Decode(String fileBase64){

        //fileName
        String fileName = fileNameRandomPath();
        System.out.println(fileName);
        Path relativePath = Paths.get("");
        // 저장할 파일 경로를 지정합니다.
        String filePath = relativePath.toAbsolutePath().toString() + "/src/main/resources/image/" + fileName;
        String returnPath =  "/src/main/resources/image/" + fileName;

        System.out.println("Working Directory = " + filePath);

        try {

            File file = new File(filePath);
            // BASE64를 일반 파일로 변환하고 저장합니다.
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decodedBytes = decoder.decode(fileBase64.getBytes());
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(decodedBytes);
            fileOutputStream.close();

        } catch(IOException e) {
            System.err.println(e);
        }
        //returnPath 반환
        return returnPath;
    }


    public String fileNameRandomPath(){

        // 현재 날짜 구하기
        LocalDate now = LocalDate.now();
        // 포맷 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        // 포맷 적용
        String formatedNow = now.format(formatter);

        Random ran = new Random();


        return formatedNow + "_" + ran.nextInt(1000000) + ".png";
    }

    public void fileDelete(String filePath){
        File file = new File(filePath);
        if(file.exists()){
            file.delete();
        }

    }

}
