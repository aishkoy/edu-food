package kg.attractor.edufood.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    Long id;

    @Column(nullable = false,
            name = "name")
    String name;

    @Column(nullable = false,
            name = "surname")
    String surname;

    @Column(nullable = false,
            name = "email",
            unique = true)
    String email;

    @Column(nullable = false,
            name = "password")
    String password;

    @Column(nullable = false,
            name = "address")
    String address;

    @Column(name = "avatar")
    String avatar;

    @Column(nullable = false,
            name = "enabled")
    Boolean enabled;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id",
            nullable = false)
    Role role;

    @OneToMany(mappedBy = "user")
    List<Order> orders;
}
