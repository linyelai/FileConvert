package com.linseven.controller;

import com.aspose.words.HeaderFooterType;
import com.aspose.words.SaveFormat;
import com.aspose.words.Section;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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

    @PostMapping("/pdfConvert2Image")
    public void pdfConvert2Image(@RequestParam("file")MultipartFile [] files , HttpServletResponse response){

        //new a temp dir to save the images
        String tempDir = UUID.randomUUID().toString().replace("-","");
        OutputStream out = null;
        try {
            File temp = new File("d:/temp/"+tempDir);
            if(!temp.exists()){
                temp.mkdir();
            }
            //get upload file
            for(MultipartFile file :files){

                String filename = file.getOriginalFilename().substring(0,file.getOriginalFilename().lastIndexOf("."));
                InputStream inputStream = file.getInputStream();
                pdf2Image(inputStream,temp.getAbsolutePath(),filename,400);

            }
            //package the image
            //generae the zip name
            String zipname = UUID.randomUUID().toString().replace("-","")+".zip";
            File zip = new File(temp.getAbsolutePath()+File.separator+zipname);
            if(!zip.exists()){
                zip.createNewFile();
            }
            OutputStream outputStream = new FileOutputStream(temp.getAbsolutePath()+File.separator+zipname);
            List<File> imageFiles = Arrays.asList(temp.listFiles());
            List<File> imagefileList = imageFiles.stream().filter(file->{

                if(file.getName().contains("png")){
                    return true;
                }else{
                    return false;
                }

            }).collect(Collectors.toList());

            toZip(imagefileList,outputStream);
            //response the zip
             out = response.getOutputStream();
            InputStream inputStream = new FileInputStream(temp.getAbsolutePath()+File.separator+zipname);
            response.setHeader("Content-Disposition", "attachment;filename=" +zipname);
            int len =0;
            byte[] buff = new byte[1024];
            while((len=inputStream.read(buff))>0){
                out.write(buff,0,len);
            }
            out.close();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(out!=null){

                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static void toZip(List<File> srcFiles , OutputStream out)throws RuntimeException {
        long start = System.currentTimeMillis();
        ZipOutputStream zos = null ;
        try {
            zos = new ZipOutputStream(out);
            for (File srcFile : srcFiles) {
                byte[] buf = new byte[1024];
                zos.putNextEntry(new ZipEntry(srcFile.getName()));
                int len;
                FileInputStream in = new FileInputStream(srcFile);
                while ((len = in.read(buf)) != -1){
                    zos.write(buf, 0, len);
                }
                zos.closeEntry();
                in.close();
            }
            long end = System.currentTimeMillis();
            System.out.println("压缩完成，耗时：" + (end - start) +" ms");
        } catch (Exception e) {
            throw new RuntimeException("zip error from ZipUtils",e);
        }finally{
            if(zos != null){
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void pdf2Image(InputStream inputStream,String filepath,String filename,int dpi){

        PDDocument pdDocument;
        try{
            pdDocument = PDDocument.load(inputStream);
            PDFRenderer renderer = new PDFRenderer(pdDocument);
            int pages = pdDocument.getNumberOfPages();// 获取PDF页数
            System.out.println("PDF page number is:" + pages);
            StringBuffer imgFilePath = null;
            for (int i = 0; i < pages; i++) {
                String imagepath = filepath+File.separator+filename+"_"+i+".png";
                File dstFile = new File(imagepath);
                BufferedImage image = renderer.renderImage(i,2.0f);
                ImageIO.write(image, "png", dstFile);// PNG
            }
            System.out.println("PDF文档转PNG图片成功！");

        }catch (Exception e){

            e.printStackTrace();
        }

    }


}
