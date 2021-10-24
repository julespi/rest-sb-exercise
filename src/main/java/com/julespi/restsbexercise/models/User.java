package com.julespi.restsbexercise.models;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(unique = true)
    @Getter
    @Setter
    private String id;

    @Getter
    @Setter
    private String name;

    @Column(unique = true)
    @Getter
    @Setter
    private String email;

    @Column(length = 60)
    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private Boolean isActive;

    @Getter
    @Setter
    private String token;

    @CreationTimestamp
    @Getter
    private Date created;

    @UpdateTimestamp
    @Getter
    private Date modified;

    @Getter
    @Setter
    private Date last_login;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @Getter
    private final Set<Phone> phones = new HashSet<Phone>();

    // TODO ver donde va la validacion del null de newPhones
    public void addPhones(Set<Phone> newPhones) {
        this.phones.clear();
        for (Phone phone : newPhones) {
            phone.setUser(this);
            this.phones.add(phone);
        }
    }


    // TODO volar esto
    @PrePersist
    private void onPersistCallback() {
        System.out.println("persiste");
    }

    // TODO volar esto
    @PreUpdate
    private void onUpdateCallback() {
        System.out.println("updatea");
    }


    public void updateLastLogin() {
        this.setLast_login(new Date());
    }
}
