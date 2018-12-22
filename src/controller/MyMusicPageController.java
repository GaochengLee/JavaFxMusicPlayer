/*
 * Copyright (c) 2018. 18-12-10 下午4:17.
 * @author 李高丞
 */

package controller;

import entity.Song;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;
import mainGUI.Main;
import entity.Music;
import handler.ClickAction;
import handler.Handler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import service.PlayState;

import java.util.List;


/**
 * 我的音乐面板的控制器 从FXML中获取 fx:id 设置一系列的 get 方法
 * 初始化了各个按钮的 Action，将事件处理放在了 handler 类里。
 *
 * @author 李高丞
 * @version 1.0 Beta
 */
public class MyMusicPageController implements Controller {

    /**
     * 获取 Main 类
     */
    private static Main mainGUI = Main.getMainGUI();

    /**
     * 获取 handler 事件处理类
     */
    private Handler handler = new Handler(mainGUI);

    /**
     * 获取当前程序的播放状态
     */
    private static PlayState playState = PlayState.getPlayState();

    /**
     * 我的音乐界面的主垂直面板
     */
    @FXML
    private VBox VBox_main;

    /**
     * 我的音乐界面的锚面板
     */
    @FXML
    private AnchorPane Anchor_top;

    /**
     * 我的音乐界面的垂直面板
     */
    @FXML
    private HBox HBox_myMusicTop;

    /**
     * 我的音乐界面的我的音乐标签
     */
    @FXML
    private Label Label_myMusic;

    /**
     * 我的音乐界面的播放全部按钮
     */
    @FXML
    private Button Button_playAll;

    /**
     * 我的音乐界面的锚面板
     */
    @FXML
    private AnchorPane Anchor_bottom;

    /**
     * 我的音乐界面的水平面板
     */
    @FXML
    private HBox HBox_bottom;

    /**
     * 我的音乐界面的表视图
     */
    @FXML
    private TableView<Music> TableView_songList;

    /**
     * 我的音乐界面的音乐ID表列
     */
    @FXML
    private TableColumn<Music, String> TableColumn_musicID;

    /**
     * 我的音乐界面的歌曲名字表列
     */
    @FXML
    private TableColumn<Music, String> TableColumn_songName;

    /**
     * 我的音乐界面的歌手表列
     */
    @FXML
    private TableColumn<Music, String> TableColumn_singer;

    /**
     * 我的音乐界面的专辑名字表列
     */
    @FXML
    private TableColumn<Music, String> TableColumn_album;

    /**
     * 我的音乐界面的歌曲长度表列
     */
    @FXML
    private TableColumn<Music, String> TableColumn_length;

    /**
     * 我的音乐界面播放当前所有歌曲按钮
     */
    @FXML
    private void Action_playAll() {
        handler.playAll();
    }

    /*
     * 各种 get 方法，用于获取相应的对象
     */

    /**
     * 获取我的音乐的主 VBox
     *
     * @return 返回我的音乐的主 VBox
     */
    public VBox getVBox_main() {
        return VBox_main;
    }

    /**
     * 获取我的音乐的顶部 AnchorPane
     *
     * @return 返回我的音乐的顶部 AnchorPane
     */
    public AnchorPane getAnchor_top() {
        return Anchor_top;
    }

    /**
     * 获取我的音乐的顶部 HBox
     *
     * @return 返回我的音乐的顶部 HBox
     */
    public HBox getHBox_top() {
        return HBox_myMusicTop;
    }

    /**
     * 获取我的音乐的我的音乐 Label
     *
     * @return 返回我的音乐的我的音乐 Label
     */
    public Label getLabel_myMusic() {
        return Label_myMusic;
    }

    /**
     * 获取我的音乐的播放全部 Button
     *
     * @return 返回我的音乐的播放全部 Button
     */
    public Button getButton_playAll() {
        return Button_playAll;
    }

    /**
     * 获取我的音乐的底部 AnchorPane
     *
     * @return 返回我的音乐的底部 AnchorPane
     */
    public AnchorPane getAnchor_bottom() {
        return Anchor_bottom;
    }

    /**
     * 获取我的音乐的底部 HBox
     *
     * @return 返回我的音乐的底部 HBox
     */
    public HBox getHBox_bottom() {
        return HBox_bottom;
    }

    /**
     * 获取我的音乐的顶部我的音乐 HBox
     *
     * @return 返回我的音乐的顶部我的音乐 HBox
     */
    public HBox getHBox_myMusicTop() {
        return HBox_myMusicTop;
    }

    /**
     * 获取我的音乐的 TableView 的一列
     *
     * @return 返回我的音乐的 TableView 的一列
     */
    public TableColumn<Music, String> getTableColumn_musicID() {
        return TableColumn_musicID;
    }

    /**
     * 获取我的音乐的 TableView 的一列
     *
     * @return 返回我的音乐的 TableView 的一列
     */
    public TableColumn<Music, String> getTableColumn_songName() {
        return TableColumn_songName;
    }

    /**
     * 获取我的音乐的 TableView 的一列
     *
     * @return 返回我的音乐的 TableView 的一列
     */
    public TableColumn<Music, String> getTableColumn_singer() {
        return TableColumn_singer;
    }

    /**
     * 获取我的音乐的 TableView 的一列
     *
     * @return 返回我的音乐的 TableView 的一列
     */
    public TableColumn<Music, String> getTableColumn_album() {
        return TableColumn_album;
    }

    /**
     * 获取我的音乐的 TableView 的一列
     *
     * @return 返回我的音乐的 TableView 的一列
     */
    public TableColumn<Music, String> getTableColumn_length() {
        return TableColumn_length;
    }

    /**
     * 获取我的音乐的 TableView
     *
     * @return 返回我的音乐的 TableView
     */
    public TableView<Music> getTableView_songList() {
        return TableView_songList;
    }

    /**
     * 初始化 MyMusicPageController
     */
    public void __init__() {

    }

    /**
     * 初始化 MyMusicPageController 的数据
     * 将 TableView 的每一列都绑定协议
     * 当添加歌曲时 能够成功更新每一列的数据
     */
    public void __initData__() {

        // 将每一个 TableColumn 都绑定了属性值协议
        // 当 TableView 更新时，每一列也能够更新
        TableColumn_musicID.setCellFactory((col) -> {
            TableCell<Music, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);

                    if (!empty) {
                        int rowIndex = this.getIndex() + 1;
                        this.setText(String.valueOf(rowIndex));
                    }
                }
            };
            return cell;
        });
        TableColumn_songName.setCellValueFactory(new PropertyValueFactory<>("musicTitle"));
        TableColumn_singer.setCellValueFactory(new PropertyValueFactory<>("musicSinger"));
        TableColumn_album.setCellValueFactory(new PropertyValueFactory<>("albumName"));
        TableColumn_length.setCellValueFactory(new PropertyValueFactory<>("musicTimeLength"));

        // 设置 TableView 鼠标点击事件
        getTableView_songList().setOnMouseClicked(new ClickAction());

        Platform.runLater(() -> {
            List<Song> songList = Song.jsonToSong();

            for (Song s : songList)
                mainGUI.getMyMusicPageController().getTableView_songList().getItems().add(Song.songToMusic(s));
        });
    }


}
