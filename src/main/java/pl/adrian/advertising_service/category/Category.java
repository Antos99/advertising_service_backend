package pl.adrian.advertising_service.category;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.adrian.advertising_service.advertisement.Advertisement;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@ToString
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name")
    private String name;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Advertisement> advertisements;

    public Category(){}
}
