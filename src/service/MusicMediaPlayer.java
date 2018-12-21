/*
 * Copyright (c) 2018. 18-12-10 下午5:06.
 * @author 李高丞
 */

package service;

import GUI.Main;
import controller.LeftMusicController;
import controller.MainController;
import controller.MyMusicPageController;
import controller.PlayListController;
import entity.ExtendedInfo;
import entity.Music;
import handler.Handler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import tool.GetMusicInfo;

import java.io.File;
import java.text.DecimalFormat;
import java.util.List;

/**
 * MusicMediaPlayer 音乐播放器
 * 手写了一个简易版的音乐播放器，使用的 Javafx 内置的播放器
 * 来实现了这个播放器
 *
 * @author 李高丞
 * @version 1.0 Beta
 */
public class MusicMediaPlayer {

    /**
     * 一首歌的总时长
     */
    private double endTime;

    /**
     * 当前的播放时长
     */
    private double currentTime;

    /**
     * javafx 中的媒体类声明此类后，可以通过 MediaPlayer 来控制媒体的播放
     */
    private static Media media;

    /**
     * javafx 中的媒体控制类，控制媒体的播放状态
     */
    private static MediaPlayer mediaPlayer;

    /**
     * 当前播放器的播放状态
     */
    private static PlayState playState = PlayState.getPlayState();

    /**
     * 获取 Main 类
     */
    private Main mainGUI = Main.getMainGUI();

    private Handler handler = new Handler(mainGUI);

    /**
     * 获取 Main 类的控制器
     */
    private MainController mainController = mainGUI.getMainController();

    /**
     * 获取左侧列表的控制器
     */
    private LeftMusicController leftMusicController = mainGUI.getLeftMusicController();

    /**
     * 获取我的音乐界面的控制器
     */
    private MyMusicPageController musicPageController = mainGUI.getMyMusicPageController();

    private PlayListController playListController = mainGUI.getPlayListController();

    /**
     * 获取 Main 类的播放进度滑动条
     */
    private Slider songSlider = mainController.getSlider_songSlider();

    /**
     * 获取 Main 类的声音滑动条
     */
    private Slider volumeSlider = mainController.getSlider_volume();

    /**
     * 获取 Main 类的播放进度条
     */
    private ProgressBar songProgress = mainController.getProgressBar_songProcess();

    /**
     * 获取我的音乐界面中的音乐列表
     */
    private TableView<Music> musicList = musicPageController.getTableView_songList();

    public MusicMediaPlayer() {

    }

    /**
     * MusicMediaPlayer 的构造器
     *
     * @param music 当前需要播放的音乐
     */
    public MusicMediaPlayer(Music music) {

        // 初始化播放器
        __initPlayer__();

        // 获取 music 的 Media
        media = new Media(new File(music.getPath()).toURI().toString());

        // 获取 music 的控制器
        mediaPlayer = new MediaPlayer(media);

        // 设置播放器界面的信息
        setMusicInfo(music);

        // 判断当前的播放列表是否存在新添加的 music
        if (playState.getCurrent_songList().contains(music)) {

            // 如果存在的话，把当前的播放位置设置为该歌曲所在的位置
            playState.setCurrentIndex(getBySongName(music.getMusicTitle()));
        } else {

            // 如果不存在，将新的位置赋给这个歌曲 
            playState.setCurrentIndex(playState.getCurrent_songList().size());
        }

    }

    /**
     * 初始化播放器
     */
    private void __initPlayer__() {
        // 如果当前播放器存在
        if (mediaPlayer != null) {
            // 暂停播放然后设置当前播放器为空
            mediaPlayer.pause();
            mediaPlayer = null;
        }
    }

    /**
     * 初始化完成后，开始启动播放
     */
    public void start() {

        // 初始化事件处理
        __initAction__();

        // 开始播放
        __play__();
    }

    /**
     * 开始播放音乐
     */
    private void __play__() {
        // 设置当前播放状态为歌曲正在播放
        playState.setState_PLAY();

        // 开始播放
        mediaPlayer.play();

        mainController.getButton_pause().getStyleClass().set(0, "buttonPause");
        mainController.getButton_last().setDisable(false);
        mainController.getButton_next().setDisable(false);
        mainController.getButton_pause().setDisable(false);
    }

