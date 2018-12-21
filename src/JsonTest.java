import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author 李高丞
 * @version 1.0 Beta
 */
public class JsonTest {
    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();
        User user = new User("lee", 25);

        String jsonObject = gson.toJson(user);

        System.out.println(jsonObject);

        User user1 = gson.fromJson(jsonObject, User.class);

        System.out.println(user1);

        String[] split = user1.toString().split(" ");

        System.out.println(split[0] + " 123456 " + split[1]);

        Files.createTempFile(Paths.get("D:\\"), "tool.ExcelTool", ".json");

        FileOutputStream fos = new FileOutputStream("D:\\tool.ExcelTool.json");
        fos.write(jsonObject.getBytes());
        fos.close();

        FileInputStream fis = new FileInputStream("D:\\tool.ExcelTool.json");
        byte[] fileData = fis.readAllBytes();

        System.out.println(new String(fileData));
    }
}

class User {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "name=" + name + " age=" + age;
    }
}
