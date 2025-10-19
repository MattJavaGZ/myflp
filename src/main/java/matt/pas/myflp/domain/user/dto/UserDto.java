package matt.pas.myflp.domain.user.dto;

import java.time.LocalDateTime;

public class UserDto implements Comparable<UserDto> {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean activ;
    private LocalDateTime dateAdded;
    private String workStation;

    public UserDto(Long id, String firstName, String lastName, String email, boolean activ, LocalDateTime dateAdded, String workStation) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.activ = activ;
        this.dateAdded = dateAdded;
        this.workStation = workStation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActiv() {
        return activ;
    }

    public void setActiv(boolean activ) {
        this.activ = activ;
    }

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDateTime dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getWorkStation() {
        return workStation;
    }

    public void setWorkStation(String workStation) {
        this.workStation = workStation;
    }

    @Override
    public int compareTo(UserDto o) {
        return o.getDateAdded().compareTo(this.getDateAdded());
    }

}
