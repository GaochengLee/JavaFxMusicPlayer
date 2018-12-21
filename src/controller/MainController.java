/*
 * Copyright (c) 2018. 18-12-10 下午4:17.
 * @author 李高丞
 */

package controller;

import GUI.Main;
import handler.Handler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.*;
import java.net.Socket;

/**
 * 主面板的控制器从FXML中获取 fx:id 设置一系列的 get 方法
 * 初始化了各个按钮的 Action，将事件处理放在了 handler 类里。
 * 初始化了网络连接，若未能成功连接会出现对话框提示
 *
 * @author 李高丞
 * @version 1.0 Beta
 */
public class MainController {
    /**
     * 获取 Main 类
     */
    private static Main mainGUI = Main.getMainGUI();

    /**
     * 获取 handler 事件处理类
     */
    private static Handler handler = new Handler(mainGUI);

    /**
     * 声明套接字
     */
    public static Socket socket;

    /**
     * 声明网络输入流
     */
    public static BufferedReader serverIn;

    /**
     * 声明网络输出流
     */
    public static PrintStream serverOut;

    /**
     * 确认是否连接上服务器
     */
    public static boolean isConnect;

    /**
     * 主界面的主面板
     */
    @FXML
    private BorderPane mainBorder;

    /**
     * 主界面的锚面板
     */
    @FXML
    private AnchorPane Anchor_top;

    /**
     * 主界面的水平面板
     */
    @FXML
    private HBox HBox_top;

    /**
     * 主界面的标签，单击返回主页
     */
    @FXML
    private Label Label_page;

    /**
     * 主界面的搜索框
     */
    @FXML
    private TextField TextField_searchSong;

    /**
     * 主界面的搜索按钮
     */
    @FXML
    private Button Button_search;

    /**
     * 主界面的最小化按钮
     */
    @FXML
    private Button Button_miniSize;

    /**
     * 主界面的更换颜色按钮
     */
    @FXML
    private Button Button_changeColor;

    /**
     * 主界面的退出按钮
     */
    @FXML
    private Button Button_exit;

    /**
     * 主界面的锚面板
     */
    @FXML
    private AnchorPane Anchor_bottom;

    /**
     * 主界面的垂直面板
     */
    @FXML
    private HBox HBox_bottom;

    /**
     * 主界面的上一首歌曲按钮
     */
    @FXML
    private Button Button_last;

    /**
     * 主界面的暂停/播放按钮
     */
    @FXML
    private Button Button_pause;

    /**
     * 主界面的下一首按钮
     */
    @FXML
    private Button Button_next;

    /**
     * 主界面的当前播放事件
     */
    @FXML
    private Label Label_currentTime;

    /**
     * 主界面的播放进度进度条
     */
    @FXML
    private ProgressBar ProgressBar_songProcess;

    /**
     * 主界面的播放进度滑动条
     */
    @FXML
    private Slider Slider_songSlider;

    /**
     * 主界面的歌曲的总共时间
     */
    @FXML
    private Label Label_totalTime;

    /**
     * 主界面的静音按钮
     */
    @FXML
    private Button Button_mute;

    /**
     * 主界面的声量堆栈面板
     */
    @FXML
    private StackPane StackPane_volume;

    /**
     * 主界面的当前音量进度条
     */
    @FXML
    private ProgressBar ProgressBar_volume;

    /**
     * 主界面的当前音量滑动条
     */
    @FXML
    private Slider Slider_volume;

    /**
     * 主界面的更换播放模式按钮
     */
    @FXML
    private Button Button_modeSwitch;

    /**
     * 主界面的播放列表按钮
     */
    @FXML
    private Button Button_playList;

    /**
     * 主界面的播放列表内的总共歌曲标签
     */
    @FXML
    private Label Label_playList;

    /**
     * 主界面状态栏的当前歌曲的比特率
     */
    @FXML
    private Label Label_BitRate;

    /**
     * 主界面状态栏的当前歌曲的采样率
     */
    @FXML
    private Label Label_SampleRate;

    /**
     * 主界面状态栏的当前歌曲的文件格式
     */
    @FXML
    private Label Label_encodingType;

