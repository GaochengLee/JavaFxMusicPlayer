/*
 * Copyright (c) 2018. 18-12-15 下午8:49.
 * @author 李高丞
 */

package tool;/*
 * Copyright (c) 2018. 18-12-14 下午9:43.
 * @author 李高丞
 */


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
 * @author 李高丞
 * @version 1.0 Beta
 */
public class PDFTool {
    // https://yq.aliyun.com/ziliao/395204
    private static Font font;

    public static void CreatePDF(Music music) throws Exception {
        ExtendedInfo info = GetMusicInfo.getExtendedInfo(music.getPath());

        File imageFile = new File(music.getImagePath());
        Image image = Image.getInstance(imageFile.toURI().toString());
        image.scaleAbsolute(300, 300);
        image.setAlignment(Image.ALIGN_CENTER);


        BaseFont font = BaseFont.createFont("/resource/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        PDFTool.font = new Font(font, 20, Font.BOLD);
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("D:\\" + music.getMusicTitle() + ".pdf"));

        document.open();

        PdfPTable table;
        Paragraph paragraph = new Paragraph(music.getMusicTitle(), new Font(font, 36, Font.BOLD));
        paragraph.setAlignment(Element.ALIGN_CENTER);

        Paragraph blankRow = new Paragraph(36f, " ", new Font(font));

        document.add(paragraph);
        document.add(blankRow);
        document.add(image);
        document.add(blankRow);


        table = new PdfPTable(2);


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

        table.setWidthPercentage(110);


        document.add(table);
        document.close();
    }

    private static PdfPCell getCell(String cellValue, int colSpan, int rowSpan) {
        PdfPCell cell = new PdfPCell();
        try {
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
