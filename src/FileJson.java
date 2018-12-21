import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entity.Song;
import entity.Tag;
import handler.Handler;
import tool.GetMusicInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.LinkedList;

/**
 * @author 李高丞
 * @version 1.0 Beta
 */
public class FileJson {

    public static void main(String[] args) throws Exception {
//        Handler.analysisJson("宝贝");
//        File file = new File("C:\\Users\\hasee\\Downloads\\tool.ExcelTool");
//        LinkedList<Song> songs = new LinkedList<>();
//
//        loopFolder(file, songs);
//
//        // todo 生成 json 文件
//        Gson gson = new Gson();
//        FileOutputStream fos = new FileOutputStream("C:\\Users\\hasee\\Downloads\\tool.ExcelTool\\tool.ExcelTool.json");
//
//        String temp = gson.toJson(songs);
//        fos.write(temp.getBytes());
//        fos.close();


//         //todo 解析 json 文件
//        JsonParser parser = new JsonParser();
//        JsonArray array = parser.parse(new FileReader("C:\\Users\\hasee\\Downloads\\tool.ExcelTool\\tool.ExcelTool.json")).getAsJsonArray();
//
//        for (int i = 0; i < array.size(); i++) {
//            System.out.println("--------------------");
//            JsonObject subObject = array.get(i).getAsJsonObject();
//            System.out.println(subObject.get("tag").getAsJsonObject().get("songName").getAsString());
//            System.out.println(subObject.get("tag").getAsJsonObject().get("artist"));
//            System.out.println(subObject.get("path").getAsString());
//        }
    }

    private static void loopFolder(File files, LinkedList<Song> songList) {
        for (File file : files.listFiles()) {
            if (file.isDirectory())
                loopFolder(file, songList);
            if (file.isFile()) {
                String name = file.getName();
                Song song = new Song();

                if (name.matches(".*(?i)mp3$") || name.matches(".*(?i)flac$")) {
                    song.setTag(GetMusicInfo.MP3Info(file.getAbsolutePath()));
                    song.setPath(file.getAbsolutePath());
                    songList.add(song);
                }
            }
        }
    }

}
