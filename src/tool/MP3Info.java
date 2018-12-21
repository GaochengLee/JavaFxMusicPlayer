package tool;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

/**
 * @author 李高丞
 * @version 1.0 Beta
 */
public class MP3Info {
    public static void main(String[] args) throws Exception {
//        MP3File file = new MP3File("B.o.B,Bruno Mars - Nothing On You.mp3");
//
//        AbstractID3v2 tag = file.getID3v2Tag();
//
//        System.out.println(tag);
//        System.out.println();

        GetMusicInfo.getMP3Image("B.o.B,Bruno Mars - Nothing On You.mp3");

//        File MP3File = new File("张悬 - 宝贝 (in a day).mp3");
//
//        try {
//            MP3Info info = new MP3Info(MP3File);
//            info.setCharset("GBK");
//
//            System.out.println(info.getSongName());
//            System.out.println(info.getArtist());
//            System.out.println(info.getAlbum());
//            System.out.println(info.getComment());
//            System.out.println(info.getYear());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private String charset = "UTF-8";

    private byte[] buf;

    public MP3Info(File mp3) throws IOException {
        buf = new byte[128];

        RandomAccessFile raf = new RandomAccessFile(mp3, "r");
        raf.seek(raf.length() - 128);
        raf.read(buf);

        raf.close();

        if (buf.length != 128) {
            throw new IOException("no");
        }

        if (!"TAG".equalsIgnoreCase(new String(buf, 0, 3))) {
            throw new IOException("not");
        }
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSongName() {
        try {
            return new String(buf, 3, 30, charset).trim();
        } catch (UnsupportedEncodingException e) {
            return new String(buf, 3, 30).trim();
        }
    }

    public String getArtist() {
        try {
            return new String(buf, 33, 30, charset).trim();
        } catch (UnsupportedEncodingException e) {
            return new String(buf, 33, 30).trim();
        }
    }

    public String getAlbum() {
        try {
            return new String(buf, 63, 30, charset).trim();
        } catch (UnsupportedEncodingException e) {
            return new String(buf, 63, 30).trim();
        }
    }

    public String getYear() {
        try {
            return new String(buf, 93, 4, charset).trim();
        } catch (UnsupportedEncodingException e) {
            return new String(buf, 93, 4).trim();
        }
    }

    public String getComment() {
        try {
            return new String(buf, 97, 28, charset).trim();
        } catch (UnsupportedEncodingException e) {
            return new String(buf, 97, 28).trim();
        }
    }
}
