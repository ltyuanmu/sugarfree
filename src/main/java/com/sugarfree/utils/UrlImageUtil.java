package com.sugarfree.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * @ClassName: 网络图片工具包
 * @Description:
 * @author: LT
 * @date: 2017/2/8
 */
@Slf4j
public class UrlImageUtil {
    @Value("${tmp.filePath}")
    private static String filePath;
    /**
     * 通过url获得图片文件
     * @param strUrl
     * @return
     */
    public static File getImageFromNetByUrl(String strUrl){
        String imgName= FilenameUtils.getName(strUrl);
        //String imgPath = "E:/temp/"+imgName;
        checkOrCreateDir(filePath);
        String imgPath = filePath+imgName;
        //查看图片是否存在
        File file = new File(imgPath);
        if(file.exists()){
            return file;
        }
        try {
            URL url = new URL(strUrl);
            FileUtils.copyURLToFile(url,file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 校验有没有此目录 如果有则创建
     * @param path
     */
    private static void checkOrCreateDir(String path){
        log.info("check path:{}",path);
        File filePath = new File(path);
        if  (!filePath.exists() && !filePath.isDirectory()){
            log.info("{} is not exists !",path);
            boolean result = filePath.mkdir();
            log.info("path create result:{}",result);
        }
    }

    public static File joinImage(File qrCode,File baseMap) throws IOException {
        if (baseMap==null){
            return qrCode;
        }
        //底图
        BufferedImage ImageOne = ImageIO.read(baseMap);
        int width = ImageOne.getWidth();//图片宽度
        int height = ImageOne.getHeight();//图片高度
        //从图片中读取RGB
        int[] ImageArrayOne = new int[width*height];
        ImageArrayOne = ImageOne.getRGB(0,0,width,height,ImageArrayOne,0,width);
        //二维码
        BufferedImage ImageTwo = ImageIO.read(qrCode);
        int widthTwo = ImageTwo.getWidth();//图片宽度
        int heightTwo = ImageTwo.getHeight();//图片高度
        //从图片中地区RGB
        int[] ImageArrayTwo = new int[widthTwo*heightTwo];
        ImageArrayTwo = ImageTwo.getRGB(0,0,widthTwo,heightTwo,ImageArrayTwo,0,widthTwo);

        //生成新图片
        BufferedImage ImageNew = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        ImageNew.setRGB(0,0,width,height,ImageArrayOne,0,width);//设置左半部分的RGB
        //进行设置叠加坐标
        ImageNew.setRGB(120,120,widthTwo,heightTwo,ImageArrayTwo,0,widthTwo);//设置右半部分的RGB
        //添加文件夹
        checkOrCreateDir(filePath);
        //生成图片路径
        String path = filePath+StringUtil.generateShortUuid()+".png";
        File outFile = new File(path);
        ImageIO.write(ImageNew, "png", outFile);//写图片
        return outFile;
    }
}
