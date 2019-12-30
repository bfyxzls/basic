package com.lind.basic.poi;

import com.lind.basic.BaseTest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.converter.core.BasicURIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.VerticalAlign;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

public class WordTest extends BaseTest {

  /**
   * 获取项目根路径
   *
   * @return
   */
  private static String getResourceBasePath() {
    // 获取跟目录
    File path = null;
    try {
      path = new File(ResourceUtils.getURL("classpath:").getPath());
    } catch (FileNotFoundException e) {
      // nothing to do
    }
    if (path == null || !path.exists()) {
      path = new File("");
    }

    String pathStr = path.getAbsolutePath();
    // 如果是在eclipse中运行，则和target同级目录,如果是jar部署到服务器，则默认和jar包同级
    pathStr = pathStr.replace("\\target\\classes", "");

    return pathStr;
  }


  @Test
  public void docxToHtml() throws Exception {
    String path = getResourceBasePath();
    String imagePath = path + "\\static\\image";
    String sourceFileName = path + "\\static\\fontstyle.docx";
    String targetFileName = path + "\\static\\fontstyle.html";

    OutputStreamWriter outputStreamWriter = null;
    try {
      XWPFDocument document = new XWPFDocument(new FileInputStream(sourceFileName));
      XHTMLOptions options = XHTMLOptions.create();
      // 存放图片的文件夹
      options.setExtractor(new FileImageExtractor(new File(imagePath)));
      // html中图片的路径
      options.URIResolver(new BasicURIResolver("image"));
      outputStreamWriter = new OutputStreamWriter(new FileOutputStream(targetFileName), "utf-8");
      XHTMLConverter xhtmlConverter;
      xhtmlConverter = (XHTMLConverter) XHTMLConverter.getInstance();
      xhtmlConverter.convert(document, outputStreamWriter, options);
    } catch (Exception ex) {
      ex.getStackTrace();
    } finally {
      if (outputStreamWriter != null) {
        outputStreamWriter.close();
      }
    }
  }

  @Test
  public void writeFile() throws IOException {
    //Blank Document
    XWPFDocument document = new XWPFDocument();

    //Write the Document in file system
    FileOutputStream out = new FileOutputStream(getResource("fontstyle.docx"));

    //create paragraph
    XWPFParagraph paragraph = document.createParagraph();

    //Set Bold an Italic
    XWPFRun paragraphOneRunOne = paragraph.createRun();
    paragraphOneRunOne.setBold(true);
    paragraphOneRunOne.setItalic(true);
    paragraphOneRunOne.setText("Font Style");
    paragraphOneRunOne.addBreak();

    //Set text Position
    XWPFRun paragraphOneRunTwo = paragraph.createRun();
    paragraphOneRunTwo.setText("Font Style two");
    paragraphOneRunTwo.setTextPosition(100);

    //Set Strike through and Font Size and Subscript
    XWPFRun paragraphOneRunThree = paragraph.createRun();
    paragraphOneRunThree.setStrike(true);
    paragraphOneRunThree.setFontSize(20);
    paragraphOneRunThree.setSubscript(VerticalAlign.SUBSCRIPT);
    paragraphOneRunThree.setText(" Different Font Styles");

    document.write(out);
    out.close();
    System.out.println("fontstyle.docx written successully");
  }

  @Test
  public void readFile() throws IOException, OpenXML4JException, XmlException {
    ClassLoader classLoader = BaseTest.class.getClassLoader();
    OPCPackage opcPackage = POIXMLDocument.openPackage(classLoader.getResource("fontstyle.docx").getPath());
    POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
    String resullt = extractor.getText();
    System.out.println(resullt);
    extractor.close();
  }
}
