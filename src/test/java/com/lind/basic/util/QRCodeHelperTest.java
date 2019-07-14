package com.lind.basic.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import org.junit.Test;

public class QRCodeHelperTest {

  @Test
  public void bgQrCode() throws Exception {
    QRCodeHelper.generateBackgroundQRCode("src/test/resources/image/bg.png", "http://www.baidu.com", 100, 100,
        "png", 10, 100, "src/test/resources/image/bg2.png");
  }

  @Test
  public void bgQrCodeByteArray() throws Exception {
    String outFilePath = "src/test/resources/image/bg2.png";
    File file = new File(outFilePath);
    FileOutputStream fos = new FileOutputStream(file);
    BufferedOutputStream bos = new BufferedOutputStream(fos);
    bos.write(QRCodeHelper.generateBackgroundQRCode(
        "src/test/resources/image/bg.png",
        "http://www.baidu.com",
        95, 95, "png", 548, 1068));
  }

  @Test
  public void bgUriQrCodeByteArray() throws Exception {
    String outFilePath = "src/test/resources/image/bg2.png";
    File file = new File(outFilePath);
    FileOutputStream fos = new FileOutputStream(file);
    BufferedOutputStream bos = new BufferedOutputStream(fos);
    bos.write(QRCodeHelper.generateBackgroundQRCode(
        QRCodeHelper.QrCodeParamter.builder()
            .bgImageUrl(new URL("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548754218376&di=1bf9f04337d55088ac17485cc7d4432c&imgtype=0&src=http%3A%2F%2Fxa.mobiletrain.org%2Fd%2Ffile%2Fnews%2F2018-02-01%2Fac4bc47285b2f1fe580f0ae78838eaed.jpg"))
            .content("http://www.baidu.com")
            .coordinateX(0)
            .coordinateY(0)
            .width(95)
            .height(95)
            .format("png")
            .build()));
  }
}
