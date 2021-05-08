package see;

/**
 * @author xhy
 * @Classname
 * @Description
 * @date 2021/3/31 - 8:51
 */
class MyData {
    int num = 0;
    public void add(){
        num = 60;
    }
    //synchronized保证操作原子性
    //public synchronized void add2(){
    //    num++;
    //}
    public void add2(){
        num++;
    }
}
public class Test {
    public static void main(String[] args) {
        test2();
    }

    //不保证原子性 volatile
    private static void test2(){
        MyData myData = new MyData();
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myData.add2();
                }
            }, String.valueOf(i)).start();
        }
        while (Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println("main count：" + myData.num);
    }

    //可见性 volatile
    private void test(){
        MyData myData = new MyData();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + myData.num);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //println方法会刷新工作副本
            System.out.println(Thread.currentThread().getName() + myData.num);
        }, "AAA").start();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("before" + myData.num);
            myData.add();
            System.out.println("after" + myData.num);
        }, "BBB").start();
        while(myData.num==0){
        }

        System.out.println("结束");
    }
}
