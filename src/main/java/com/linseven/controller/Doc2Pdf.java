package com.linseven.controller;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 *convert
 */
@Component
public class Doc2Pdf {

    public  boolean getLicense() {
        boolean result = false;
        try {
            ClassLoader classLoader = Doc2Pdf.class.getClassLoader();
            InputStream is =classLoader.getResourceAsStream("license.xml"); // license.xml应放在…\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public  void doc2pdf(String Address) {

        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {
            long old = System.currentTimeMillis();
            File file = new File("d:/pdf1.pdf"); //新建一个空白pdf文档
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(Address); //Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void doc2pdf(InputStream inputStream, OutputStream outputStream) {

        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {

            Document doc = new Document(inputStream); //Address是将要被转化的word文档
            doc.save(outputStream, SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static  void main(String [] args){
        Doc2Pdf doc2Pdf = new Doc2Pdf();
        doc2Pdf.doc2pdf("d:/images/Word.docx");
    }
}