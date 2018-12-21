/*
 * Copyright (c) 2018. 18-12-10 下午4:17.
 * @author 李高丞
 */

package controller;

import GUI.Main;
import entity.Music;
import handler.ClickAction;
import handler.Handler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import service.MusicMediaPlayer;
import service.PlayState;

import java.io.File;

/**
 * 播放列表面板的控制器 从FXML中获取 fx:id 设置一系列的 get 方法
 * 初始化了各个按钮的 Action，将事件处理放在了 handler 类里。
 *
 * @author 李高丞
 * @version 1.0 Beta
 */
public class PlayListController implements Controller {

    /**
     * 获取主类
     */
    private Main mainGUI = Main.getMainGUI();

    /**
     * 获取当前的歌曲的播放状态
     */
    private PlayState playState = PlayState.getPlayState();

    /**
     * 获取 handler
     */
    private static Handler handler = new Handler(Main.getMainGUI());

    /**
     * 播放列表的主面板
     */
    @FXML
    private AnchorPane Anchor_mainPlayList;

    /**
     * 播放列表的垂直面板
     */
    @FXML
    private VBox VBox_mainPlayList;

    /**
     * 播放列表的播放列表标签
     */
    @FXML
    private Label Label_playList;

    /**
     * 播放列表的清除全部歌曲按钮
     */
    @FXML
    private Button Button_clearAll;

    /**
     * 播放列表的表视图
     */
    @FXML
    private TableView<Music> TableView_playList;

    /**
     * 播放列表的歌名表列
     */
    @FXML
    private TableColumn<Music, String> TableColumn_playListSongName;

    /**
     * 播放列表的歌手表列
     */
    @FXML
    private TableColumn<Music, String> TableColumn_playListSinger;

    /**
     * 播放列表的专辑表列
     */
    @FXML
    private TableColumn<Music, String> TableColumn_playListAlbum;

    /**
     * 播放列表的歌曲长度表列
     */
    @FXML
    private TableColumn<Music, String> TableColumn_playListLength;

    /*
     * 各种 get 方法，用于获取相应的对象
     */

    /**
     * 获取播放列表的主 AnchorPane
     *
     * @return 返回播放列表的主 AnchorPane
     */
    public AnchorPane getAnchor_mainPlayList() {
        return Anchor_mainPlayList;
    }

    /**
     * 获取播放列表的主 VBox
     *
     * @return 返回播放列表的主 VBox
     */
    public VBox getVBox_mainPlayList() {
        return VBox_mainPlayList;
    }

    /**
     * 获取播放列表的播放列表 Label
     *
     * @return 返回播放列表的播放列表 Label
     */
    public Label getLabel_playList() {
        return Label_playList;
    }

    /**
     * 获取播放列表的删除全部 Button
     *
     * @return 返回播放列表的删除全波 Button
     */
    public Button getButton_clearAll() {
        return Button_clearAll;
    }

    /**
     * 获取播放列表的 TableView
     *
     * @return 返回播放列表的 TableView
     */
    public TableView<Music> getTableView_songList() {
        return TableView_playList;
    }

    /**
     * 获取播放列表的 TableView 的一列
     *
     * @return 返回播放列表的 TableView 的一列
     */
    public TableColumn<Music, String> getTableColumn_playListSongName() {
        return TableColumn_playListSongName;
    }

    /**
     * 获取播放列表的 TableView 的一列
     *
     * @return 返回播放列表的 TableView 的一列
     */
    public TableColumn<Music, String> getTableColumn_playListSinger() {
        return TableColumn_playListSinger;
    }

    /**
     * 获取播放列表的 TableView 的一列
     *
     * @return 返回播放列表的 TableView 的一列
     */
    public TableColumn<Music, String> getTableColumn_playListAlbum() {
        return TableColumn_playListAlbum;
    }

    /**
     * 获取播放列表的 TableView 的一列
     *
     * @return 返回播放列表的 TableView 的一列
     */
    public TableColumn<Music, String> getTableColumn_playListLength() {
        return TableColumn_playListLength;
    }

    /**
     * 清除播放列表
     */
    @FXML
    private void Action_clearAll(ActionEvent e) {
        playState.getCurrent_songList().clear();
        TableView_playList.getItems().clear();

        if (PlayState.getPlayState() != null) {
            File file = new File("C:\\Users\\hasee\\Documents\\JavaCode\\LearnJavaFx\\src\\initial.jpg");
            Image image = new Image(file.toURI().toString());

            mainGUI.getLeftMusicController().getImageView_cover().imageProperty().setValue(image);
            new MusicMediaPlayer().stop();
        }
    }

    /**
     * 初始化 PlayListController
     */
    public void __init__() {
        __initData__();
    }

    /**
     * 初始化 PlayListController 的数据
     * 将 TableView 的每一列都绑定协议
     * 当添加歌曲时 能够成功更新每一列的数据
     */
    private void __initData__() {

        // 将每一个 TableColumn 都绑定了属性值协议
        // 当 TableView 更新时，每一列也能够更新
        TableColumn_playListSongName.setCellValueFactory(new PropertyValueFactory<>("musicTitle"));
        TableColumn_playListSinger.setCellValueFactory(new PropertyValueFactory<>("musicSinger"));
        TableColumn_playListAlbum.setCellValueFactory(new PropertyValueFactory<>("albumName"));
        TableColumn_playListLength.setCellValueFactory(new PropertyValueFactory<>("musicTimeLength"));

        // 设置播放列表鼠标点击事件
        getTableView_songList().setOnMouseClicked(new ClickAction());
    }
}
