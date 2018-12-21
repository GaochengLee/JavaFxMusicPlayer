/*
 * Copyright (c) 2018. 18-12-13 下午2:52.
 * @author 李高丞
 */

package entity;

import java.text.DecimalFormat;

/**
 * @author 李高丞
 * @version 1.0 Beta
 */
public class ExtendedInfo {

    /**
     * 扩展信息：比特率
     */
    private String BitRate;
    /**
     * 扩展信息：声道
     */
    private String Channels;
    /**
     * 扩展信息：采样率
     */
    private String SampleRate;
    /**
     * 扩展信息：文件格式
     */
    private String EncodingType;
    /**
     * 扩展信息：压缩方式
     */
    private String Format;
    /**
     * 扩展信息：文件大小
     */
    private String Size;
    /**
     * 扩展信息：歌曲流派
     */
    private String Genre;
    /**
     * 扩展信息：歌曲版权声明
     */
    private String Copyright;

    /**
     * 默认构造器
     */
    public ExtendedInfo() {
        this.BitRate = "空";
        this.Channels = "空";
        this.SampleRate = "空";
        this.EncodingType = "空";
        this.Format = "空";
        this.Size = "空";
        this.Genre = "空";
        this.Copyright = "空";
    }

    /**
     * 获取扩展信息的比特率
     */
    public String getBitRate() {
        return BitRate;
    }

    /**
     * 设置扩展信息的比特率
     */
    public void setBitRate(String bitRate) {
        BitRate = bitRate + " kbs";
    }

    /**
     * 获取扩展信息的声道
     */
    public String getChannels() {
        return Channels;
    }

    /**
     * 设置扩展信息的声道
     */
    public void setChannels(String channels) {
        Channels = channels;
    }

    /**
     * 获取扩展信息的采样率
     */
    public String getSampleRate() {
        return SampleRate;
    }

    /**
     * 设置扩展信息的采样率
     */
    public void setSampleRate(String sampleRate) {
        SampleRate = sampleRate + " HZ";
    }

    /**
     * 获取扩展信息的文件格式
     */
    public String getEncodingType() {
        return EncodingType;
    }

    /**
     * 设置扩展信息的文件格式
     */
    public void setEncodingType(String encodingType) {
        EncodingType = encodingType;
    }

    /**
     * 获取扩展信息的文件压缩格式
     */
    public String getFormat() {
        return Format;
    }

    /**
     * 设置扩展信息的文件压缩格式
     */
    public void setFormat(String format) {
        Format = format;
    }

    /**
     * 获取扩展信息的文件大小
     */
    public String getSize() {
        return Size;
    }

    /**
     * 设置扩展信息的大小
     */
    public void setSize(String size) {
        Size = formatSize(size);
    }

    /**
     * 获取扩展信息的文件流派
     */
    public String getGenre() {
        return Genre;
    }

    /**
     * 设置扩展信息的文件流派
     */
    public void setGenre(String genre) {
        Genre = genre;
    }

    /**
     * 获取扩展信息的版权声明
     */
    public String getCopyright() {
        return Copyright;
    }

    /**
     * 设置扩展信息的版权声明
     */
    public void setCopyright(String copyright) {
        Copyright = copyright;
    }

    /**
     * 格式化文件大小
     *
     * @param size 文件大小
     * @return 格式化后的大小
     */
    private String formatSize(String size) {
        DecimalFormat format = new DecimalFormat("0.00");
        double temp = Double.parseDouble(size);
        temp = temp / 1024 / 1024;

        return format.format(temp) + " MB";
    }

    /**
     * 重写了 toString 方法，使其能够输出扩展信息的内容
     *
     * @return 扩展信息的字符串
     */
    @Override
    public String toString() {
        return "ExtendInfo [BitRate=" + BitRate + ",Channels=" + Channels
                + ",SampleRate=" + SampleRate + ",EncodingType=" + EncodingType
                + ",Format=" + Format + ",Size=" + Size + ",Genre=" + Genre
                + ",Copyright=" + Copyright + "]";
    }
}
