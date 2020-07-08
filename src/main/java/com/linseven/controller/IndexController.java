package com.linseven.controller;

import com.aspose.words.HeaderFooterType;
import com.aspose.words.SaveFormat;
import com.aspose.words.Section;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @Author linseven
 * @Date 2020/5/30
 */
@RestController
@Slf4j
public class IndexController {

    @Autowired
    private Doc2Pdf doc2Pdf;

    @GetMapping("/index")
    public  void index(){
        doc2Pdf.doc2pdf("/home/1.docx");
        log.info("index");

    }

    @GetMapping("/excel")
    public  void excel(String filepath){
        doc2Pdf.doc2pdf(filepath);
        log.info("excel");

    }


}
