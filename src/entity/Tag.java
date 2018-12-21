/*
 * Copyright (c) 2018. 18-12-10 下午8:14.
 * @author 李高丞
 */

package entity;

/**
 * 硬盘里的歌曲的标签信息实体类
 *
 * @author 李高丞
 * @version 1.0 Beta
 */
public class Tag {
    /**
     * 歌曲名字
     */
    private String songName;
    /**
     * 歌手
     */
    private String artist;
    /**
     * 专辑
     */
    private String album;
    /**
     * 歌曲时长
     */
    private String length;

    /*
     * get 方法和 set 方法
     * 用于获得或修改标签类的信息
     */

    /**
     * 获取标签信息的歌名
     */
    public String getSongName() {
        return songName;
    }

    /**
     * 设置标签信息的歌名
     */
    public void setSongName(String songName) {
        this.songName = songName;
    }

    /**
     * 获取标签信息的歌手
     */
    public String getArtist() {
        return artist;
    }

    /**
     * 设置标签信息的歌手
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * 获取标签信息的专辑名字
     */
    public String getAlbum() {
        return album;
    }

    /**
     * 设置标签信息的专辑名字
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     * 获取标签信息的播放长度
     */
    public String getLength() {
        return length;
    }

    /**
     * 设置标签信息的播放长度
     */
    public void setLength(String length) {
        this.length = length;
    }

    /**
     * 标签类的构造器
     */
    public Tag() {
        songName = "空";
        artist = "空";
        album = "空";
        length = "空";

    }

    /**
     * 重写了 toString 方法，用于格式化输出字符串
     *
     * @return 一个格式化了的字符串
     */
    @Override
    public String toString() {
        return "Tag [songName=" + songName + ", artist=" + artist + ", album=" + album + ", length=" + length + "]";
    }
}
