package entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "create_date")
    private String createDate;

    @Column(name = "contact_info")
    private String contactInfo;

    @OneToMany(mappedBy = "assignment")
    private List<Dinnerevent> dinnerEvents = new ArrayList<>();

    @ManyToMany(mappedBy = "assignment")
    private Set<User> users = new HashSet<>();


}
