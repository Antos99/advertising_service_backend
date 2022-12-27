package pl.adrian.advertising_service.role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="roles")
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name="name")
    private EnumRole name;

    public Role(EnumRole enumRole){
        name = enumRole;
    }
}
