package com.sugarfree.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import java.io.File;
import java.net.URL;

/**
 * @ClassName: 网络图片工具包
 * @Description:
 * @author: LT
 * @date: 2017/2/8
 */
public class UrlImageUtil {
    /**
     * 通过url获得图片文件
     * @param strUrl
     * @return
     */
    public static File getImageFromNetByUrl(String strUrl){
        String imgName= FilenameUtils.getName(strUrl);
        String imgPath = "E:/temp/"+imgName;
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

}
