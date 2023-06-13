package entities;

import lombok.*;

import javax.persistence.*;

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

    @Column(name = "time")
    private String time;

    @Column(name = "location")
    private String location;

    @Column(name = "dish")
    private String dish;

    @Column(name = "price_per_person")
    private double pricePerPerson;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;


}
