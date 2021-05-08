package see;

/**
 * @author xhy
 * @Classname
 * @Description
 * @date 2021/4/7 - 9:12
 */
public class MyData2 {
    volatile int num = 0;
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
