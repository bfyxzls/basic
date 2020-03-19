package com.lind.basic.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * 二维码生成工具.
 * 两种类型的方法，带背景二维码和单纯二维码.
 */
@Slf4j
public class QRCodeHelper extends LuminanceSource {

  /**
   * 二维码颜色.
   */
  private static final int BLACK = 0xFF000000;
  /**
   * 二维码颜色.
   */
  private static final int WHITE = 0xFFFFFFFF;
  private static int border = 1;
  private final BufferedImage image;
  private final int left;
  private final int top;

  /**
   * 构造器.
   */
  public QRCodeHelper(BufferedImage image) {
    this(image, 0, 0, image.getWidth(), image.getHeight());
  }


  /**
   * 构造器.
   */
  public QRCodeHelper(BufferedImage image, int left, int top, int width, int height) {
    super(width, height);
    int sourceWidth = image.getWidth();
    int sourceHeight = image.getHeight();
    if (left + width > sourceWidth || top + height > sourceHeight) {
      throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
    }
    for (int y = top; y < top + height; y++) {
      for (int x = left; x < left + width; x++) {
        if ((image.getRGB(x, y) & 0xFF000000) == 0) {
          // = white
          image.setRGB(x, y, 0xFFFFFFFF);
        }
      }
    }
    this.image = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_BYTE_GRAY);
    this.image.getGraphics().drawImage(image, 0, 0, null);
    this.left = left;
    this.top = top;
  }

  private static BufferedImage toBufferedImage(BitMatrix matrix) {
    int width = matrix.getWidth();
    int height = matrix.getHeight();
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
      }
    }
    return image;
  }

  /**
   * 生成二维码图片.
   */
  private static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
    BufferedImage image = toBufferedImage(matrix);
    if (!ImageIO.write(image, format, file)) {
      throw new IOException("Could not write an image of format " + format + " to " + file);
    }
  }

  /**
   * 生成二维码图片流.
   */
  private static void writeToStream(BitMatrix matrix, String format, OutputStream stream)
      throws IOException {
    BufferedImage image = toBufferedImage(matrix);
    if (!ImageIO.write(image, format, stream)) {
      throw new IOException("Could not write an image of format " + format);
    }
  }

  /**
   * 生成二维码字节数组.
   */
  public static byte[] generateQRCode(String text, int width, int height, String format)
      throws Exception {
    Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
    // 指定编码格式
    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
    // 指定纠错等级
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
    // 白边大小，取值范围0~4
    hints.put(EncodeHintType.MARGIN, border);
    BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE,
        width, height, hints);
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    byte[] bytes = null;
    try {
      writeToStream(bitMatrix, format, out);
      bytes = out.toByteArray();
    } finally {
      out.close();
    }
    return bytes;
  }

  /**
   * 根据内容，生成指定宽高、指定格式的二维码图片.
   *
   * @param text   内容
   * @param width  宽
   * @param height 高
   * @param format 图片格式
   * @return 生成的二维码图片路径
   */
  public static String generateQRCode(String text, int width, int height,
                                      String format, String pathName) throws Exception {
    Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
    // 指定编码格式
    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
    // 指定纠错等级
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
    // 白边大小，取值范围0~4
    hints.put(EncodeHintType.MARGIN, border);
    BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE,
        width, height, hints);
    File outputFile = new File(pathName);
    writeToFile(bitMatrix, format, outputFile);
    return pathName;
  }

  /**
   * 输出二维码图片流.
   *
   * @param text     二维码内容
   * @param width    二维码
   * @param height   二维码高
   * @param format   图片格式eg: png, jpg, gif
   * @param response HttpServletResponse
   */
  public static void generateQRCode(String text, int width, int height, String format,
                                    HttpServletResponse response) throws Exception {
    Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
    // 指定编码格式
    hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
    // 指定纠错等级
    hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
    // 白边大小，取值范围0~4
    hints.put(EncodeHintType.MARGIN, border);
    BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE,
        width, height, hints);
    writeToStream(bitMatrix, format, response.getOutputStream());
  }

  private static BitMatrix deleteWhite(BitMatrix matrix) {
    int[] rec = matrix.getEnclosingRectangle();
    int resWidth = rec[2] + 1;
    int resHeight = rec[3] + 1;

    BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
    resMatrix.clear();
    for (int i = 0; i < resWidth; i++) {
      for (int j = 0; j < resHeight; j++) {
        if (matrix.get(i + rec[0], j + rec[1])) {
          resMatrix.set(i, j);
        }
      }
    }
    return resMatrix;
  }

  /**
   * 解析指定路径下的二维码图片.
   *
   * @param filePath 二维码图片路径
   * @return
   */
  private static String parseQRCode(String filePath) {
    String content = "";
    try {
      File file = new File(filePath);
      BufferedImage image = ImageIO.read(file);
      LuminanceSource source = new QRCodeHelper(image);
      Binarizer binarizer = new HybridBinarizer(source);
      BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
      Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();
      hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
      MultiFormatReader formatReader = new MultiFormatReader();
      Result result = formatReader.decode(binaryBitmap, hints);
      // 设置返回值
      content = result.getText();
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return content;
  }

  private static BufferedImage watermark(File file, InputStream waterFile,
                                         int x, int y, float alpha) throws IOException {
    BufferedImage buffImg = ImageIO.read(file);

    // 获取层图
    BufferedImage waterImg = ImageIO.read(waterFile);

    // 创建Graphics2D对象，用在底图对象上绘图
    Graphics2D g2d = buffImg.createGraphics();
    int waterImgWidth = waterImg.getWidth();// 获取层图的宽度
    int waterImgHeight = waterImg.getHeight();// 获取层图的高度
    // 在图形和图像中实现混合和透明效果
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
    // 绘制
    g2d.drawImage(waterImg, x, y, waterImgWidth, waterImgHeight, null);
    g2d.dispose();// 释放图形上下文使用的系统资源
    return buffImg;
  }

  /**
   * 水印. 
   */
  private static BufferedImage watermark(byte[] bgfile, InputStream waterFile,
                                         int x, int y, float alpha) throws IOException {
    BufferedImage buffImg = ImageIO.read(new ByteArrayInputStream(bgfile));

    // 获取层图
    BufferedImage waterImg = ImageIO.read(waterFile);

    // 创建Graphics2D对象，用在底图对象上绘图
    Graphics2D g2d = buffImg.createGraphics();
    int waterImgWidth = waterImg.getWidth();// 获取层图的宽度
    int waterImgHeight = waterImg.getHeight();// 获取层图的高度
    // 在图形和图像中实现混合和透明效果
    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
    // 绘制
    g2d.drawImage(waterImg, x, y, waterImgWidth, waterImgHeight, null);
    g2d.dispose();// 释放图形上下文使用的系统资源
    return buffImg;
  }

  /**
   * 水印. 
   */
  private static BufferedImage watermark(File file, File waterFile, int x, int y, float alpha)
      throws IOException {
    InputStream input = new FileInputStream(waterFile);

    return watermark(file, input, x, y, alpha);
  }

  /**
   * 输出水印图片.
   */
  private static void generateWaterFile(BufferedImage buffImg, String savePath) {
    int temp = savePath.lastIndexOf(".") + 1;
    try {

      ImageIO.write(buffImg, savePath.substring(temp), new File(savePath));

    } catch (IOException e1) {

      e1.printStackTrace();
    }
  }

  /**
   * 输出水印图片.
   */
  private static void generateWaterStream(
      BufferedImage buffImg, String format, OutputStream outputStream) {
    try {

      ImageIO.write(buffImg, format, outputStream);

    } catch (IOException e1) {

      e1.printStackTrace();
    }
  }

  /**
   * 保存背景图的二维码.
   *
   * @param bgImagePath .
   * @param x           .
   * @param y           .
   */
  public static void generateBackgroundQRCode(
      String bgImagePath, String text, int width, int height,
      String format, int x, int y, String outputFile) throws Exception {
    // 把字节数组读到输入流
    InputStream input = new ByteArrayInputStream(generateQRCode(text, width, height, format));
    // 水印
    BufferedImage buffImg = watermark(new File(bgImagePath), input, x, y, 1.0f);
    // 保存水印图
    generateWaterFile(buffImg, outputFile);
  }

  /**
   * 保存背景图的二维码.
   *
   * @param bgImagePath .
   * @param x           .
   * @param y           .
   */
  public static byte[] generateBackgroundQRCode(
      String bgImagePath, String text, int width, int height,
      String format, int x, int y) throws Exception {
    // 把字节数组读到输入流
    InputStream input = new ByteArrayInputStream(generateQRCode(text, width, height, format));
    // 水印
    BufferedImage buffImg = watermark(new File(bgImagePath), input, x, y, 1.0f);
    // 保存水印图
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    generateWaterStream(buffImg, format, outputStream);
    return outputStream.toByteArray();
  }

  /**
   * 保存URI背景图的二维码.
   */
  public static byte[] generateBackgroundQRCode(QrCodeParamter qrCodeParamter) throws Exception {
    border = qrCodeParamter.getBorder();
    // 把字节数组读到输入流
    InputStream input = new ByteArrayInputStream(
        generateQRCode(qrCodeParamter.getContent(), qrCodeParamter.getWidth(),
            qrCodeParamter.getHeight(), qrCodeParamter.getFormat()));
    // 水印
    //new一个URL对象
    //打开链接
    HttpURLConnection conn = (HttpURLConnection) qrCodeParamter.getBgImageUrl().openConnection();
    //设置请求方式为"GET"
    conn.setRequestMethod("GET");
    //超时响应时间为5秒
    conn.setConnectTimeout(5 * 1000);
    //通过输入流获取图片数据
    InputStream inStream = conn.getInputStream();
    //得到图片的二进制数据，以二进制封装得到数据，具有通用性
    byte[] data = readInputStream(inStream);

    BufferedImage buffImg =
        watermark(data, input,
            qrCodeParamter.getCoordinateX(), qrCodeParamter.getCoordinateY(), 1.0f);
    // 保存水印图
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    generateWaterStream(buffImg, qrCodeParamter.getFormat(), outputStream);
    return outputStream.toByteArray();
  }

  private static byte[] readInputStream(InputStream inStream) throws Exception {
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    //创建一个Buffer字符串
    byte[] buffer = new byte[1024];
    //每次读取的字符串长度，如果为-1，代表全部读取完毕
    int len = 0;
    //使用一个输入流从buffer里把数据读取出来
    while ((len = inStream.read(buffer)) != -1) {
      //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
      outStream.write(buffer, 0, len);
    }
    //关闭输入流
    inStream.close();
    //把outStream里的数据写入内存
    return outStream.toByteArray();
  }

  @Override
  public byte[] getRow(int y, byte[] row) {
    if (y < 0 || y >= getHeight()) {
      throw new IllegalArgumentException("Requested row is outside the image: " + y);
    }
    int width = getWidth();
    if (row == null || row.length < width) {
      row = new byte[width];
    }
    image.getRaster().getDataElements(left, top + y, width, 1, row);
    return row;
  }

  @Override
  public byte[] getMatrix() {
    int width = getWidth();
    int height = getHeight();
    int area = width * height;
    byte[] matrix = new byte[area];
    image.getRaster().getDataElements(left, top, width, height, matrix);
    return matrix;
  }

  @Override
  public boolean isCropSupported() {
    return true;
  }

  @Override
  public LuminanceSource crop(int left, int top, int width, int height) {
    return new QRCodeHelper(image, this.left + left, this.top + top, width, height);
  }

  @Override
  public boolean isRotateSupported() {
    return true;
  }

  @Override
  public LuminanceSource rotateCounterClockwise() {
    int sourceWidth = image.getWidth();
    int sourceHeight = image.getHeight();
    AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0, 0.0, sourceWidth);
    BufferedImage rotatedImage = new BufferedImage(sourceHeight, sourceWidth,
        BufferedImage.TYPE_BYTE_GRAY);
    Graphics2D g = rotatedImage.createGraphics();
    g.drawImage(image, transform, null);
    g.dispose();
    int width = getWidth();
    return new QRCodeHelper(rotatedImage, top, sourceWidth - (left + width), getHeight(), width);
  }

  /**
   * 构建二维码参数.
   */
  @Getter
  @Builder(toBuilder = true)
  public static class QrCodeParamter {
    /**
     * 识别二维码的链接.
     */
    private String content;
    /**
     * 宽度.
     */
    private int width;
    /**
     * 高度.
     */
    private int height;
    /**
     * 图片格式.
     */
    private String format;
    /**
     * 横坐标.
     */
    private int coordinateX;
    /**
     * 纵坐标.
     */
    private int coordinateY;
    /**
     * 背景图的url.
     */
    private URL bgImageUrl;
    /**
     * 边框0~4.
     */
    private int border;
  }

}
