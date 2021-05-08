package juc;

import java.util.concurrent.TimeUnit;

/**
 * @author xhy
 * @Classname
 * @Description
 * @date 2021/4/25 - 15:29
 */

class Phone {
    public synchronized void sendEmail() throws Exception {
      //  TimeUnit.SECONDS.sleep(4L);
        for (int i = 0; i < 100; i++) {
            System.out.println(1);
        }
        System.out.println("SendEmail");
    }

    public synchronized void sendSMS() throws Exception {
        for (int i = 0; i < 100; i++) {
            System.out.println(2);
        }
        System.out.println("sendSMS");
    }

    public synchronized void sendEmail2() throws Exception {
        TimeUnit.SECONDS.sleep(4L);
        for (int i = 0; i < 100; i++) {
            System.out.println(1);
        }
        System.out.println("SendEmail");
    }



    public void sayHello() {
        System.out.println("sayHello");
    }

    public static synchronized void sendEmail3() throws Exception {
        //  TimeUnit.SECONDS.sleep(4L);
        for (int i = 0; i < 100; i++) {
            System.out.println(1);
        }
        System.out.println("SendEmail");
    }

    public static synchronized void sendSMS3() throws Exception {
        for (int i = 0; i < 100; i++) {
            System.out.println(2);
        }
        System.out.println("sendSMS");
    }
}

/**
 * synchronized的8锁现象
 * 1.标准访问 先打印邮件还是短信    先有邮件后有短信
 * 2.邮件中sleep 4秒 先打印邮件还是短信    先打印邮件  因为sleep不会释放对象锁
 * 3.新增普通方法sayhello 先打印hello还是邮件  先打印hello
 * 4.两部手机 先打印邮件还是短信  随机
 * 5.两个静态方法 同一部手机 先打印邮件还是短信   先打印邮件
 * 6.两个静态方法 同两部手机 先打印邮件还是短信   先打印邮件
 * 7.一个静态方法 一个普通同步方法 一部手机   随机
 * 8.一个静态方法 一个普通同步方法 2部手机   随机
 *
 *
 *     一个对象内 如果有多个synchronized方法，在同一时刻，只要一个线程去调用了其中一个
 * synchronized方法，其他线程只能等待。 在同一时间内只能一个线程去调用一个synchronized方法
 * synchronized加在方法上就是对象锁，锁的是this，被锁定后，其他线程都不能进入当前对象的synchronized方法
 *
 * 同步方法不会对普通方法产生影响
 *
 * 两部手机 对应两个对象 分别对两个对象加上synchronized 互不影响
 *
 * 对static方法加锁 其实是对类加锁  会影响所有实例对象
 *
 * 静态方法是类锁  普通同步方法是对象锁  对象锁和类锁 锁的不是同一对象 所以两个锁之间没有竞争
 */
public class PhoneTest {

    public static void main(String[] args) throws InterruptedException {
        test1();
    }


    private static void test1() {
        Phone phone = new Phone();
        new Thread(() -> {
            try {
                phone.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A").start();

        //   Thread.sleep(100);
        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "B").start();
    }

    private static void test2() {
        Phone phone = new Phone();
        new Thread(() -> {
            try {
                phone.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A").start();

        //   Thread.sleep(100);
        new Thread(() -> {
            try {
                phone.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "B").start();
    }

    private static void test3() {
        Phone phone = new Phone();
        new Thread(() -> {
            try {
                phone.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A").start();

        //   Thread.sleep(100);
        new Thread(() -> {
            phone.sayHello();
        }, "B").start();
    }

    private static void test4() {
        Phone phone = new Phone();
        Phone phone2 = new Phone();

        new Thread(() -> {
            try {
                phone.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A").start();

        //   Thread.sleep(100);
        new Thread(() -> {
            try {
                phone2.sendSMS();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "B").start();
    }

    private static void test5() {
        Phone phone = new Phone();
        new Thread(() -> {
            try {
                phone.sendEmail3();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A").start();

        //   Thread.sleep(100);
        new Thread(() -> {
            try {
                phone.sendSMS3();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "B").start();

    }

    private static void test6() {
        Phone phone = new Phone();
        Phone phone2 = new Phone();
        new Thread(() -> {
            try {
                phone.sendEmail3();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A").start();

        //   Thread.sleep(100);
        new Thread(() -> {
            try {
                phone2.sendSMS3();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "B").start();

    }

    private static void test7() {
        Phone phone = new Phone();
        new Thread(() -> {
            try {
                phone.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A").start();

        //   Thread.sleep(100);
        new Thread(() -> {
            try {
                phone.sendSMS3();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "B").start();
    }

    private static void test8() {
        Phone phone = new Phone();
        Phone phone2 = new Phone();
        new Thread(() -> {
            try {
                phone.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A").start();

        //   Thread.sleep(100);
        new Thread(() -> {
            try {
                phone2.sendSMS3();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "B").start();
    }
}
