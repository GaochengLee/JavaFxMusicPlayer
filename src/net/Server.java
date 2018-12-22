/*
 * Copyright (c) 2018. 18-12-10 下午 4:57.
 * @author 李高丞
 */

package net;

import com.google.gson.Gson;
import entity.Song;
import tool.GetMusicInfo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Server 类
 * <p>
 * 这个类是服务器端，负责接受客户端发送来的数据和
 * 向客户端发送音乐文件
 * 服务器端实现了多线程，能够处理多个用户的操作
 *
 * @author 李高丞
 * @version 1.0 Beta
 */
public class Server extends Thread {

    /**
     * 服务器所有音乐文件存放的目录
     */
    private static final String PATH = "C:\\Users\\hasee\\Downloads\\test\\";

    /**
     * 服务器套接字
     */
    private static ServerSocket serverSocket;

    /**
     * 客户端套接字
     */
    private Socket clientSocket;

    /**
     * 所有音乐文件存放的链表
     */
    private static LinkedList<Song> list = new LinkedList<>();

    private static ArrayList<PrintWriter> clientOutputStream = new ArrayList<>();
    private static LinkedList<Socket> client = new LinkedList<>();

    /**
     * 服务器端启动的 main 方法
     *
     * @param args 命令行参数
     * @throws Exception 抛出异常，不想处理
     */
    public static void main(String[] args) throws Exception {
        // 将所有的音乐文件存放在链表中
        loopTheFolder(list);

        // 初始化服务器套接字
        serverSocket = new ServerSocket(5432);

        // 启动服务器处理线程
        new serverHandler().start();
    }

    /**
     * 服务器构造器
     *
     * @param clientSocket 客户端套接字
     */
    public Server(Socket clientSocket) {
        // 初始化客户端套接字
        this.clientSocket = clientSocket;

        // 显示客户端连接
        System.out.println("New connection: " + clientSocket.getInetAddress().getHostAddress() +
                ":" + clientSocket.getPort() + " connect the server!");
    }

