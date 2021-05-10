package juc;

/**
 * @author xhy
 * @Classname
 * @Description
 * @date 2021/5/10 - 11:05
 *
 * 题目 两个线程 一个线程加一 一个线程减一 交替10轮 结果为0
 * 1.高聚低合的前提下，线程操作资源类
 * 2.判断/干活/通知其他线程
 * 3.防止虚假唤醒
 */
class NumberFix {
    private int num = 0;

    public synchronized void addNumber() throws InterruptedException {
        //while防止虚假唤醒
        while (num != 0) {
            this.wait();
        }
        num++;
        System.out.println(Thread.currentThread().getName() + ":" + num);
        this.notifyAll();
    }

    public synchronized void delNumber() throws InterruptedException {
        while (num == 0) {
            this.wait();
        }
        num--;
        System.out.println(Thread.currentThread().getName() + ":" + num);
        this.notifyAll();
    }
}
public class ProdConsumer {
    public static void main(String[] args) {
        NumberFix numberFix = new NumberFix();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    numberFix.addNumber();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    numberFix.delNumber();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    numberFix.addNumber();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    numberFix.delNumber();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();
    }
}
