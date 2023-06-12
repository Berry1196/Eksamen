package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "trips")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date")
    private String date;
    @Column(name = "time")
    private String time;
    @Column(name = "location")
    private String location;
    @Column(name = "duration")
    private String duration;
    @Column(name = "packing_list")
    private String packingList;

    @ManyToMany(mappedBy = "tripList")
    private List<User> user;

    @OneToMany
    private List<Guide> guide;


    public Trip(String name, String date, String time, String location, String duration, String packingList) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.duration = duration;
        this.packingList = packingList;
    }
}
