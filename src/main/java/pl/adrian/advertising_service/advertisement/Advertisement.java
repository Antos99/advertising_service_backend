package pl.adrian.advertising_service.advertisement;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.adrian.advertising_service.address.Address;
import pl.adrian.advertising_service.category.Category;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@ToString
@Table(name="advertisements")
public class Advertisement {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    @Column(name="name")
    private String name;
    @Column(name="price")
    private Float price;
    @Column(name="description")
    private String description;
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

    public Advertisement(){}

}
