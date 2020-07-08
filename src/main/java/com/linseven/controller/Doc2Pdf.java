package com.linseven.controller;
import com.aspose.cells.Workbook;
import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

@Component
public class Doc2Pdf {

    public  boolean getLicense() {
        boolean result = false;
      /*  try {
            InputStream is =new FileInputStream("d:/license.xml"); // license.xml应放在…\WebRoot\WEB-INF\classes路径下
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return result;
    }

    public  void doc2pdf(String Address) {

        if (!getLicense()) { // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {
            long old = System.currentTimeMillis();
            File file = new File("pdf1.pdf"); //新建一个空白pdf文档
            FileOutputStream os = new FileOutputStream(file);
            Document doc = new Document(Address); //Address是将要被转化的word文档
            doc.save(os, SaveFormat.PDF);//全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            long now = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void excel2pdf(String Address) {

        if (!getLicense()) {          // 验证License 若不验证则转化出的pdf文档会有水印产生
            return;
        }
        try {
            File pdfFile = new File("d:/pdf1.pdf");// 输出路径
            Workbook wb = new Workbook(Address);// 原始excel路径
            FileOutputStream fileOS = new FileOutputStream(pdfFile);
            wb.save(fileOS, SaveFormat.PDF);
            fileOS.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static  void main(String [] args){
        Doc2Pdf doc2Pdf = new Doc2Pdf();
        doc2Pdf.excel2pdf("d:/index.xlsx");
    }
}