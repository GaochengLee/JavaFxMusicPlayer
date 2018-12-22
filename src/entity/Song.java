/*
 * Copyright (c) 2018. 18-12-10 下午8:14.
 * @author 李高丞
 */

package entity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import tool.GetMusicInfo;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 保存在硬盘的歌曲的实体类
 *
 * @author 李高丞
 * @version 1.0 Beta
 */
public class Song {

    /**
     * 歌曲的标签
     */
    private Tag tag;
    /**
     * 歌曲的保存路径
     */
    private String path;
    /**
     * 歌曲的大小
     */
    private String length;

    /*
     * get 方法和 set 方法
     * 用于获得和修改 Song 的属性
     */

    /**
     * 获取存放在硬盘里歌曲的标签信息
     */
    public Tag getTag() {
        if (tag == null)
            tag = new Tag();
        return tag;
    }

    /**
     * 设置存放在硬盘的歌曲的标签信息
     */
    public void setTag(Tag tag) {
        this.tag = tag;
    }

    /**
     * 获取存放在硬盘里的歌曲的存放路径
     *
     * @return 歌曲的存放路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置存放在硬盘里的歌曲的存放路径
     *
     * @param path 歌曲的存放路径
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获得存放在硬盘里的歌曲的播放长度
     *
     * @return 歌曲播放长度
     */
    public String getLength() {
        return length;
    }

    /**
     * 设置存放在硬盘里的歌曲的长度
     *
     * @param length 歌曲长度
     */
    public void setLength(String length) {
        this.length = length;
    }

    /**
     * 静态方法
     * 添加一个新的歌曲，根据文件保存的路径
     *
     * @param path 文件保存的路径
     * @return 一个实体的 Song 类
     */
    public static Song addSong(String path) {
        Song song = new Song();

        // 设置 Song 的路径
        song.setPath(path);

        Tag tag;

        // 如果是 MP3 文件
        if (path.endsWith(".mp3")) {
            // 获取 MP3 的标签信息
            tag = GetMusicInfo.MP3Info(path);
            song.setTag(tag);
        }

        return song;
    }

    /**
     * 静态方法
     * 将硬盘中的实体 Song 类转化为播放器里的歌曲实体类 Music
     *
     * @param song 需要转化的 song 类
     * @return 播放器的歌曲实体类 music
     */
    public static Music songToMusic(Song song) {
        // 新建引用对象
        Music music = new Music();

        // 信息转化
        music.setAlbumName(song.getTag().getAlbum());
        music.setMusicSinger(song.getTag().getArtist());
        music.setMusicTimeLength(song.getTag().getLength());
        music.setMusicTitle(song.getTag().getSongName());
        music.setPath(song.getPath());

        return music;
    }

    public static void songToJson(List<Song> songList) {
        Gson gson = new Gson();

        try {
            FileOutputStream fos = new FileOutputStream("D:\\LocalSong.json", false);
            String temp = gson.toJson(songList);
            fos.write(temp.getBytes());
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static LinkedList<Song> jsonToSong() {
        LinkedList<Song> songList = new LinkedList<>();

        try {
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(new FileReader("D:\\LocalSong.json")).getAsJsonArray();

            for (int i = 0; i < array.size(); i++) {
                Song song = new Song();
                Tag tag = new Tag();

                JsonObject object = array.get(i).getAsJsonObject();

                // 设置标签信息
                tag.setSongName(object.get("tag").getAsJsonObject().get("songName").getAsString());
                tag.setArtist(object.get("tag").getAsJsonObject().get("artist").getAsString());
                tag.setAlbum(object.get("tag").getAsJsonObject().get("album").getAsString());
                tag.setLength(object.get("tag").getAsJsonObject().get("length").getAsString());
                // 设置标签
                song.setTag(tag);
                song.setPath(object.get("path").getAsString());

                songList.add(song);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return songList;
    }

    /**
     * 重写了 toString 方法用于格式化输出数据
     *
     * @return 格式化输出的字符串
     */
    @Override
    public String toString() {
        return "Song [tag=" + tag + ", path=" + path + ", length=" + length + "]";
    }

    /**
     * 重写了 equals 方法用于比较
     *
     * @param obj 要比较的对象
     * @return true 说明是同一歌曲
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Song) {
            Song song = (Song) obj;
            return this.path.equals(song.path);
        }
        return false;
    }
}
