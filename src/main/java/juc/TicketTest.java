package juc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xhy
 * @Classname
 * @Description
 * @date 2021/4/12 - 13:54
 * 1.故障现象
 * arraylist 线程不安全 java.util.ConcurrentModificationException
 * 2.故障原因
 *  在一个线程修改集合的时候有其他线程操作集合
 * 3.解决方法
 *    new Vector()
 *    Collections.synchronizedList(new ArrayList())
 *     new CopyOnWriteArrayList();
 */
public class TicketTest {

    public static void main(String[] args) {
       // List list = new ArrayList();
       // List list = new Vector();
       // List list = Collections.synchronizedList(new ArrayList());
        List list = new CopyOnWriteArrayList();
        for (int i = 0; i < 40; i++) {
            new Thread(() -> {
                list.add(Thread.currentThread().getName());
                System.out.println(list);
            },  String.valueOf(i)).start();

        }
        list.remove(12);

    }

    private static void test(){
        Ticket ticket = new Ticket();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        }, "C").start();
    }
}

class Ticket{
    private int num = 30;

    Lock lock = new ReentrantLock();

    public void sale(){
        lock.lock();
        try {
            if(num > 0){
                System.out.println(Thread.currentThread().getName() + "卖出了第" + (num--) + "票，还剩" + num);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}