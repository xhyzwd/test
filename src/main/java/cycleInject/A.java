package cycleInject;

/**
 * @author xhy
 * @Classname
 * @Description
 * @date 2021/3/12 - 10:19
 */
public class A {
    private B b;// tag::tagname[]

    public A(B b) {
        this.b = b;
    }

    // end::tagname[]
}