    /**
     * 主界面状态栏的当前歌曲的压缩格式
     */
    @FXML
    private Label Label_format;

    /**
     * 主界面状态栏的当前歌曲的大小
     */
    @FXML
    private Label Label_size;

    /**
     * 主界面状态栏的当前歌曲的流派
     */
    @FXML
    private Label Label_genre;

    /**
     * 主界面状态栏的当前歌曲的版权声明
     */
    @FXML
    private Label Label_copyright;

    /**
     * 主界面状态栏的当前歌曲的声道
     */
    @FXML
    private Label Label_channels;

    /*
     * 各种 get 方法，用于获取相应的对象
     */

    /**
     * 获取主界面的主 BorderPane
     *
     * @return 返回主界面的主 BorderPane
     */
    public BorderPane getMainBorder() {
        return mainBorder;
    }

    /**
     * 获取主界面的顶部 AnchorPane
     *
     * @return 返回主界面的顶部 AnchorPane
     */
    public AnchorPane getAnchor_top() {
        return Anchor_top;
    }

    /**
     * 获取主界面的顶部 HBox
     *
     * @return 返回主界面的顶部 HBox
     */
    public HBox getHBox_top() {
        return HBox_top;
    }

    /**
     * 获取主界面的返回主界面 Label
     *
     * @return 返回主界面的返回主界面 Label
     */
    public Label getLabel_page() {
        return Label_page;
    }

    /**
     * 获取主界面的搜索歌曲 TextField
     *
     * @return 返回主界面的搜索歌曲 TextField
     */
    public TextField getTextField_searchSong() {
        return TextField_searchSong;
    }

    /**
     * 获取主界面的搜索 Button
     *
     * @return 返回主界面的搜索 Button
     */
    public Button getButton_search() {
        return Button_search;
    }

    /**
     * 获取主界面的最小化 Button
     *
     * @return 返回主界面的最小化 Button
     */
    public Button getButton_miniSize() {
        return Button_miniSize;
    }

    /**
     * 获取主界面的换色 Button
     *
     * @return 返回主界面的换色 Button
     */
    public Button getButton_changeColor() {
        return Button_changeColor;
    }

    /**
     * 获取主界面的退出 Button
     *
     * @return 返回主界面的退出 Button
     */
    public Button getButton_exit() {
        return Button_exit;
    }

    /**
     * 获取主界面的底部 AnchorPane
     *
     * @return 返回主界面的底部 AnchorPane
     */
    public AnchorPane getAnchor_bottom() {
        return Anchor_bottom;
    }

    /**
     * 获取主界面的底部 HBox
     *
     * @return 返回主界面的底部 HBox
     */
    public HBox getHBox_bottom() {
        return HBox_bottom;
    }

    /**
     * 获取主界面的上一首 Button
     *
     * @return 返回主界面的上一首 Button
     */
    public Button getButton_last() {
        return Button_last;
    }

    /**
     * 获取主界面的暂停/播放 Button
     *
     * @return 返回主界面的暂停/播放 Button
     */
    public Button getButton_pause() {
        return Button_pause;
    }

    /**
     * 获取主界面的下一首 Button
     *
     * @return 返回主界面的下一首 Button
     */
    public Button getButton_next() {
        return Button_next;
    }

    /**
     * 获取主界面的当前时间 Label
     *
     * @return 返回主界面的当前时间 Label
     */
    public Label getLabel_currentTime() {
        return Label_currentTime;
    }

    /**
     * 获取主界面的歌曲进度条 ProgressBar
     *
     * @return 返回主界面的歌曲进度条 ProGressBar
     */
    public ProgressBar getProgressBar_songProcess() {
        return ProgressBar_songProcess;
    }

    /**
     * 获取主界面的歌曲滑动块 Slider
     *
     * @return 返回主界面的歌曲滑动块 Slider
     */
    public Slider getSlider_songSlider() {
        return Slider_songSlider;
    }

    /**
     * 获取主界面的歌曲总共时间
     *
     * @return 返回主界面的歌曲的总共时间
     */
    public Label getLabel_totalTime() {
        return Label_totalTime;
    }

    /**
     * 获取主界面的静音 Button
     *
     * @return 返回主界面的静音 Button
     */
    public Button getButton_mute() {
        return Button_mute;
    }

