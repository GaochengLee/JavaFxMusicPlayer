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
 * @author 李高丞
 * @version 1.0 Beta
 */
public class ExcelTool {

    public static void CreateMsg(Music music) throws Exception {
        WritableWorkbook book = Workbook.createWorkbook(new File("D:\\" + music.getMusicTitle() + ".xls"));

        WritableSheet sheet = book.createSheet("First", 0);

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

        MusicToExcel(sheet, music);
        book.write();
        book.close();

    }

    private static void MusicToExcel(WritableSheet sheet, Music music) throws Exception {
        ExtendedInfo info = GetMusicInfo.getExtendedInfo(music.getPath());

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

        WritableImage image = new WritableImage(2, 12, 1, 1, new File(music.getImagePath()));

        sheet.setColumnView(2, 30);
        sheet.setRowView(12, 3000, false);
        sheet.addImage(image);
    }

    private static void addLabel(WritableSheet sheet, int col, int row, String text) throws Exception {
        Label label = new Label(col, row, text);
        sheet.addCell(label);
    }
}
