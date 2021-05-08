package entity;

/**
 * @author xhy
 * @Classname
 * @Description
 * @date 2021/1/14 - 9:33
 */
public class City {
    private String name;
    private int code;

    public City() {
    }

    public City(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
