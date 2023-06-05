import java.util.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Test04 {
    public static void main(String[] args) {

        Map<String, String> map = new HashMap<>(3);
        map.put("name", "Jack");
        map.put("age", "21");
        map.put("sex", "Male");
        //对Map数据进行分组。根据相同的slot放在一个分组
        //key就是slot，value就是一个组
        Map<Integer, List<Map.Entry<String, String>>> result = map.entrySet()
                .stream()
                .collect(Collectors.groupingBy(entry -> entry.hashCode() % 5)
                );

        for (List<Map.Entry<String, String>> list : result.values()) {
            String[] arr = new String[list.size() * 2];
            int j = 0;
            for (int i = 0; i < list.size(); i++) {
                j = i<<1;
                Map.Entry<String, String> e = list.get(i);
                arr[j] = e.getKey();
                arr[j + 1] = e.getValue();
            }

            System.out.println(Arrays.toString(arr));
        }
    }

    static final class Node{
        // 共享
        static final Node SHARED = new Node();

        // 独占
        static final Node EXCLUSIVE = null;

        // 线程被取消
        static final int CANCELLED =  1;

        // 后继线程需要唤醒
        static final int SIGNAL    = -1;

        // 等待condition唤醒
        static final int CONDITION = -2;

        // 共享式同步状态获取将无条件传播下去
        static final int PROPAGATE = -3;

        // 初始0，值为以上的几种
        volatile int waitStatus;

        // 线程
        volatile Thread thread;

        volatile Node prev;

        volatile Node next;


    }

    private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) {
        // 获取前驱节点的状态
        int ws = pred.waitStatus;
        if (ws == Node.SIGNAL)
            // 等待CPU 可以阻塞
            return true;
        if (ws > 0) {
            // 意味着线程取消了，需要取点所有的线程取消的节点，并保持双向队列的结构
            do {
                node.prev = pred = pred.prev;
            } while (pred.waitStatus > 0);
            pred.next = node;
        } else {
            // 置位，用于后序唤醒操作
            compareAndSetWaitStatus(pred, ws, Node.SIGNAL);
        }
        return false;
    }

    private static void compareAndSetWaitStatus(Node pred, int ws, int signal) {
    }

    private final boolean parkAndCheckInterrupt() {
        LockSupport.park(this);
        return Thread.interrupted();
    }




}

