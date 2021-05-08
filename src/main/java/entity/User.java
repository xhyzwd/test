package entity;

/**
 * @author xhy
 * @Classname
 * @Description
 * @date 2021/1/14 - 9:32
 */
public class User {
    private String userName;
    private String phone;
    private Address address;

    public User(String userName, String phone, Address address) {
        this.userName = userName;
        this.phone = phone;
        this.address = address;
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        System.out.println("address");
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
