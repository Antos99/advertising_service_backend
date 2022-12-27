package pl.adrian.advertising_service.advertisement;

import lombok.*;
import pl.adrian.advertising_service.address.Address;
import pl.adrian.advertising_service.category.Category;
import pl.adrian.advertising_service.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="advertisements")
@ToString
public class Advertisement {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="category_name", referencedColumnName="name")
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="username", referencedColumnName="username")
    private User user;
    @Column(name="name")
    private String name;
    @Column(name="price")
    private Float price;
    @Column(name="description")
    private String description;
    @OneToOne(mappedBy="advertisement", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private Address address;
    @Column(name="created")
    private LocalDateTime created;
    @Column(name="expired")
    private LocalDateTime expired;
    @Column(name="modified")
    private LocalDateTime modified;
    @Column(name="active")
    private Boolean active;
    @Transient
    private Integer duration;

    public Advertisement(Category category, User user, String name, Float price,
                         String description, Integer duration) {
        this.category = category;
        this.user = user;
        this.name = name;
        this.price = price;
        this.description = description;
        this.created = LocalDateTime.now();
        this.active = true;
        this.duration = duration;
        this.expired = this.created.plusDays(this.duration);
    }
}
