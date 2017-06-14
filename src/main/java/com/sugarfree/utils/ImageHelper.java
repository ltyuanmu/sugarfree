package com.sugarfree.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/***
 * 对图片进行操作
 *
 * @author chenzheng_java
 * @since 2011/7/29
 *
 */
public class ImageHelper {

    private static ImageHelper imageHelper = null;

    public static ImageHelper getImageHelper() {
        if (imageHelper == null) {
            imageHelper = new ImageHelper();
        }
        return imageHelper;
    }

    /***
     * 按指定的比例缩放图片
     *
     * @param sourceImagePath
     *      源地址
     * @param destinationPath
     *      改变大小后图片的地址
     * @param scale
     *      缩放比例，如1.2
     */
    public static void scaleImage(String sourceImagePath,
                                  String destinationPath, double scale,String format) {

        File file = new File(sourceImagePath);
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(file);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();

            width = parseDoubleToInt(width * scale);
            height = parseDoubleToInt(height * scale);

            Image image = bufferedImage.getScaledInstance(width, height,
                    Image.SCALE_SMOOTH);
            BufferedImage outputImage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics graphics = outputImage.getGraphics();
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();

            ImageIO.write(outputImage, format, new File(destinationPath));
        } catch (IOException e) {
            System.out.println("scaleImage方法压缩图片时出错了");
            e.printStackTrace();
        }

    }

    /***
     * 将图片缩放到指定的高度或者宽度
     * @param sourceImagePath 图片源地址
     * @param destinationPath 压缩完图片的地址
     * @param width 缩放后的宽度
     * @param height 缩放后的高度
     * @param auto 是否自动保持图片的原高宽比例
     * @param format 图图片格式 例如 jpg
     */
    public static void scaleImageWithParams(String sourceImagePath,
                                            String destinationPath, int width, int height, boolean auto,String format) {

        try {
            File file = new File(sourceImagePath);
            BufferedImage bufferedImage = null;
            bufferedImage = ImageIO.read(file);
            if (auto) {
                ArrayList<Integer> paramsArrayList = getAutoWidthAndHeight(bufferedImage,width,height);
                width = paramsArrayList.get(0);
                height = paramsArrayList.get(1);
                System.out.println("自动调整比例，width="+width+" height="+height);
            }

            Image image = bufferedImage.getScaledInstance(width, height,
                    Image.SCALE_DEFAULT);
            BufferedImage outputImage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics graphics = outputImage.getGraphics();
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();
            ImageIO.write(outputImage, format, new File(destinationPath));
        } catch (Exception e) {
            System.out.println("scaleImageWithParams方法压缩图片时出错了");
            e.printStackTrace();
        }
    }

    /***
     * 将二维码图片缩放到指定的高度或者宽度
     * @param qrCode 图片源地址
     * @param qrCode 压缩完图片的地址
     * @param width 缩放后的宽度
     * @param height 缩放后的高度
     */
    public static void scaleQrCodeImage(File qrCode,
                                            File destFile, int width, int height) {

        try {
            File file = qrCode;
            BufferedImage bufferedImage = null;
            bufferedImage = ImageIO.read(file);
            ArrayList<Integer> paramsArrayList = getAutoWidthAndHeight(bufferedImage,width,height);
            width = paramsArrayList.get(0);
            height = paramsArrayList.get(1);
            System.out.println("自动调整比例，width="+width+" height="+height);

            Image image = bufferedImage.getScaledInstance(width, height,
                    Image.SCALE_DEFAULT);
            BufferedImage outputImage = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics graphics = outputImage.getGraphics();
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();
            ImageIO.write(outputImage, "png",destFile);
        } catch (Exception e) {
            System.out.println("scaleImageWithParams方法压缩图片时出错了");
            e.printStackTrace();
        }
    }

    /**
     * 将double类型的数据转换为int，四舍五入原则
     *
     * @param sourceDouble
     * @return
     */
    private static int parseDoubleToInt(double sourceDouble) {
        int result = 0;
        result = (int) sourceDouble;
        return result;
    }

    /***
     *
     * @param bufferedImage 要缩放的图片对象
     * @param width_scale 要缩放到的宽度
     * @param height_scale 要缩放到的高度
     * @return 一个集合，第一个元素为宽度，第二个元素为高度
     */
    private static ArrayList<Integer> getAutoWidthAndHeight(BufferedImage bufferedImage,int width_scale,int height_scale){
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        double scale_w =getDot2Decimal( width_scale,width);

        System.out.println("getAutoWidthAndHeight width="+width + "scale_w="+scale_w);
        double scale_h = getDot2Decimal(height_scale,height);
        if (scale_w<scale_h) {
            arrayList.add(parseDoubleToInt(scale_w*width));
            arrayList.add(parseDoubleToInt(scale_w*height));
        }
        else {
            arrayList.add(parseDoubleToInt(scale_h*width));
            arrayList.add(parseDoubleToInt(scale_h*height));
        }
        return arrayList;

    }


    /***
     * 返回两个数a/b的小数点后三位的表示
     * @param a
     * @param b
     * @return
     */
    public static double getDot2Decimal(int a,int b) {

        BigDecimal bigDecimal_1 = new BigDecimal(a);
        BigDecimal bigDecimal_2 = new BigDecimal(b);
        BigDecimal bigDecimal_result = bigDecimal_1.divide(bigDecimal_2, new MathContext(4));
        Double double1 = new Double(bigDecimal_result.toString());
        System.out.println("相除后的double为：" + double1);
        return double1;
    }

    /*public static void main(String[] args) throws IOException {
        File qrFile = new File("E:\\temp\\code.png");
        File destFile = new File("E:\\temp\\code1.png");
        scaleQrCodeImage(qrFile,destFile,104,104);

        File baseMap= new File("E:\\temp\\baseMap.png");
        //底图
        BufferedImage ImageOne = ImageIO.read(baseMap);
        int width = ImageOne.getWidth();//图片宽度
        int height = ImageOne.getHeight();//图片高度
        //从图片中读取RGB
        int[] ImageArrayOne = new int[width*height];
        ImageArrayOne = ImageOne.getRGB(0,0,width,height,ImageArrayOne,0,width);
        //二维码
        BufferedImage ImageTwo = ImageIO.read(destFile);
        int widthTwo = ImageTwo.getWidth();//图片宽度
        int heightTwo = ImageTwo.getHeight();//图片高度
        //从图片中地区RGB
        int[] ImageArrayTwo = new int[widthTwo*heightTwo];
        ImageArrayTwo = ImageTwo.getRGB(0,0,widthTwo,heightTwo,ImageArrayTwo,0,widthTwo);

        //生成新图片
        BufferedImage ImageNew = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        ImageNew.setRGB(0,0,width,height,ImageArrayOne,0,width);//设置左半部分的RGB
        //进行设置叠加坐标
        ImageNew.setRGB(43,617,widthTwo,heightTwo,ImageArrayTwo,0,widthTwo);//设置右半部分的RGB
        //添加文件夹
        //生成图片路径
        String path = "E:\\temp\\"+StringUtil.generateShortUuid()+".png";
        File outFile = new File(path);
        ImageIO.write(ImageNew, "png", outFile);//写图片
    }*/

}