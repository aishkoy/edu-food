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
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    Long id;

    @Column(name = "name",
            nullable = false)
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "image")
    String image;

    @Column(name = "address",
            nullable = false)
    String address;

    @OneToMany(mappedBy = "restaurant")
    List<Product> products;
}
