package com.lind.basic.word;

import com.aspose.words.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import javax.swing.text.StyledEditorKit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 从world模型提供信息.
 */
@Slf4j
public class ExtractWordTest {
    private static InputStream license;
    private static InputStream fileInput;
    private static File outputFilePdf;
    private static File outputFileWord;

    /**
     * 获取license
     *
     * @return
     */
    public static boolean getLicense(String templateName) {
        boolean result = false;
        try {
            license = new ClassPathResource("license.xml").getInputStream();
            fileInput = new ClassPathResource(templateName).getInputStream();
            License aposeLic = new License();
            aposeLic.setLicense(license);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Test
    public void writeMask() throws Exception {
        // 验证License
        if (!getLicense("templates/contract.docx")) {
            return;
        }
        Document doc = new Document(fileInput);
        String[] flds = new String[]{"title", "money"};
        Object[] vals = new Object[]{"测试", 500};
        doc.getMailMerge().execute(flds, vals); //调用接口
        //主要调用aspose.words的邮件合并接口MailMerge
        //3.1 填充单个文本域
        doc.getMailMerge().execute(flds, vals); //调用接口
        outputFilePdf = new File("d:/1.pdf");
        outputFileWord = new File("d:/exportfile.docx");
        FileOutputStream output = new FileOutputStream(outputFilePdf);
        FileOutputStream outputDoc = new FileOutputStream(outputFileWord);
        doc.save(output, SaveFormat.PDF);
        doc.save(outputDoc, SaveFormat.DOCX);
        output.flush();
        output.close();
    }


    @Test
    public void readMask() throws Exception {
        // 验证License
        if (!getLicense("templates/exportfile.docx")) {
            return;
        }
        Document doc = new Document(fileInput);
        BookmarkCollection  fields = doc.getRange().getBookmarks();
        for(Bookmark field : fields) {
            logger.info("fields:{} = {}", field.getName(),field.getText());
        }

    }

    @Test
    public void writeFile() throws IOException {
        //1创建目标文件
        File desFile = new File("d:/des.txt");
        //2创建字节输出流对象
        FileOutputStream out = new FileOutputStream(desFile);
        out.write("哈哈哈".getBytes());
        //4 关闭文件
        out.close();

    }
}
