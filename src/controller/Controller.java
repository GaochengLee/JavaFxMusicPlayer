/*
 * Copyright (c) 2018. 18-12-10 下午3:04.
 * @author 李高丞
 */

package controller;

import entity.Music;
import javafx.scene.control.TableView;

/**
 * 这个接口是指要求实现了一个方法 能够获取 TableView
 */
public interface Controller {
    /**
     * 获取TableView
     */
    TableView<Music> getTableView_songList();
}
