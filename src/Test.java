import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Test{
    public static void main(String[] args){
        Object [] array = Stream.iterate(1.0, v -> v*2.0)
                                .peek(e -> System.out.println("value:"+e))
                                .limit(20).toArray();
    }

}

class MyData{
    int A = 0;
    //volatile int A = 0;

    public void ATo60(){
        this.A = 60;
        String a = new Random().nextInt(2) == 0? "A": "C";
        switch(a){
            case "A":
                System.out.println("haha");
                break;
            case "C":
                System.out.println("C");
                break;
        }
    }

}

abstract class SonData extends MyData{
    private String name;
    private int age;
    SonData(){
        this.name = "zhx";
        this.age = 18;
        System.out.println("zhx");
    }

    protected abstract void test();
}

class SonsonData extends SonData{
    SonsonData(){
        super();
    }

    @Override
    protected void test() {

    }
}

interface Interface{
    public abstract void print();

    default void printf(){

    }
}