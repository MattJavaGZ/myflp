package matt.pas.myflp.domain.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

public class CilentToSaveDto {
    @NotEmpty(message = "Imię nie może być puste")
    private String firstName;
    @NotEmpty(message = "Nazwisko nie może być puste")
    private String lastName;
    private String address;
    @Email(message = "Podaj poprawny adres email")
    private String email;
    private String phone;
    private String fbLink;
    private List<Long> groupIds = new ArrayList<>();

    public CilentToSaveDto() {
    }

    public CilentToSaveDto(String firstName, String lastName, String address, String email, String phone, String fbLink,
                           List<Long> groupIds) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.fbLink = fbLink;
        this.groupIds = groupIds;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFbLink() {
        return fbLink;
    }

    public void setFbLink(String fbLink) {
        this.fbLink = fbLink;
    }

    public List<Long> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Long> groupIds) {
        this.groupIds = groupIds;
    }
}
