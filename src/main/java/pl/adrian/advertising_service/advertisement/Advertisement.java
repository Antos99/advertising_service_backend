package pl.adrian.advertising_service.advertisement;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.adrian.advertising_service.address.Address;
import pl.adrian.advertising_service.category.Category;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="advertisements")
public class Advertisement {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;
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

    public Advertisement(Category category, String name, Float price,
                         String description, Integer duration) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.description = description;
        this.created = LocalDateTime.now();
        this.active = true;
        this.duration = duration;
        this.expired = this.created.plusDays(this.duration);
    }
}
