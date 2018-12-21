/*
 * Copyright (c) 2018. 18-12-10 下午4:17.
 * @author 李高丞
 */

package controller;

import GUI.Main;
import entity.Music;
import handler.ClickAction;
import handler.Handler;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 搜索列表面板的控制器 从FXML中获取 fx:id 设置一系列的 get 方法
 * 初始化了各个按钮的 Action，将事件处理放在了 handler 类里。
 *
 * @author 李高丞
 * @version 1.0 Beta
 */
public class SearchPageController implements Controller {

    /**
     * 获取 Main 类
     */
    private static Main mainGUI = Main.getMainGUI();

    /**
     * 搜索界面的主面板
     */
    @FXML
    private AnchorPane Anchor_searchMain;

    /**
     * 搜索界面的垂直面板
     */
    @FXML
    private VBox VBox_searchMain;

    /**
     * 搜索界面的水平面板
     */
    @FXML
    private HBox HBox_topPane;

    /**
     * 搜索界面的搜索标签
     */
    @FXML
    private Label Label_searchSong;

    /**
     * 搜索界面的表视图
     */
    @FXML
    private TableView<Music> TableView_searchList;

    /**
     * 搜索界面的歌名表列
     */
    @FXML
    private TableColumn<Music, String> TableColumn_searchSongName;

    /**
     * 搜索界面的歌手表列
     */
    @FXML
    private TableColumn<Music, String> TableColumn_searchSinger;

    /**
     * 搜索界面的专辑表列
     */
    @FXML
    private TableColumn<Music, String> TableColumn_searchAlbum;

    /**
     * 搜索界面的歌曲长度表列
     */
    @FXML
    private TableColumn<Music, String> TableColumn_searchTime;

    /*
     * 各种 get 方法，用于获取相应的对象
     */

    /**
     * 获得搜索界面的主 Pane
     *
     * @return 返回搜索界面的主 Pane
     */
    public AnchorPane getAnchor_searchMain() {
        return Anchor_searchMain;
    }

    /**
     * 获得搜索界面的主 VBox
     *
     * @return 返回搜索界面的主 VBox
     */
    public VBox getVBox_searchMain() {
        return VBox_searchMain;
    }

    /**
     * 获得搜索界面的上方的 HBox
     *
     * @return 返回搜索界面上方的 HBox
     */
    public HBox getHBox_topPane() {
        return HBox_topPane;
    }

    /**
     * 获得搜索界面的搜索 Label
     *
     * @return 返回搜索界面的搜索 Label
     */
    public Label getLabel_searchSong() {
        return Label_searchSong;
    }

    /**
     * 获得搜索界面的 TableView
     *
     * @return 返回搜索界面的 TableView
     */
    public TableView<Music> getTableView_searchList() {
        return TableView_searchList;
    }

    public TableColumn<Music, String> getTableColumn_searchSongName() {
        return TableColumn_searchSongName;
    }

    /**
     * 获得搜索界面 TableView 的一列
     *
     * @return 返回搜索界面 TableView 的一列
     */
    public TableColumn<Music, String> getTableColumn_searchSinger() {
        return TableColumn_searchSinger;
    }

    /**
     * 获得搜索界面 TableView 的一列
     *
     * @return 返回搜索界面 TableView 的一列
     */
    public TableColumn<Music, String> getTableColumn_searchAlbum() {
        return TableColumn_searchAlbum;
    }

    /**
     * 获得搜索界面 TableView 的一列
     *
     * @return 返回搜索界面 TableView 的一列
     */
    public TableColumn<Music, String> getTableColumn_searchTime() {
        return TableColumn_searchTime;
    }

    /**
     * 实现接口的方法
     *
     * @return 获得搜索界面的 TableView
     */
    @Override
    public TableView<Music> getTableView_songList() {
        return TableView_searchList;
    }

    /**
     * 初始化 SearchPageController
     */
    public void __init__() {
        __initData__();
        __initAction__();
    }

    /**
     * 初始化 SearchPageController 的数据
     * 将 TableView 的每一列都绑定协议
     * 当添加歌曲时 能够成功更新每一列的数据
     */
    private void __initData__() {

        // 将每一个 TableColumn 都绑定了属性值协议
        // 当 TableView 更新时，每一列也能够更新
        TableColumn_searchSongName.setCellValueFactory(new PropertyValueFactory<>("musicTitle"));
        TableColumn_searchSinger.setCellValueFactory(new PropertyValueFactory<>("musicSinger"));
        TableColumn_searchAlbum.setCellValueFactory(new PropertyValueFactory<>("albumName"));
        TableColumn_searchTime.setCellValueFactory(new PropertyValueFactory<>("musicTimeLength"));
    }

    /**
     * 初始化 SearchPageController 的事件处理
     */
    private void __initAction__() {
        // 设置播放列表鼠标点击事件
        TableView_searchList.setOnMouseClicked(new ClickAction());
    }

}

