package io.github.zyngjaku.tmsbackend.dao.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;
import io.github.zyngjaku.tmsbackend.request.UserRequest;
import io.github.zyngjaku.tmsbackend.security.WebSecurityConfig;
import io.github.zyngjaku.tmsbackend.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
@Table(name = "Users")
public class User {
    public enum Role {
        OWNER, FORWARDER, DRIVER
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column(nullable = false, unique = true)
    private String mail;
    @JsonIgnore
    @Column(nullable = false)
    private String password;
    @Column(name="first_name", nullable = false)
    private String firstName;
    @Column(name="last_name", nullable = false)
    private String lastName;
    @JsonIgnore
    private String token;
    @Column(name="avatar_url", nullable = false)
    private String avatarUrl;
    @OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name = "localization_id")
    private Localization localization;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "company_id")
    private Company company;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }

    public User(String mail, String password, String firstName, String lastName, Company company, Role role) {
        this();
        setMail(mail);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
        setCompany(company);
        setRole(role);
        setAvatarUrl(Utils.generateAvatarUrl(firstName, lastName, Utils.generateHexColor()));
    }

    public User(String mail, String password, String firstName, String lastName, String token, Company company, Role role) {
        this(mail, password, firstName, lastName, company, role);
        this.token = token;
    }

    public User(String mail, String password, String firstName, String lastName, String token, Localization localization, Company company, Role role) {
        this(mail, password, firstName, lastName, token, company, role);
        this.localization = localization;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
         this.company = company;
    }

    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;

        if (role == Role.DRIVER) {
            setLocalization(new Localization());
        }
    }

    public Localization getLocalization() {
        return localization;
    }
    public void setLocalization(Localization localization) {
        this.localization = localization;
    }

    public void copyFromRequest(UserRequest userRequest) {
        if (userRequest.getMail() != null) {
            setMail(userRequest.getMail());
        }
        if (userRequest.getFirstName() != null) {
            setFirstName(userRequest.getFirstName());
        }
        if (userRequest.getLastName() != null) {
            setLastName(userRequest.getLastName());
        }
        if (userRequest.getRole() != null) {
            setRole(userRequest.getRole());
        }
    }
}
