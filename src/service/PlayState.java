/*
 * Copyright (c) 2018. 18-12-10 下午5:05.
 * @author 李高丞
 */

package service;

import GUI.Main;
import entity.Music;
import javafx.scene.image.Image;

import java.util.LinkedList;

/**
 * PlayState 当前的播放状态
 * 这个类可以获取当前音乐播放器的播放状态
 * 为什么要写这个类呢？
 * 因为要实现列表循环、单曲循环、随机播放那就必须要知道当前的播放列表
 * 并且要根据当前的播放模式来确定下一首需要播放什么歌曲
 *
 * @author 李高丞
 * @version 1.0 Beta
 */
public class PlayState {

    /**
     * 播放歌曲
     */
    public static final int PLAY = 1;
    /**
     * 暂停播放
     */
    public static final int PAUSE = -1;
    /**
     * 前一首
     */
    public static final int PREMUSIC = 3;
    /**
     * 下一首
     */
    public static final int NEXMUSIC = -3;
    /**
     * 静音
     */
    public static final int MUTE = 2;
    /**
     * 取消静音
     */
    public static final int UNMUTE = -2;
    /**
     * 单曲循环
     */
    public static final int SINFLECYCLE = 10;
    /**
     * 列表循环
     */
    public static final int LOOPPLAY = 11;
    /**
     * 随机播放
     */
    public static final int RANDOMPLAY = 12;
    /**
     * 获取 Main 类
     */
    private static Main mainGUI = Main.getMainGUI();

    /**
     * PlayState 类为单例设计
     */
    private static PlayState playState;

    /**
     * 当前的播放列表
     */
    private static LinkedList<Music> current_songList;

    /**
     * 当前的正在播放的音乐
     */
    private Music currentMusic;

    /**
     * 当前的播放状态（正在播放 / 暂停）
     */
    private int currentState;

    /**
     * 当前播放模式（列表循环 / 单曲循环 / 随机播放）
     */
    private int currentMode;

    /**
     * 当前播放的音量
     */
    private int currentVolume;

    /**
     * 当前播放歌曲在播放列表中的位置
     */
    private int currentIndex;

    /**
     * 当前播放歌曲的封面
     */
    private Image currentImage;

    /**
     * PlayState 的构造器
     *
     * @return 返回 PlayState
     */
    public static PlayState getPlayState() {

        // 如果是第一次出创建 PlayState
        if (playState == null) {
            System.out.println("new PlayState create");
            playState = new PlayState();

            playState.currentState = PAUSE;
            playState.currentMode = LOOPPLAY;
            playState.currentVolume = UNMUTE;
            PlayState.current_songList = new LinkedList<>();

        }
        return playState;
    }

    /*
     * get 方法和 set 方法
     * 用来获得或修改当前的播放状态
     */

    public int getCurrentVolume() {
        return currentVolume;
    }

    public int getCurrentState() {
        return playState.currentState;
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public Music getCurrentMusic() {
        return currentMusic;
    }

    public int getCurrentMode() {
        return currentMode;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public LinkedList<Music> getCurrent_songList() {
        return current_songList;
    }

    public void setState_PAUSE() {
        playState.currentState = PAUSE;
    }

    public void setState_PLAY() {
        playState.currentState = PLAY;
    }

    public void setCurrentVolume(int currentVolume) {
        this.currentVolume = currentVolume;
    }

    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }

    public void setCurrentMode(int currentMode) {
        this.currentMode = currentMode;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public void setCurrentMusic(Music currentMusic) {
        this.currentMusic = currentMusic;
    }
}
