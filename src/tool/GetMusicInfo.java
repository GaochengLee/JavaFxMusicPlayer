/*
 * Copyright (c) 2018. 18-12-10 下午5:05.
 * @author 李高丞
 */

package tool;

import entity.ExtendedInfo;
import entity.Tag;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.flac.FlacFileReader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.flac.FlacTag;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;
import org.jaudiotagger.tag.images.Artwork;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;

/**
 * GetMusicInfo 这个类是一个工具类，主要是获得 MP3 文件的一些信息
 * 比如：MP3 文件的标签，一种是在文件头，一种是在文件尾。
 * 除此之外，还可以获得 FLAC 音乐文件的信息，但是暂时没有进行测试。
 *
 * @author 李高丞
 * @version 1.0 Beta
 */
public class GetMusicInfo {

    /*
     * jaudiotagger 是一个第三方包，能够获取 MP3，FLAC 等音乐文件格式的一系列信息。
     * jar包下载地址：
     * https://bitbucket.org/ijabz/jaudiotagger/src/master/README.md?fileviewer=file-view-default
     *
     * API文档：
     * http://www.jthink.net/jaudiotagger/javadoc/index.html
     *
     */


    /*
     * 每个 ID3V2.3 的标签都由一个标签头和若干个标签帧或一个扩展标签头组成。
     * 关于曲目的信息如标题、作者等都存放在不同的标签帧中，扩展标签头和标
     * 签帧并不是必要的，但每个标签至少要有一个标签帧。标签头和标签帧一起
     * 顺序存放在 MP3 文件的首部。
     *
     * MP3 的图片存储在 APIC 标签下
     *
     *
     * 帧标识
     * 用四个字符标识一个帧，说明一个帧的内容含义，常用的对照如下：
     * TIT2 = 标题 表示内容为这首歌的标题，下同
     * TPE1 = 作者
     * TALB = 专集
     * TRCK = 音轨 格式：N/M 其中 N 为专集中的第 N 首，M 为专集中共 M 首，N 和 M 为 ASCII 码表示的数字
     * TYER = 年代 是用 ASCII 码表示的数字
     * TCON = 类型 直接用字符串表示
     * COMM = 备注 格式："eng\0备注内容"，其中 eng 表示备注所使用的自然语言
     *
     *
     */

    /**
     * 静态方法 MP3Info
     * 获取 MP3 文件的标签信息
     *
     * @param path MP3 文件存储的路径
     * @return 返回一个便签的实体类
     */
    public static Tag MP3Info(String path) {

        // 声明一个 MP3 文件引用对象
        MP3File file;

        try {

            // 将这个 MP3 文件引用对象引用到存储在路径 path 的文件
            file = new MP3File(path);

            // 获取 MP3 文件存储在标签的名字
            String songName = file.getID3v2Tag().frameMap.get("TIT2").toString();

            // 获取 MP3 文件存储在标签的作者
            String artist = file.getID3v2Tag().frameMap.get("TPE1").toString();

            //获取 MP3 文件存储在标签的专辑
            String album = file.getID3v2Tag().frameMap.get("TALB").toString();

            // 获取 MP3 文件存储在标签的时长
            String length = file.getMP3AudioHeader().getTrackLengthAsString();

            //todo file.getMP3AudioHeader BitRate(比特率) Channels(声道) SampleRate(采样率) encodingType(文件格式) format(压缩格式)
            //System.out.println(file.getID3v2Tag());

            // 删除标签中的无用字节
            if (songName != null)
                songName = songName.substring(6, songName.length() - 3);
            if (artist != null)
                artist = artist.substring(6, artist.length() - 3);
            if (album != null)
                album = album.substring(6, album.length() - 3);

            // 建立一个标签的实体类，将从 MP3 中获取的标签值存储到这个标签实体类中
            Tag tag = new Tag();
            tag.setSongName(songName);
            tag.setAlbum(album);
            tag.setArtist(artist);
            tag.setLength(length);

            return tag;

        } catch (Exception e) {
            // 显示读取错误信息
            System.err.println("获取MP3信息出错");
        }
        // 返回一个空标签
        return new Tag();
    }

    /**
     * 获取 MP3 文件存储在表头的歌曲封面
     *
     * @param path MP3 文件所在的位置
     */
    public static void getMP3Image(String path) {
        //Image image;
        try {
            // 获取 MP3 文件
            File sourceFile = new File(path);

            // 获取 MP3 文件
            MP3File mp3File = new MP3File(sourceFile);

            // 获取 MP3 文件的标签
            AbstractID3v2Tag tag = mp3File.getID3v2Tag();

            // 获取 MP3 文件的歌曲封面
            AbstractID3v2Frame frame = (AbstractID3v2Frame) tag.getFrame("APIC");
            FrameBodyAPIC body = (FrameBodyAPIC) frame.getBody();

            // 获取 MP3 文件的字节数据
            byte[] imageData = body.getImageData();

            //image = Toolkit.getDefaultToolkit().createImage(imageData, 0, imageData.length);

            // MP3 图片的存储路径
            String storePath = path;

            // MP3 图片的存储路径
            storePath = storePath.substring(0, storePath.length() - 3);
            storePath += "png";

            // 声明文件输出流
            FileOutputStream fos = new FileOutputStream(storePath);

            // 写出文件
            fos.write(imageData);

            // 关闭输出流
            fos.close();

        } catch (Exception e) {
            // 声明错误信息
            System.err.println("读取MP3文件图片出错");
        }

    }

