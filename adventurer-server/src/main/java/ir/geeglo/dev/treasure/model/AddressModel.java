package ir.geeglo.dev.treasure.model;

import ir.geeglo.dev.treasure.GeegloSpringServiceProvider;
import ir.geeglo.dev.treasure.data.entity.AddressEntity;
import ir.geeglo.dev.treasure.data.entity.UserEntity;

/**
 * @author Mohammad Rahmati, 10/14/2018
 */
public class AddressModel {
    private int id;
    private String title;
    private Double latitude;
    private Double longitude;
    private String detail;
    private String phoneNumber;
    private String postCode;

    public AddressModel() {

    }

    public AddressModel(
            int id, String title,
            Double latitude, Double longitude,
            String detail, String phoneNumber, String postCode) {
        this.id = id;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.detail = detail;
        this.phoneNumber = phoneNumber;
        this.postCode = postCode;
    }

    public AddressModel(
            AddressEntity addressEntity) {
        this.id = addressEntity.getId();
        this.title = addressEntity.getTitle();
        this.latitude = addressEntity.getLatitude();
        this.longitude = addressEntity.getLongitude();
        this.detail = addressEntity.getDetail();
        this.phoneNumber = addressEntity.getPhoneNumber();
        this.postCode = addressEntity.getPostCode();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }


    public static void main(String[] args) {
        UserEntity userEntity = GeegloSpringServiceProvider.getUserService().selectByMobile("09391366128");
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setTitle("title");
        addressEntity.setDetail("detail");
        addressEntity.setLongitude(52d);
        addressEntity.setLatitude(35d);
        addressEntity.setUserEntity(userEntity);
        GeegloSpringServiceProvider.getAddressService().save(addressEntity);
        System.out.println(addressEntity.getId());
    }
}
