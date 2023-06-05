import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public class Test03 {
    public static void main(String[] args) throws Exception{
        byte[] gbks = "周鸿翔".getBytes("gbk");
        String s = new String(gbks, "UTF-8");
       System.out.println(new String(s.getBytes("utf-8"), "utf-8"));
        byte[] a= "中".getBytes("GBK");//长度为2

        byte[] b= "中".getBytes("UTF-8");//长度为3

        byte[] c= "中".getBytes("ISO8859-1");//长度为1

        String s_gbk = new String(a,"GBK");
        String s_utf8 = new String(b,"UTF-8");
        String s_iso88591 = new String(c,"ISO8859-1");

        System.out.println(s_gbk.equals("中"));
    }

    public static void a(String s, int s1){
        Integer a = 10;

        for (int i = 0; i < 1000; i++) {
            a += i;
            a++;
        }

        System.out.println(a);
    }
}

class Test03A implements Serializable {
    public static final long serialVersionUID = 13L;



    @Override
    public String toString() {
        return "Test03A{" +
                "serialVersionUID=" + serialVersionUID +
                '}';
    }
}

abstract class Test03B{
    public abstract void a();
}
