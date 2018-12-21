/*
 * Copyright (c) 2018. 18-12-15 下午8:49.
 * @author 李高丞
 */

package tool;

import entity.ExtendedInfo;
import entity.Music;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;

/**
 * 这个类是为了导出 Excel 设计的
 *
 * @author 李高丞
 * @version 1.0 Beta
 */
public class ExcelTool {

    /**
     * 生成 Excel 信息
     *
     * @param music 要导出的音乐
     * @throws Exception
     */
    public static void CreateMsg(Music music) throws Exception {
        // 生成一的 Excel 表，并指明它的输出路径
        WritableWorkbook book = Workbook.createWorkbook(new File("D:\\" + music.getMusicTitle() + ".xls"));

        // 创建表的第一页
        WritableSheet sheet = book.createSheet("First", 0);

        // 向表里添加标签信息
        addLabel(sheet, 0, 0, "SongName");
        addLabel(sheet, 0, 1, "Singer");
        addLabel(sheet, 0, 2, "Album");
        addLabel(sheet, 0, 3, "TimeLength");
        addLabel(sheet, 0, 4, "BitRate");
        addLabel(sheet, 0, 5, "Channels");
        addLabel(sheet, 0, 6, "SampleRate");
        addLabel(sheet, 0, 7, "EncodingType");
        addLabel(sheet, 0, 8, "Format");
        addLabel(sheet, 0, 9, "Size");
        addLabel(sheet, 0, 10, "Genre");
        addLabel(sheet, 0, 11, "Copyright");

        // 将音乐类转化为表里的标签
        MusicToExcel(sheet, music);
        // 写入信息到 Excel 文档中
        book.write();
        // 关闭文档输入流
        book.close();

    }

    /**
     * 将 Music 类转化为 Excel 中的标签
     *
     * @param sheet Excel 中的一页
     * @param music 需要转化的音乐类
     * @throws Exception
     */
    private static void MusicToExcel(WritableSheet sheet, Music music) throws Exception {
        // 获得音乐的扩展信息
        ExtendedInfo info = GetMusicInfo.getExtendedInfo(music.getPath());

        // 向表里添加扩展元素
        addLabel(sheet, 2, 0, music.getMusicTitle());
        addLabel(sheet, 2, 1, music.getMusicSinger());
        addLabel(sheet, 2, 2, music.getAlbumName());
        addLabel(sheet, 2, 3, music.getMusicTimeLength());
        addLabel(sheet, 2, 4, info.getBitRate());
        addLabel(sheet, 2, 5, info.getChannels());
        addLabel(sheet, 2, 6, info.getSampleRate());
        addLabel(sheet, 2, 7, info.getEncodingType());
        addLabel(sheet, 2, 8, info.getFormat());
        addLabel(sheet, 2, 9, info.getSize());
        addLabel(sheet, 2, 10, info.getGenre());
        addLabel(sheet, 2, 11, info.getCopyright());
        addLabel(sheet, 0, 12, "MusicCover");

        // 生成一个表图片
        WritableImage image = new WritableImage(2, 12, 1, 1, new File(music.getImagePath()));

        // 设置图片列位置和列大小
        sheet.setColumnView(2, 30);
        // 设置图片行位置和行大小
        sheet.setRowView(12, 3000, false);
        // 向表里写入图片
        sheet.addImage(image);
    }

    /**
     * 向一页里添加标签
     *
     * @param sheet Excel 里的一页
     * @param col   行
     * @param row   列
     * @param text  信息
     * @throws Exception
     */
    private static void addLabel(WritableSheet sheet, int col, int row, String text) throws Exception {
        Label label = new Label(col, row, text);
        sheet.addCell(label);
    }
}
