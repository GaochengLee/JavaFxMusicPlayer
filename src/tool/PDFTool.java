/*
 * Copyright (c) 2018. 18-12-15 下午8:49.
 * @author 李高丞
 */

package tool;

import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import entity.ExtendedInfo;
import entity.Music;
import entity.Song;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 这个类是为了导出 PDF 文档设计的
 *
 * @author 李高丞
 * @version 1.0 Beta
 */
public class PDFTool {
    // https://yq.aliyun.com/ziliao/395204
    /**
     * 设置字体
     */
    private static Font font;

    /**
     * 创建 PDF
     *
     * @param music 要输出的音乐
     * @throws Exception
     */
    public static void CreatePDF(Music music) throws Exception {
        //音乐的扩展信息
        ExtendedInfo info = GetMusicInfo.getExtendedInfo(music.getPath());

        // 音乐图片的存放位置
        File imageFile = new File(music.getImagePath());
        Image image = Image.getInstance(imageFile.toURI().toString());
        // 图片的大小
        image.scaleAbsolute(300, 300);
        // 设置图片居中
        image.setAlignment(Image.ALIGN_CENTER);

        // 设置中文字体
        BaseFont font = BaseFont.createFont("/resource/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        // 设置 PDF 中的字体 
        PDFTool.font = new Font(font, 20, Font.BOLD);
        // 打开一个 PDF 文档
        Document document = new Document();
        // 声明一个 PDF 写入器 并添加保存位置
        PdfWriter.getInstance(document, new FileOutputStream("D:\\" + music.getMusicTitle() + ".pdf"));

        // 打开文件流
        document.open();

        // 创建 PDF 内的一个表
        PdfPTable table;
        // 创建 PDF 内的一个段落
        Paragraph paragraph = new Paragraph(music.getMusicTitle(), new Font(font, 36, Font.BOLD));
        // 设置段落居中
        paragraph.setAlignment(Element.ALIGN_CENTER);

        // 创建 PDF 内的一个空行
        Paragraph blankRow = new Paragraph(36f, " ", new Font(font));


        // 建立一个两列的表
        table = new PdfPTable(2);

        // 向表里填充元素
        table.addCell(getCell("歌名", 1, 1));
        table.addCell(getCell(music.getMusicTitle(), 1, 1));
        table.addCell(getCell("歌手", 1, 1));
        table.addCell(getCell(music.getMusicSinger(), 1, 1));
        table.addCell(getCell("专辑名字", 1, 1));
        table.addCell(getCell(music.getAlbumName(), 1, 1));
        table.addCell(getCell("歌曲时长", 1, 1));
        table.addCell(getCell(music.getMusicTimeLength(), 1, 1));
        table.addCell(getCell("比特率", 1, 1));
        table.addCell(getCell(info.getBitRate(), 1, 1));
        table.addCell(getCell("声道", 1, 1));
        table.addCell(getCell(info.getChannels(), 1, 1));
        table.addCell(getCell("采样率", 1, 1));
        table.addCell(getCell(info.getSampleRate(), 1, 1));
        table.addCell(getCell("文件格式", 1, 1));
        table.addCell(getCell(info.getEncodingType(), 1, 1));
        table.addCell(getCell("压缩方式", 1, 1));
        table.addCell(getCell(info.getFormat(), 1, 1));
        table.addCell(getCell("文件大小", 1, 1));
        table.addCell(getCell(info.getSize(), 1, 1));
        table.addCell(getCell("音乐类型", 1, 1));
        table.addCell(getCell(info.getGenre(), 1, 1));
        table.addCell(getCell("版权", 1, 1));
        table.addCell(getCell(info.getCopyright(), 1, 1));

        // 设置表的宽度
        table.setWidthPercentage(110);

        // 添加一个段落
        document.add(paragraph);
        // 添加空行
        document.add(blankRow);
        // 添加图片
        document.add(image);
        // 添加空行
        document.add(blankRow);
        // 添加表
        document.add(table);
        // 关闭文件流
        document.close();
    }

    /**
     * 获得一个 PDF 元素
     *
     * @param cellValue 元素值
     * @param colSpan
     * @param rowSpan
     * @return 返回一个元素
     */
    private static PdfPCell getCell(String cellValue, int colSpan, int rowSpan) {
        // 创建一个 PDF 元素
        PdfPCell cell = new PdfPCell();
        try {
            // 新建一个元素
            cell = new PdfPCell(new Phrase(cellValue, font));
            cell.setRowspan(rowSpan);
            cell.setColspan(colSpan);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cell;
    }
}
