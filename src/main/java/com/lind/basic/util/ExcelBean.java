package com.lind.basic.util;

import java.io.Serializable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

/**
 * 导入导出excel.
 */
public class ExcelBean implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 列头（标题）名.
   */
  private String headTextName;

  /**
   * 对应字段名.
   */
  private String propertyName;

  /**
   * 合并单元格数.
   */
  private Integer cols = 0;

  /**
   * 样式不被序列化.
   */
  private transient XSSFCellStyle cellStyle;

  /**
   * init.
   *
   * @param headTextName .
   * @param propertyName .
   */
  public ExcelBean(String headTextName, String propertyName) {
    this.headTextName = headTextName;
    this.propertyName = propertyName;
  }

  /**
   * init.
   *
   * @param headTextName .
   * @param propertyName .
   * @param cols         .
   */
  public ExcelBean(String headTextName, String propertyName, Integer cols) {
    super();
    this.headTextName = headTextName;
    this.propertyName = propertyName;
    this.cols = cols;
  }

  public String getHeadTextName() {
    return headTextName;
  }

  public void setHeadTextName(String headTextName) {
    this.headTextName = headTextName;
  }

  public String getPropertyName() {
    return propertyName;
  }

  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }

  public Integer getCols() {
    return cols;
  }

  public void setCols(Integer cols) {
    this.cols = cols;
  }

  public XSSFCellStyle getCellStyle() {
    return cellStyle;
  }

  public void setCellStyle(XSSFCellStyle cellStyle) {
    this.cellStyle = cellStyle;
  }
}
