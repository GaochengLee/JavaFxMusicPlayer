package tool;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author 李高丞
 * @version 1.0 Beta
 */
public class test {

    public static void main(String[] args) {
        System.out.println(GetMusicInfo.getExtendedInfo("C:\\Users\\hasee\\Downloads\\tool.ExcelTool\\Stay Gold - 大橋トリオ.mp3"));
        //System.out.println(GetMusicInfo.MP3Info("C:\\Users\\hasee\\Downloads\\tool.ExcelTool\\Stay Gold - 大橋トリオ.mp3"));
    }
//    public static void main(String[] args) {
//        try {
//            //FileInputStream需要一个文件地址
//            FileInputStream fis = new FileInputStream("张悬 - 宝贝 (in a day).mp3");
////            System.out.println(fis.available());
////            System.out.println((char) fis.read());
////            System.out.println(fis.available());
//            byte[] bytes = new byte[3];
//            int length = fis.read(bytes);
//
//            System.out.println(length);
//
//            String s = new String(bytes, 0, length);
//            System.out.println(s);
//            System.out.println(fis.read());
//            System.out.println(fis.read());
//            System.out.println(fis.read());
//            System.out.println("===========");
//            byte[] size = new byte[4];
//            fis.read(size);
//            int l = toSize(size);
//            System.out.println(l);
//            byte[] data = new byte[l-10];
//            fis.read(data);
//            int index = 0;
//            while (index < data.length){
//                System.out.println(new String(data, index, 4));
//                index+=4;
//                int nameSize = toSize2(data, index);
//                index+=4;
//                index+=2;
//                System.out.println(nameSize);
//                System.out.println(new String(data, index, nameSize));
//                index+=nameSize;
//            }
////            fis.skip(fis.available() - 128);
////            byte[] bytes = new byte[128];
////            fis.read(bytes);
////            System.out.println(new String(bytes, 0, 3));
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    public static int toSize(byte[] bytes){
//        int result = bytes[0] << 21
//                | bytes[1] << 14
//                | bytes[2] << 7
//                | bytes[3];
//        return result;
//    }
//    public static int toSize2(byte[] data, int offset) {
//        int result = data[offset] << 24
//                | data[offset + 1] << 16
//                | data[offset + 2] << 8
//                | data[offset + 3];
//        return result;
//
//    }
}