    /**
     * 获取主界面的音量堆栈面板 StackPane
     *
     * @return 返回主界面的音量堆栈面板 StackPane
     */
    public StackPane getStackPane_volume() {
        return StackPane_volume;
    }

    /**
     * 获取主界面的音量进度条 ProgressBar
     *
     * @return 返回主界面的音量进度条 ProgressBar
     */
    public ProgressBar getProgressBar_volume() {
        return ProgressBar_volume;
    }

    /**
     * 获取主界面的音量滑动块 Slider
     *
     * @return 返回主界面的音量滑动块 Slider
     */
    public Slider getSlider_volume() {
        return Slider_volume;
    }

    /**
     * 获取主界面的播放模式切换 Button
     *
     * @return 返回主界面的播放模式切换 Button
     */
    public Button getButton_modeSwitch() {
        return Button_modeSwitch;
    }

    /**
     * 获取主界面的播放列表 Button
     *
     * @return 返回主界面的播放列表 Button
     */
    public Button getButton_playList() {
        return Button_playList;
    }

    /**
     * 获取主界面的播放列表 Label
     *
     * @return 返回主界面的播放列表 Label
     */
    public Label getLabel_playList() {
        return Label_playList;
    }

    /**
     * 获取网络输出流
     *
     * @return 返回网络输出流
     */
    public static PrintStream getServerOut() {
        return serverOut;
    }

    /**
     * 获取网络套接字
     *
     * @return 返回网络套接字
     */
    public static Socket getSocket() {
        return socket;
    }

    /**
     * 获取网络输出流
     *
     * @return 返回网络输出流
     */
    public static BufferedReader getServerIn() {
        return serverIn;
    }

    /**
     * 获取主界面的比特率 Label
     *
     * @return 返回主界面的比特率 Label
     */
    public Label getLabel_BitRate() {
        return Label_BitRate;
    }

    /**
     * 获取主界面的采样率 Label
     *
     * @return 返回主界面的采样率 Label
     */
    public Label getLabel_SampleRate() {
        return Label_SampleRate;
    }

    /**
     * 获取主界面的文件格式 Label
     *
     * @return 返回主界面的文件格式 Label
     */
    public Label getLabel_encodingType() {
        return Label_encodingType;
    }

    /**
     * 获取主界面的压缩方式 Label
     *
     * @return 返回主界面的压缩方式 Label
     */
    public Label getLabel_format() {
        return Label_format;
    }

    /**
     * 获取主界面的文件大小 Label
     *
     * @return 返回主界面的文件大小 Label
     */
    public Label getLabel_size() {
        return Label_size;
    }

    /**
     * 获取主界面的音乐类型 Label
     *
     * @return 返回主界面的音乐类型 Label
     */
    public Label getLabel_genre() {
        return Label_genre;
    }

    /**
     * 获取主界面的版权 Label
     *
     * @return 返回主界面的版权 Label
     */
    public Label getLabel_copyright() {
        return Label_copyright;
    }

    /**
     * 获取主界面的声道 Label
     *
     * @return 返回主界面的声道 Label
     */
    public Label getLabel_channels() {
        return Label_channels;
    }

    /**
     * 搜索按钮事件
     *
     * @param e 事件
     */
    @FXML
    private void Action_searchButton(ActionEvent e) {
        handler.search();
    }

    /**
     * 单击了左上角的 “ 退出 ” 按钮后的事件处理
     *
     * @param e 事件
     */
    @FXML
    private void Action_exit(ActionEvent e) {
        Platform.exit();
        System.exit(0);
    }

    /**
     * 单击了左上角的 “ 最小化按 ” 钮后的事件处理
     *
     * @param e 事件
     */
    @FXML
    private void Action_miniSize(ActionEvent e) {
        Main.getMainStage().setIconified(true);
    }

    /**
     * 单击了左上角的 “ 换肤 ” 按钮后的事件处理
     *
     * @param e 事件
     */
    @FXML
    private void Action_changeColor(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("通知");
        alert.setHeaderText("^-^");
        alert.setContentText("换色功能还处于开发阶段");
        alert.showAndWait();
    }