    /**
     * 获取音乐的扩展信息
     *
     * @param path 音乐的保存路径
     * @return 返回扩展信息
     */
    public static ExtendedInfo getExtendedInfo(String path) {
        // MP3文件
        MP3File file;
        // 扩展信息
        ExtendedInfo info = null;
        String Copyright = "空";
        String Genre = "空";

        try {
            // 文件路径
            File f = new File(path);
            // MP3文件
            file = new MP3File(path);
            // 扩展信息
            info = new ExtendedInfo();

            // 获得扩展信息
            String BitRate = file.getMP3AudioHeader().getBitRate();
            String Channels = file.getMP3AudioHeader().getChannels();
            String SampleRate = file.getMP3AudioHeader().getSampleRate();
            String EncodingType = file.getMP3AudioHeader().getEncodingType();
            String Format = file.getMP3AudioHeader().getFormat();
            String Size = String.valueOf(f.length());

            // 获得扩展信息 并清除无用字符
            if (file.getID3v2Tag().frameMap.containsKey("TCOP")) {
                Copyright = file.getID3v2Tag().frameMap.get("TCOP").toString();
                Copyright = Copyright.substring(6, Copyright.length() - 3);
            }
            // 获得扩展信息 并清除无用字符
            if (file.getID3v2Tag().frameMap.containsKey("TCON")) {
                Genre = file.getID3v2Tag().frameMap.get("TCON").toString();
                Genre = Genre.substring(6, Genre.length() - 3);
            }

            // 设置扩展信息
            info.setBitRate(BitRate);
            info.setChannels(Channels);
            info.setSampleRate(SampleRate);
            info.setEncodingType(EncodingType);
            info.setFormat(Format);
            info.setSize(Size);
            info.setGenre(Genre);
            info.setCopyright(Copyright);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return info;
    }


    /*
     * 阅读 JAudiotagger API，使用 FlacFileReader 对象的 read 方法读取一个 Flac 文件，
     * 返回一个 AudioFile（接口）类型的对象（实现了该接口的类的实例），
     * 再通过 AudioFile 中的方法 getAudioHeader 和 getTag 分别获得 AudioHeader（接口）类型的对象和 FlacTag 类（实现了 Tag 接口）的对象。
     * 利用获得的 AudioHeader 和 FlacTag 的对象即可获得想要的元数据（ Metadata ）信息，
     * 包括采样率（ SampleRate ）、制作格式或制作技术（ Format ）、单曲名（ TITLE ）、单曲艺术家（ ARTIST ）、
     * 专辑名（ ALBUM ）、专辑艺术家（ ALBUM_ARTIST ）、音轨号（ TRACK ）、语言（ LANGUAGE ）、版权方（ COPYRIGHT ）等等，
     * 使用 FlacTag 对象的 getFirstArtwork 方法可以获得图片。
     */


    /**
     * 获取 FLAC 文件的标签
     *
     * @param path FLAC 文件的存储位置
     * @return 一个标签实体类
     */
    public static Tag FLACInfo(String path) {
        try {

            // 声明一个 FLAC 文件的读写器
            FlacFileReader flacFileReader = new FlacFileReader();

            // 获取 FLAC 文件
            AudioFile audioFile = flacFileReader.read(new File(path));

            // 获取 FLAC 文件的标签
            org.jaudiotagger.tag.Tag tag = audioFile.getTag();

            /*
             * FLAC 文件的标签在被提取出来时存到了映射表里
             */

            // 获取 FLAC 文件表头的信息
            String songName = tag.getFirst(FieldKey.TITLE);
            String artist = tag.getFirst(FieldKey.ARTIST);
            String album = tag.getFirst(FieldKey.ALBUM);

            // 获取 FLAC 文件的时长
            int trackLength = audioFile.getAudioHeader().getTrackLength();
            int minutes = trackLength / 60;
            int seconds = trackLength % 60;

            // 声明一个标签实体类
            Tag tag1 = new Tag();

            //将 FLAC 文件的标签信息存储到标签实体类中
            tag1.setSongName(songName);
            tag1.setArtist(artist);
            tag1.setAlbum(album);
            tag1.setLength(minutes + ":" + seconds);

            return tag1;

        } catch (Exception e) {
            // 声明错误信息
            System.err.println("读取FLAC信息出错");
        }
        // 返回空标签实体类
        return new Tag();
    }

    /**
     * 获取存储在 FLAC 文件中的歌曲封面
     *
     * @param path
     * @return
     */
    public static Image getFLACImage(String path) {
        Image image = null;

        try {

            // 获取 FLAC 文件
            FlacFileReader flacFileReader = new FlacFileReader();
            AudioFile audioFile = flacFileReader.read(new File(path));

            // 读取 FLAC 文件的标签
            FlacTag tag = (FlacTag) audioFile.getTag();

            // 获取 FLAC 文件的封面（艺术作品）
            Artwork artwork = tag.getFirstArtwork();

            // 获取封面的字节数据
            byte[] imageData = artwork.getBinaryData();
            image = Toolkit.getDefaultToolkit().createImage(imageData, 0, imageData.length);

            // 封面图片的存储位置
            String storePath = path.substring(0, path.length() - 4) + "png";

            // 声明文件输出流
            FileOutputStream fos = new FileOutputStream(storePath);

            // 将封面数据输出
            fos.write(imageData);

            // 关闭输出流
            fos.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }
}
