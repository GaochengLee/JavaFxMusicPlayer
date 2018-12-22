/*
 * Copyright (c) 2018. 18-12-10 下午5:05.
 * @author 李高丞
 */

package service;

import mainGUI.Main;
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
            playState = new PlayState();
            // 初始化状态
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

    /**
     * 获得当前音量状态
     *
     * @return 返回当前音量状态
     */
    public int getCurrentVolume() {
        return currentVolume;
    }

    /**
     * 获得当前播放/暂停状态
     *
     * @return 返回当前播放/暂停状态
     */
    public int getCurrentState() {
        return playState.currentState;
    }

    /**
     * 获得当前播放的音乐的图片
     *
     * @return 返回当前播放的音乐的图片
     */
    public Image getCurrentImage() {
        return currentImage;
    }

    /**
     * 获得当前播放的音乐
     *
     * @return 返回当前播放的音乐
     */
    public Music getCurrentMusic() {
        return currentMusic;
    }

    /**
     * 获得当前播放的模式
     *
     * @return 返回当前播放的模式
     */
    public int getCurrentMode() {
        return currentMode;
    }

    /**
     * 获得当前播放的位置
     *
     * @return 返回当前播放的位置
     */
    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * 获得当前播放的音乐列表
     *
     * @return 返回当前播放的音乐列表
     */
    public LinkedList<Music> getCurrent_songList() {
        return current_songList;
    }

    /**
     * 设置当前播放状态为 暂停播放
     */
    public void setState_PAUSE() {
        playState.currentState = PAUSE;
    }

    /**
     * 设置当前播放状态为 开始播放
     */
    public void setState_PLAY() {
        playState.currentState = PLAY;
    }

    /**
     * 设置当前音量
     *
     * @param currentVolume 改变的音量
     */
    public void setCurrentVolume(int currentVolume) {
        this.currentVolume = currentVolume;
    }

    /**
     * 设置当前图片
     *
     * @param currentImage 要改变的图片
     */
    public void setCurrentImage(Image currentImage) {
        this.currentImage = currentImage;
    }

    /**
     * 设置当前音乐播放模式
     *
     * @param currentMode 改变当前音乐播放模式
     */
    public void setCurrentMode(int currentMode) {
        this.currentMode = currentMode;
    }

    /**
     * 设置当前播放位置
     *
     * @param currentIndex 改变当前播放位置
     */
    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    /**
     * 设置当前播放音乐
     *
     * @param currentMusic 改变当前播放音乐
     */
    public void setCurrentMusic(Music currentMusic) {
        this.currentMusic = currentMusic;
    }
}