    /**
     * 初始化事件处理
     */
    private void __initAction__() {

        // 当播放器控制器准备好时
        mediaPlayer.setOnReady(() -> {

            // 设置结束时间
            endTime = mediaPlayer.getStopTime().toSeconds();

            // 将当前播放的歌曲放在播放列表中
            for (Music music : mainGUI.getPlayListController().getTableView_songList().getItems())

                // 如果不存在在部分列表里的话，就添加到播放列表中
                if (!playState.getCurrent_songList().contains(music))
                    playState.getCurrent_songList().add(music);

        });

        // 当媒体播放器播放到文件末尾时
        mediaPlayer.setOnEndOfMedia(() -> {

            // 停止播放
            mediaPlayer.stop();

            // 设置播放位置到当前歌曲的开头位置
            mediaPlayer.seek(Duration.ZERO);

            // 判断当前的播放模式
            switch (playState.getCurrentMode()) {
                case PlayState.LOOPPLAY:
                    // 如果是列表循环，调用列表播放方法
                    playLoop();
                    break;
                case PlayState.RANDOMPLAY:
                    // 如果是随机播放，调用随机播放方法
                    playRandom();
                    break;
                case PlayState.SINFLECYCLE:
                    // 如果是单曲循环，重新再播放
                    mediaPlayer.play();
                    break;

            }
        });

        // 设置媒体控制器的当前播放时间协议
        // 为当前播放时间协议添加上一个事件监听，使用了观察者
        // 如果当前播放时间改变，会通知给这个播放器控制器
        mediaPlayer.currentTimeProperty().addListener(ov -> {
            // 获取当前播放时间，转化为秒
            currentTime = mediaPlayer.getCurrentTime().toSeconds();

            // 设置主界面的当前时间
            mainController.getLabel_currentTime().setText(SecondsToStr(currentTime));

            // 设置主界面的结束时间
            mainController.getLabel_totalTime().setText(SecondsToStr(endTime));

            // 设置主界面的歌曲播放滑动条位置
            songSlider.setValue(currentTime / endTime * 100);

            // 设置主界面的歌曲播放进度条位置
            songProgress.setProgress(currentTime / endTime);
        });

        // 设置滑动条的值协议
        // 为当前滑动条的值协议添加上事件监听，使用观察者
        // 如果滑动条的值发生了变化，会触发这个事件处理
        songSlider.valueProperty().addListener(ov -> {
            // 如果滑动条的值发生了变化，将媒体控制器的播放时间调到这个位置

            if (songSlider.isValueChanging())
                mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(songSlider.getValue() / 100));
            if (songSlider.isSnapToTicks())
                mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(songSlider.getValue() / 100));
            if (songSlider.isPressed())
                mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(songSlider.getValue() / 100));
        });

        // 将媒体控制器的声音协议和声音滑动条的值进行绑定
        // 当声音滑动条的值发送变化时，音乐播放的声音也会变化
        // 把值分成了 100 份
        mediaPlayer.volumeProperty().bind(volumeSlider.valueProperty().divide(100));

        // 设置 播放/暂停 按钮的事件处理
        mainController.getButton_pause().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // 获取当前是否在播放
                int target = playState.getCurrentState();

                // 如果处于播放，就暂停
                if (target == PlayState.PLAY) {
                    mediaPlayer.pause();
                    playState.setState_PAUSE();
                    mainController.getButton_pause().getStyleClass().set(0, "buttonPlay");
                }
                // 如果处于暂停，就播放
                if (target == PlayState.PAUSE) {
                    mediaPlayer.play();
                    playState.setState_PLAY();
                    mainController.getButton_pause().getStyleClass().set(0, "buttonPause");
                }
            }
        });

        // 设置 静音 按钮的事件处理
        mainController.getButton_mute().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // 获取当前是否静音
                int isMute = playState.getCurrentVolume();

                // 如果静音，则取消静音
                if (isMute == PlayState.MUTE) {
                    playState.setCurrentVolume(PlayState.UNMUTE);
                    mediaPlayer.setMute(false);
                    mainController.getButton_mute().getStyleClass().set(0, "buttonMute");
                }
                // 如果不是静音，则静音
                if (isMute == PlayState.UNMUTE) {
                    mediaPlayer.setMute(true);
                    playState.setCurrentVolume(PlayState.MUTE);
                    mainController.getButton_mute().getStyleClass().set(0, "buttonCancelMute");
                }
            }
        });

    }

    public void stop() {
        playState.getCurrent_songList().clear();
        playListController.getTableView_songList().getItems().clear();

        mediaPlayer.stop();

        // 设置播放位置到当前歌曲的开头位置
        mediaPlayer.seek(Duration.ZERO);

        mainController.getLabel_currentTime().setText("--:--");
        mainController.getLabel_totalTime().setText("--:--");
        mainController.getButton_pause().setDisable(true);
        mainController.getButton_last().setDisable(true);
        mainController.getButton_next().setDisable(true);

        leftMusicController.getLabel_songName().setText("-----");
        leftMusicController.getLabel_singer().setText("-----");

        Handler.refreshStatusBar(new ExtendedInfo());
        Handler.messageProperty().setValue("0");

    }

    /**
     * 播放下一首歌曲
     * 以当前播放列表为准
     */
    private void playNext() {

        // 获取当前播放歌曲在列表中的位置
        int index = playState.getCurrentIndex();

        // 获取当前播放列表
        List<Music> list = playState.getCurrent_songList();

        // 播放器音乐实体类
        Music music;

        ExtendedInfo info;

        // 如果当前播放歌曲是列表中的最后一个
        if (index >= playState.getCurrent_songList().size()) {

            // 获取列表中的第一个歌曲
            music = list.get(0);

            info = GetMusicInfo.getExtendedInfo(music.getPath());

            // 开始播放
            MusicMediaPlayer mp = new MusicMediaPlayer(music);
            playState.setCurrentIndex(0);
            mp.start();

            // 在主界面设置播放信息
            setMusicInfo(music);

            Handler.refreshStatusBar(info);

        } else {
            // 如果当前播放歌曲不是列表中的最后一个

            // 获取下一首歌曲
            music = list.get(index);

            info = GetMusicInfo.getExtendedInfo(music.getPath());

            // 开始播放
            MusicMediaPlayer mp = new MusicMediaPlayer(music);
            playState.setCurrentIndex(index);
            mp.start();

            // 设置主界面播放信息
            setMusicInfo(music);

            Handler.refreshStatusBar(info);
        }
    }

    /**
     * 列表循环播放方法
     */
    private void playLoop() {
        // 获取当前的歌曲的播放位置
        int playIndex = playState.getCurrentIndex();

        // 如果当前播放列表只有一首歌
        if (musicList.getItems().size() == 1)
            // 继续播放同一首
            mediaPlayer.play();

        else if (playIndex >= musicList.getItems().size()) {
            // 如果当前播放位置是最后一个
            // 从头开始播放
            playState.setCurrentIndex(0);

        } else if (musicList.getItems().size() > 1) {
            // 如果是播放位置是其他位置
            // 并且播放列表大小不是 1

            // 设置播放位置为下一首
            playState.setCurrentIndex(playIndex + 1);

            // 播放下一首
            playNext();
        }
    }

    /**
     * 随机播放方法
     */
    private void playRandom() {

        // 获取播放列表歌曲数目
        int temp = playState.getCurrent_songList().size();

        // 根据播放列表的歌曲数目产生一个随机数
        int random = (int) (Math.random() * temp);

        // 声明一个歌曲实体类
        Music music;

        System.out.println(random);

        // 获取在该位置的歌曲
        music = playState.getCurrent_songList().get(random);

        ExtendedInfo info = GetMusicInfo.getExtendedInfo(music.getPath());

        // 开始播放歌曲
        MusicMediaPlayer musicMediaPlayer = new MusicMediaPlayer(music);
        // 设置播放位置
        playState.setCurrentIndex(random);
        musicMediaPlayer.start();

        Handler.refreshStatusBar(info);
    }

    /**
     * 设置主界面歌曲的播放信息
     *
     * @param music 当前正在播放的歌曲
     */
    private void setMusicInfo(Music music) {
        // 获取当前播放歌曲的图片（当前只是播放 MP3 文件）
        // FLAC 文件尚未测试
        GetMusicInfo.getMP3Image(music.getPath());

        // 获取歌曲封面的存放位置
        String musicPath = music.getPath().substring(0, music.getPath().lastIndexOf('.')) + ".png";

        // 将该图片转化为实体类，让程序能浏览图片
        File file = new File(musicPath);
        Image image = new Image(file.toURI().toString());

        // 设置播放歌曲的封面
        leftMusicController.getImageView_cover().imageProperty().setValue(image);
        // 设置播放歌曲的歌手
        leftMusicController.getLabel_singer().setText(music.getMusicSinger());
        // 设置播放歌曲的歌名
        leftMusicController.getLabel_songName().setText(music.getMusicTitle());
    }

    /**
     * 将以 double 类存储的秒数转化为字符串类型
     *
     * @param seconds 存入的秒数
     * @return hh:mm 类的字符串
     */
    private static String SecondsToStr(Double seconds) {
        // 选择要格式化的类型
        DecimalFormat formatter = new DecimalFormat("00");
        // 将 double 转化为 int
        int count = seconds.intValue();
        count = count % 3600;

        // 获取分钟
        Integer Minutes = count / 60;
        // 获取秒
        count = count % 60;

        // 返回 hh:mm 类的字符串
        return formatter.format(Minutes) + ":" + formatter.format(count);
    }

    /**
     * 根据歌曲名字获取在表中的位置
     *
     * @param name 歌曲名字
     * @return 在播放列表中的位置
     */
    @SuppressWarnings("SameParameterValue")
    private int getBySongName(String name) {
        int index = 0;

        // 找到在播放列表中的位置
        for (Music music : musicList.getItems()) {
            if (music.getMusicTitle().equals(name))
                // 找到了，则返回当前位置
                return index;
            else
                // 否则，查询下一个位置
                index++;
        }
        // 找不到返回 0
        return 0;
    }
}
