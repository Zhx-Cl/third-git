import java.io.Serializable;
import java.util.Scanner;

public class Test1 {
    public static void main(String[] args) {
        System.out.println(int.class);
        int a = 10;
        final int finalA = a;
        class InnerOfMain{
            private int age;
            public void setAge(){
                this.age = finalA;
            }
        }

        class Inner2OfMain{

        }
        a = 20;
        new InnerOfMain().setAge();
    }
}

class A{
    private String name;
    private int age = getAge();

    public A(String name, int age){
        System.out.println("super");
        this.name = name;
        this.age = age;
    }

    public int getAge(){
        System.out.println("super getAge");
        return 0;
    }

    private String getName(){
        return "";
    }
}

class B extends A implements Serializable {


    private String address;

    {
        System.out.println("son init");
    }
    public B(String name, int age, String address) {
        super(name, age);
        System.out.println("son");
        this.address = address;
    }

    public int getAge(){
        System.out.println("son getAge");
        return 0;
    }

    public class Inner{
        private int a = 10;
        public int getAge(){
            return a;
        }
    }
}