    /**
     * 服务器的 run 方法
     * 服务器通过这个方法来处理客户端发送来的信息
     */
    @Override
    public void run() {

        // 读取客户端发送信息的引用
        BufferedReader br;

        // 存储客户端发送的信息
        String msg;

        try {
            // 初始化引用，从客户端套接字获取输入流
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // 当输入流不为空的时候
            while ((msg = br.readLine()) != null) {
                System.out.println(msg);

                // 分析客户端发送来的信息
                String[] split = msg.split(" ");

                // 如果是搜索命令
                if (split[1].equals("search")) {
                    System.out.println(split[2]);

                    // 创建一个 json 文件，发送给客户端进行读操作
                    createJson(split[2]);
                    sendJson(clientSocket, split[2]);
                }
                // 如果是发送命令
                if (split[1].equals("send")) {
                    System.out.println(split[3]);

                    // 获取根据搜索关键字发送过来的信息，生成一个链表来存放服务器端搜索到的信息
                    LinkedList<Song> songList = getByKeyword(split[2], list);

                    Song song = getSong(split[3], split[2], songList);
                    System.out.println(song);


                    // 将服务器端搜索到的文件发送出去
                    sendFile(clientSocket, new File(song.getPath() + "\\" + song.getTag().getArtist() +
                            " - " + song.getTag().getSongName() + ".mp3"));
                }
                if (split[1].equals("message")) {
                    String name = clientSocket.getInetAddress().getHostAddress() + ":" +
                            clientSocket.getPort();
                    String message = split[3];

                    System.out.println("Message [from client " +
                            clientSocket.getInetAddress().getHostAddress() + ":" +
                            clientSocket.getPort() + "]: " + message);

                    sendToEveryClient(message, name);
                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("error: " + e.getMessage());
            System.out.println("Client " + clientSocket.getInetAddress().getHostAddress()
                    + ":" + clientSocket.getPort() + " quit the server!");
        }

    }

    /**
     * 发送 json 文件到客户端进行读操作
     *
     * @param socket  客户端套接字
     * @param keyword 搜索的关键词
     * @throws IOException 网络异常
     */
    private static void sendJson(Socket socket, String keyword) throws IOException {

        // where the server create the file and the server music base
        File file = new File(PATH + keyword + ".json");
        // 发送文件
        sendFile(socket, file);
    }

    /**
     * 将一个文件发送到客户端
     *
     * @param socket 客户端套接字
     * @param file   要发送的文件
     * @throws IOException 网络异常
     */
    private static void sendFile(Socket socket, File file) throws IOException {
        // create the file stream and send the file to the client
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        FileInputStream fis = new FileInputStream(file);

        // 如果文件不存在，发送错误信息
        if (!file.exists()) {
            String error = "File " + file + " does not exist...\n";
            int length = error.length();
            for (int i = 0; i < length; i++) {
                dos.write((int) error.charAt(i));
            }
            System.out.println(error);
        }

        // 声明文件的长度
        int length;

        // 要发送文件的字节大小（最大30MB）
        byte[] sendData = new byte[31457280];

        System.out.println("Send File Name: " + file.getName());

        // 先给客户端发送文件名字和文件大小
        dos.writeUTF(file.getName() + "/" + file.length());

        // 给客户的发送文件
        while ((length = fis.read(sendData, 0, sendData.length)) > 0) {
            System.out.println("Send length: " + length + " B");
            dos.write(sendData, 0, length);
            dos.flush();
        }
        // 关闭文件读取流
        fis.close();
    }

    /**
     * 根据客户端发送过来的关键字，在服务器端进行搜索
     *
     * @param keyword 客户端发送的关键字
     * @throws IOException 网络异常
     */
    private static void createJson(String keyword) throws IOException {

        // 根据关键字来获得歌曲的链表
        LinkedList<Song> songList = getByKeyword(keyword, list);

        // 使用 Google 的 Java 第三方类库创建 json 文件
        Gson gson = new Gson();

        // 创建文件输出流
        FileOutputStream fos = new FileOutputStream(PATH + keyword + ".json");

        // 生成 json 数据
        String temp = gson.toJson(songList);

        // 将 json 数据写入到文件
        fos.write(temp.getBytes());

        //关闭文件读取流
        fos.close();
    }

    /**
     * 递归整个文件夹
     *
     * @param songList 将搜索到的歌曲添加到需要添加到的链表
     */
    private static void loopTheFolder(LinkedList<Song> songList) {

        // 存放音乐文件的目录
        File file = new File("C:\\Users\\hasee\\Downloads\\test");

        // 搜索文件
        for (File files : file.listFiles()) {
            // 如果是文件夹，进入文件夹再搜索文件
            if (files.isDirectory()) {
                loopTheFolder(songList);
            }
            // 如果是文件，判断是否为 MP3 文件， 是就将其添加到链表中
            if (files.isFile()) {
                String name = files.getName();
                Song song = new Song();

                if (name.matches(".*(?i)mp3$")) {
                    // 设置实体类 song 的标签和地址
                    song.setTag(GetMusicInfo.MP3Info(files.getAbsolutePath()));
                    song.setPath(file.getAbsolutePath());
                    songList.add(song);
                }
            }
        }
    }

    /**
     * 根据关键字来搜索文件夹，然后返回一个存有搜索到的文件的链表
     *
     * @param keyword  关键词
     * @param songList 需要保存的链表
     * @return 一个存有搜索到的文件的链表
     */
    private static LinkedList<Song> getByKeyword(String keyword, LinkedList<Song> songList) {
        // 创建一个新的链表
        LinkedList<Song> newList = new LinkedList<>();

        // 如果匹配关键词，就将其添加到新链表中
        for (Song song : songList) {
            if (song.getTag().getSongName().contains(keyword)) {
                System.out.println(song.getTag());
                newList.add(song);
            }
        }
//        System.out.println(newList.size());
        return newList;
    }

    private static Song getSong(String singer, String songName, List<Song> songList) {
        for (Song s : songList) {
            if (s.getTag().getArtist().equals(singer) && s.getTag().getSongName().equals(songName))
                return s;
        }
        return null;
    }

    /**
     * 服务器帮助类，继承了线程
     * 帮助服务器处理多客户链接
     */
    static class serverHandler extends Thread {
        @Override
        public void run() {
            System.out.println("Server started... wait ");
            while (true) {
                try {
                    // 接受客户端套接字
                    Socket socket = serverSocket.accept();
                    // 启动服务器线程
                    new Server(socket).start();

                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                    clientOutputStream.add(printWriter);
                    client.add(socket);

                } catch (IOException e) {
                    //e.printStackTrace();
                    System.out.println("Some problem happen !");
                    System.exit(1);
                }
            }
        }
    }

    private static void sendToEveryClient(String message, String name) {
        Iterator<PrintWriter> it = clientOutputStream.iterator();
        while (it.hasNext()) {
            try {
                PrintWriter writer = it.next();
                writer.println("From" + name + " said : " + message);
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
