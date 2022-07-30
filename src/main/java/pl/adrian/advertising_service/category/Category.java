package pl.adrian.advertising_service.category;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
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

    public Category(){}

    public Category(String name) {
        this.name = name;
    }
}
