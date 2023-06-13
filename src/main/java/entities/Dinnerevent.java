package entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
public class Dinnerevent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "time")
    private String time;

    @Column(name = "location")
    private String location;

    @Column(name = "dish")
    private String dish;

    @Column(name = "price_per_person")
    private double pricePerPerson;

    @OneToMany(mappedBy = "dinnerevent")
    private List<Assignment> assignments = new ArrayList<>();


}
