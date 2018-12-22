/*
 * Copyright (c) 2018. 18-12-10 下午8:14.
 * @author 李高丞
 */

package entity;

/**
 * 播放器里的歌曲实体类
 * 为什么有了歌曲实体类还要做这一个播放器的歌曲实体类？
 * 因为实际的 song 类存在标签，每次处理播放状态是，
 * 再将其一个一个地转化为数据填充比较麻烦，所以使用这一个类方便管理
 *
 * @author 李高丞
 * @version 1.0 Beta
 */
public class Music {

    /**
     * Music 类的歌曲名
     */
    private String musicTitle;

    /**
     * Music 类的歌手
     */
    private String musicSinger;

    /**
     * Music 类的专辑名
     */
    private String albumName;

    /**
     * Music 类的音乐播放长度
     */
    private String musicTimeLength;

    /**
     * Music 类的音乐存储路径
     */
    private String path;

    /*
     * get 方法和 set 方法
     * 用来获取和修改 Music 类的信息
     */

    /**
     * 获取播放器中的音乐名字
     *
     * @return 音乐名字
     */
    public String getMusicTitle() {
        return musicTitle;
    }

    /**
     * 设置播放器中的音乐名字
     *
     * @param musicTitle 音乐名字
     */
    public void setMusicTitle(String musicTitle) {
        this.musicTitle = musicTitle;
    }

    /**
     * 获得播放器中的歌曲歌手名字
     *
     * @return 歌曲歌手名字
     */
    public String getMusicSinger() {
        return musicSinger;
    }

    /**
     * 设置播放器中的歌曲歌手名字
     *
     * @param musicSinger 歌曲歌手名字
     */
    public void setMusicSinger(String musicSinger) {
        this.musicSinger = musicSinger;
    }

    /**
     * 获得音乐播放器中歌曲的专辑名字
     *
     * @return 歌曲的专辑名字
     */
    public String getAlbumName() {
        return albumName;
    }

    /**
     * 设置音乐播放器中歌曲的专辑名字
     *
     * @param albumName 歌曲的专辑名字
     */
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    /**
     * 获得音乐播放器中的歌曲播放时长
     *
     * @return 歌曲播放时长
     */
    public String getMusicTimeLength() {
        return musicTimeLength;
    }

    /**
     * 设置音乐播放器中歌曲的播放时长
     *
     * @param musicTimeLength 歌曲的播放时长
     */
    public void setMusicTimeLength(String musicTimeLength) {
        this.musicTimeLength = musicTimeLength;
    }

    /**
     * 获取音乐播放器中歌曲的存放路径
     *
     * @return 歌曲的存放路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置音乐播放器中歌曲的存放位置
     *
     * @param path 歌曲的存放位置
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 获取音乐播放器中歌曲封面的存放路径
     *
     * @return 歌曲封面的存放路径
     */
    public String getImagePath() {
        return getPath().substring(0, getPath().length() - 3) + "png";
    }

    public static Song musicToSong(Music music) {
        Song song = new Song();
        Tag tag = new Tag();

        song.setPath(music.getPath());
        tag.setAlbum(music.getAlbumName());
        tag.setArtist(music.getMusicSinger());
        tag.setSongName(music.getMusicTitle());
        tag.setLength(music.getMusicTimeLength());
        song.setTag(tag);

        return song;
    }

    /**
     * 重写了 equals 方法便于比较
     *
     * @param obj 需要比较的对象
     * @return true 说明是同一首歌曲
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Music) {
            Music other = (Music) obj;
            return this.getPath().equals(other.getPath());
        }
        return false;
    }

    /**
     * 重写了 toString 方法，用于格式化输出
     *
     * @return 一个格式化输出的字符串
     */
    @Override
    public String toString() {
        return "Music [musicTitle=" + musicTitle + ", musicSinger=" + musicSinger
                + ", albumName=" + albumName + ", musicTimeLength=" + musicTimeLength + ", path=" + path + "]";
    }
}
