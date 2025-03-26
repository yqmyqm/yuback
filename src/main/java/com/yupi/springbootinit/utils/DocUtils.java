package com.yupi.springbootinit.utils;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DocUtils {
    /**
     * 获取文本内容
     */
    public static String docToText(MultipartFile multipartFile) {
        StringBuilder result = new StringBuilder();

        try (InputStream inputStream = multipartFile.getInputStream()) {  // 直接获取输入流[1,2](@ref)
            XWPFDocument document = new XWPFDocument(inputStream);

            // 1. 处理段落和列表
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String text = paragraph.getText().trim();
                if (!text.isEmpty()) {
                    // 检测是否为列表项（如项目符号或编号列表）
                    if (paragraph.getNumID() != null) {
                        result.append("• ").append(text).append("\n");
                    } else {
                        result.append(text).append("\n");
                    }
                }
            }

            // 2. 处理表格
            for (XWPFTable table : document.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    StringBuilder rowText = new StringBuilder();
                    for (XWPFTableCell cell : row.getTableCells()) {
                        rowText.append(cell.getText().trim()).append("\t");
                    }
                    if (rowText.length() > 0) {
                        rowText.setLength(rowText.length() - 1);
                        result.append(rowText).append("\n");
                    }
                }
            }

            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "文件读取失败: " + e.getMessage();
        }
    }
}
