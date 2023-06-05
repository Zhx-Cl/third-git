
public interface TestInterface01 {
    void print();
}

interface inter1{
    void print2();
}

interface inter2 extends TestInterface01, inter1{

}


class Aa implements inter2{

    @Override
    public void print() {

    }

    @Override
    public void print2() {

    }
}