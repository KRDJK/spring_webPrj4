package com.project.web_prj.common;

import com.project.web_prj.util.FileUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
public class UploadController {

    private static final String UPLOAD_PATH = "E:\\sl_basic\\upload";



    // upload-form.jsp로 포워딩하는 요청
    @GetMapping("/upload-form")
    public String uploadForm() {
        return "upload/upload-form";
    }


    // 파일 업로드 처리를 위한 요청
    // MultipartFile : 클라이언트가 전송한 파일 정보들을 담은 객체
    // ex) 원본 파일명, 파일 용량, 파일 컨텐츠 타입, 업로드한 운영체제에 대한 정보, 업로드한 시간 등등등을 포함한다.
    @PostMapping("/upload")
    public String upload(@RequestParam("file") List<MultipartFile> fileList) {

//        application.properties 설정 파일에서 업로딩 파일 크기 제한도 할 수 있다.
//        spring.servlet.multipart.max-file-size=20MB
//        spring.servlet.multipart.max-request-size=20MB


        log.info("/upload POST! - {}", fileList);

        for (MultipartFile file : fileList) {

            log.info("file-name: {}", file.getName());
            log.info("file-origin-name: {}", file.getOriginalFilename());
            log.info("file-size(): {}KB", (double) file.getSize() / 1024); // byte 단위로 나온다.
            log.info("file-type: {}", file.getContentType());
            System.out.println("=========================================================================");


            // 서버에 업로드파일 저장

//            // 1-1. 업로드 파일 저장 경로
//            String uploadPath = "E:\\sl_basic\\upload"; // 윈도우는 \ (역슬래시) , 리눅스는 / (정슬래시)


            // 1. 세이브파일 객체 생성
            // - 첫번째 파라미터는 파일 저장경로 지정, 두번째 파라미터는 파일명 지정
        /*File f = new File(uploadPath, file.getOriginalFilename());

        try {
            file.transferTo(f); // 이렇게 했더니 파일명이 중복이면 덮어 씌워버리네??? 문제군..
                                // 그래서 일단위 또는 분단위로 폴더를 새로 만들기도 하고
                                // 한 20자리의 문자열을 랜덤으로 생성해서 파일명으로 만들어주기도 한다.
        } catch (IOException e) {
            e.printStackTrace();
        }*/

            FileUtils.uploadFile(file, UPLOAD_PATH);
        }


        return "redirect:/upload-form";
    }


    // 비동기 요청 파일 업로드 처리
    @PostMapping("/ajax-upload")
    @ResponseBody
    public List<String> ajaxUpload(List<MultipartFile> files) { // JSON 방식이 아니면 그냥 form 태그 활용해서 파라미터 받듯이 받으면 된다.

        log.info("/ajax-upload POST! - {}", files.get(0).getOriginalFilename());


        // 클라이언트에게 전송할 파일경로 리스트
        List<String> fileNames = new ArrayList<>();


        // 클라이언트가 전송한 파일 업로드하기
        for (MultipartFile file : files) {

            String fullPath = FileUtils.uploadFile(file, UPLOAD_PATH);

            fileNames.add(fullPath);
        }


        return fileNames;
    }


    // 파일 데이터 로드 요청 처리
    @GetMapping("/loadFile")
    @ResponseBody
    public void loadFile(@RequestParam("fileName") String fullPath) {
        log.info("/loadFile GET - {}", fullPath);

    }

}
