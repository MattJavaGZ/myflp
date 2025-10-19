package matt.pas.myflp.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import matt.pas.myflp.domain.user.validate.UserEmailConstraint;


public class UserRegisterDto {
    @NotBlank
    @Size(min = 3, message = "Imię musi zawierać conajmniej 3 znaki")
    private String firstName;
    @NotBlank
    @Size(min = 3, message = "Nazwisko musi zawierać conajmniej 3 znaki")
    private String lastName;
    @NotBlank
    @Email
    @UserEmailConstraint
    private String email;
    @NotBlank
    @Size(min = 8, message = "Hasło musi zawierać conajmniej 8 znaków")
    private String password;
    private String workStation;


    public @NotBlank @Size(min = 3, message = "Imię musi zawierać conajmniej 3 znaki") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank @Size(min = 3, message = "Imię musi zawierać conajmniej 3 znaki") String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank @Size(min = 3, message = "Nazwisko musi zawierać conajmniej 3 znaki") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank @Size(min = 3, message = "Nazwisko musi zawierać conajmniej 3 znaki") String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank @Email String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank @Email String email) {
        this.email = email;
    }

    public @NotBlank @Size(min = 8, message = "Hasło musi zawierać conajmniej 8 znaków") String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 8, message = "Hasło musi zawierać conajmniej 8 znaków") String password) {
        this.password = password;
    }

    public String getWorkStation() {
        return workStation;
    }

    public void setWorkStation(String workStation) {
        this.workStation = workStation;
    }
}
