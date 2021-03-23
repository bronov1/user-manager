package com.wamisoftware.usermanager.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User extends AbstractPerson {

    private static final int MIN_LENGTH = 5;
    private static final int MAX_LENGTH = 25000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    @Email
    @Size(min = MIN_LENGTH)
    private String login;
    @Size(min = MIN_LENGTH)
    private String password;
    @Column(length = MAX_LENGTH)
    @Size(max = MAX_LENGTH)
    private String aboutMe;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_address",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))
    private List<Address> addresses;

    public User(String name, String lastName, LocalDate birthDate, List<Address> addresses,
                @Size(min = MIN_LENGTH) String login,
                @Size(min = MIN_LENGTH) String password,
                @Size(max = MAX_LENGTH) String aboutMe) {
        super(name, lastName, birthDate);
        this.login = login;
        this.password = password;
        this.aboutMe = aboutMe;
        this.addresses = addresses;
    }

    public User() {
        super();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return id == user.id &&
                login.equals(user.login) &&
                password.equals(user.password) &&
                aboutMe.equals(user.aboutMe) &&
                Objects.equals(addresses, user.addresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, login, password, aboutMe, addresses);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", aboutMe='" + aboutMe + '\'' +
                ", addresses=" + addresses +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