    /**
     * 单击了右下角的“播放列表”按钮后的事件处理
     *
     * @param e 事件
     */
    @FXML
    private void Action_playList(ActionEvent e) {
        handler.playList();
    }

    /**
     * 单击了右下角的“播放模式”按钮后的事件处理
     *
     * @param e 事件
     */
    @FXML
    private void Action_modeSwitch(ActionEvent e) {
        handler.changeMode();
    }

    /**
     * 单击了左下角的“前一首歌曲”按钮后的事件处理
     *
     * @param e 事件
     */
    @FXML
    private void Action_preMusic(ActionEvent e) {
        handler.prePlay();
    }

    /**
     * 单击了左下角的“下一首歌曲”按钮后的事件处理
     *
     * @param e 事件
     */
    @FXML
    private void Action_nextMusic(ActionEvent e) {
        handler.nextPlay();
    }

    /**
     * 初始化 MainController
     */
    public void __init__() {
        __initSlider__();
        __initCSS__();
        __initNetWork__();

        // 对左上角区域的 Label 设置一个鼠标单击处理器
        // 鼠标单击这个区域会返回主界面
        getLabel_page().setOnMouseClicked(event -> {
            // 如果当前界面不为空，将当前界面设置为主界面
            if (handler.isListOn()) {
                // 设置主界面的中央为空
                mainBorder.setCenter(null);
                // 设置主界面的中央为我的音乐界面
                mainBorder.setCenter(mainGUI.getMyMusicPage());
            }
        });

        // 对上方区域的 TextField 设置一个按键处理器
        // 当按下回车键之后，通过网络发送搜索数据到服务器
        getTextField_searchSong().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                // 如果按下的是回车键 则触发事件
                if (event.getCode() == KeyCode.ENTER) {
                    // 如果当前 TextField 里面的内容不为空时， 才将数据发送到服务器
                    if (getTextField_searchSong().getText() != null) {
                        // 发送到服务器的数据
                        serverOut.println("# search " + getTextField_searchSong().getText());
                        // 启动一个线程，接收从服务器发送过来的搜索结果
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    // 接受文件
                                    Handler.receiveFile(socket);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                        // 等待数据传输结束
                        try {
                            Thread.sleep(500);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    // 如果当前主界面不为搜索界面
                    // 将其切换为搜索界面
                    if (mainBorder.getCenter() != null) {
                        // 设置主界面的中央为空
                        mainBorder.setCenter(null);
                        // 设置主界面的中央为搜索界面
                        mainBorder.setCenter(mainGUI.getSearchPage());
                        // 刷新搜索界面显示搜索结果
                        handler.refreshSearch(getTextField_searchSong().getText());
                    }
                }
            }
        });
    }

    /**
     * 初始化主界面下方的播放数据
     */
    public void __initData__() {

        // 如果主界面不为空，设置主界面为我的音乐
        if (mainBorder.getCenter() != null) {
            // 设置主界面的中央为空
            mainBorder.setCenter(null);
            // 设置主界面的中央为我的音乐界面
            mainBorder.setCenter(mainGUI.getMyMusicPage());
        }
        // 设置初始播放时间为 --:--
        Label_currentTime.setText("--:--");
        // 设置总共播放时间为 --:--
        Label_totalTime.setText("--:--");
        // 设置初始播放列表内的歌曲数为 0
        Label_playList.setText("0");
        // 设置比特率为 ---
        Label_BitRate.setText("---");
        // 设置声道为 ---
        Label_channels.setText("---");
        // 设置版权为 ---
        Label_copyright.setText("---");
        // 设置大小为 ---
        Label_size.setText("---");
        // 设置采样率为 ---
        Label_SampleRate.setText("---");
        // 设置音乐类型为 ---
        Label_genre.setText("---");
        // 文件压缩格式为 ---
        Label_format.setText("---");
        // 文件格式为 ---
        Label_encodingType.setText("---");
    }

    /**
     * 对播放条和音量条的初始化
     */
    private void __initSlider__() {

        // 设置当前歌曲的音量为 100， 最大为 100
        // 设置滑动条为最右端位置
        Slider_volume.setValue(100);

        // 设置进度条为中间位置
        ProgressBar_volume.setProgress(1);

        // 设置滑动条的鼠标单击事件，单击后将滑动条移动到单击的位置，进度条也跟到相应位置
        Slider_songSlider.setOnMouseClicked(event -> {
            double songValue = Slider_songSlider.getValue();
            ProgressBar_songProcess.setProgress(songValue / 100);
        });

        // 设置滑动条的鼠标正在拖动事件，正在拖动时，进度条也跟到相应位置
        Slider_songSlider.setOnMouseDragged(event -> {
            double songValue = Slider_songSlider.getValue();
            ProgressBar_songProcess.setProgress(songValue / 100);
        });

        // 设置滑动条的鼠标进入拖动事件，进入拖动时，进度条也跟到相应位置
        Slider_songSlider.setOnMouseDragEntered(event -> {
            double songValue = Slider_songSlider.getValue();
            ProgressBar_songProcess.setProgress(songValue / 100);
        });

        // 设置滑动条的鼠标离开拖动事件，离开拖动时，进度条也跟到相应位置
        Slider_songSlider.setOnMouseDragExited(event -> {
            double songValue = Slider_songSlider.getValue();
            ProgressBar_songProcess.setProgress(songValue / 100);
        });

        // 设置音量滑动条的值变化协议，添加了一个监听器，使用观察者来监听
        // 当滑动条的值变化时，进度条的值也变化
        Slider_volume.valueProperty().addListener((o, ov, nv) -> {
            double songVolume = Slider_volume.getValue();
            ProgressBar_volume.setProgress(songVolume / 100);
        });

        // 设置音量滑动条的正在拖动事件，正在拖动时，进度条也跟到相应位置
        Slider_volume.setOnMouseDragged(event -> {
            double songVolume = Slider_volume.getValue();
            ProgressBar_volume.setProgress(songVolume / 100);
        });

        // 设置音量滑动条的进入拖动事件，进入拖动时，进度条也跟到相应位置
        Slider_volume.setOnMouseDragEntered(event -> {
            double songVolume = Slider_volume.getValue();
            ProgressBar_volume.setProgress(songVolume / 100);
        });

        // 设置音量滑动条的离开拖动事件，离开拖动是，进度条也跟到相应位置
        Slider_volume.setOnMouseDragExited(event -> {
            double songVolume = Slider_volume.getValue();
            ProgressBar_volume.setProgress(songVolume / 100);
        });
    }

    /**
     * 初始化样式层叠表
     */
    private void __initCSS__() {
        /*
         * 初始化为默认 CSS 样式
         */
        Button_search.getStyleClass().set(0, "buttonSearch");
        Button_miniSize.getStyleClass().set(0, "buttonMiniSize");
        Button_changeColor.getStyleClass().set(0, "buttonChangeColor");
        Button_playList.getStyleClass().set(0, "playList");
        Button_exit.getStyleClass().set(0, "buttonExit");
        Button_last.getStyleClass().set(0, "buttonPre");
        Button_pause.getStyleClass().set(0, "buttonPlay");
        Button_next.getStyleClass().set(0, "buttonNext");
        Button_mute.getStyleClass().set(0, "buttonMute");
        Button_modeSwitch.getStyleClass().set(0, "modeLoopPlay");
    }

    /**
     * 初始化网络
     */
    private void __initNetWork__() {
        try {
            // 链接到的服务器地址和端口
            socket = new Socket("127.0.0.1", 5432);
            serverOut = new PrintStream(socket.getOutputStream());

            //serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //Thread thread = new Thread(new RemoteReader());
            //thread.start();

        } catch (IOException e) {
            // 当无法连接上服务器时，弹出一个对话框来提示
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("未能成功连接上服务器");
            alert.setHeaderText("连接服务器失败");
            alert.setContentText("尝试重启客户端连接服务器");
            alert.showAndWait();

            // 将需要网络通信的按钮和文字输入条禁用
            Platform.runLater(() -> {
                TextField_searchSong.setDisable(true);
                Button_search.setDisable(true);
                mainGUI.getLeftMusicController().getButton_chatRoom().setDisable(true);
            });
        }
    }

}
