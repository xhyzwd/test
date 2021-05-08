package entity;

/**
 * @author xhy
 * @Classname
 * @Description
 * @date 2021/1/14 - 9:34
 */
public class Address {
    private String addressName;
    private City city;

    public Address() {
    }

    public Address(String addressName, City city) {
        this.addressName = addressName;
        this.city = city;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public City getCity() {
        System.out.println("city");
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
