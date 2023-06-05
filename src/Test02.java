import java.io.*;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.*;

public class Test02 {

    private static boolean flag = true;

    public static void main(String[] args) throws Exception {
        User user = new User();
        Entry zhx = new Entry("zhx", user);
        System.out.println(zhx.get());
        System.out.println(zhx.name);
        user = null;
        System.gc();
        System.out.println(zhx.get());
        System.out.println(zhx.name);
    }

    static class Entry extends WeakReference<User>{
        String name;
        public Entry(String name,User user) {
            super(user);
            this.name = name;
        }
    }

    static class User{

    }

    public static int binarySearch(int[] nums, int target, boolean isLeft){
        int left = 0;
        int right = nums.length - 1;
        int mid;
        int ans = 0;

        while(left <= right){
            mid = left + (right - left) / 2;

            if (nums[mid] > target || (isLeft && nums[mid] == target)){
                ans = mid;
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }

        return ans;
    }

    private static void testVolatile() throws InterruptedException {
        new Thread(() -> {
            System.out.println("in");

            while(flag){
                System.out.println(flag);
            }

            System.out.println("end");
        }).start();

        Thread.sleep(2000);
        System.out.println("modify");
        flag = false;
    }

    private static void testCombine() {
        CompletableFuture<Integer> _20 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("20 结束");
            return 20;
        });

        CompletableFuture<Integer> _10 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("10 结束");
            return 10;
        });

        CompletableFuture<Integer> res = _20.thenCombine(_10, Integer::sum);

        System.out.println(res.join());
    }

    private static void testApplayEither() throws InterruptedException, ExecutionException {
        CompletableFuture<String> A = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "A";
        });

        CompletableFuture<String> B = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "B";
        });

        CompletableFuture<String> winner = A.applyToEither(B, v -> {
            System.out.println(v + " is winner");
            return v;
        });

        System.out.println(winner.get());
    }

    private static void testThenApplay() throws InterruptedException {
        CompletableFuture.supplyAsync(() ->{
            System.out.println("111");
            return 1;
        }).thenApply(v -> {
            System.out.println("222");
            int a = 1/0;
            return 2 + v;
        }).thenApply(v -> {
            System.out.println("333");

            return 3 + v;
        }).whenComplete((v, e) -> {
            System.out.println(v);
        }).exceptionally(throwable -> {
            throwable.printStackTrace();
            return 0;
        });

        Thread.sleep(1000);
    }

    private static void testWhenComplete() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int a = 1/0;
            return "132";
        }).whenComplete((value, e) -> {
            if (e == null) {
                System.out.println(value);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            return null;
        });

        System.out.println("other");

        executorService.shutdown();
    }

    private static void testCompletableFutureSupply() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "hello world";
        }, executorService);

        System.out.println(stringCompletableFuture.get());

        executorService.shutdown();
    }


    private static void completableFutrue() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        CompletableFuture<Void> hello_world = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "hello world");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, executorService);

        System.out.println(hello_world.get());

        executorService.shutdown();
    }

    private static void testFutureTask() throws InterruptedException, java.util.concurrent.ExecutionException {
        FutureTask<String> hello_world = new FutureTask<>(() -> {
            Thread.sleep(1000);
            System.out.println("hello world");
            return "";
        });

        new Thread(hello_world, "t1").start();

        String s = hello_world.get();
        System.out.println(s);
    }

    private static void test1() throws IOException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        serialize();
        String s = "zhx";

        Field value = s.getClass().getDeclaredField("value");
        value.setAccessible(true);

        char[] o = (char[])value.get(s);
        int i = 0, j = o.length-1;
        char temp;

        while(i < j){
           temp = o[i];
           o[i] = o[j];
           o[j] = temp;
           i++;j--;
        }

        s = "abababa";
        String replace = s.replace("a", "c");
        System.out.println(replace);
    }


    public static void serialize() throws IOException, ClassNotFoundException {
        String s = "zjx";
        Integer a = 10;
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("test.obj"));
        oos.writeObject(s);
        oos.writeObject(a);

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("test.obj"));
        String o = (String)ois.readObject();
        Integer A = (Integer) ois.readObject();

        System.out.println(o);
        System.out.println(A);

    }
}
