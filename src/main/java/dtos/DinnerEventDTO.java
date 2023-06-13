package dtos;

import entities.Assignment;
import entities.Dinnerevent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class DinnerEventDTO {
    private Long id;
    private String time;
    private String location;
    private String dish;
    private double pricePerPerson;
    private List<String> assignments;

    public DinnerEventDTO(Dinnerevent dinnerevent) {
        this.id = dinnerevent.getId();
        this.time = dinnerevent.getTime();
        this.location = dinnerevent.getLocation();
        this.dish = dinnerevent.getDish();
        this.pricePerPerson = dinnerevent.getPricePerPerson();
        if (dinnerevent.getAssignments() != null) {
            this.assignments = dinnerevent.getAssignments().stream()
                    .map(Assignment::getFamilyName)
                    .collect(Collectors.toList());
        }
    }

    public DinnerEventDTO(String time, String location, String dish, double pricePerPerson) {
        this.time = time;
        this.location = location;
        this.dish = dish;
        this.pricePerPerson = pricePerPerson;
    }

    public static List<DinnerEventDTO> getDtos(List<Dinnerevent> dinnerevents) {
        List<DinnerEventDTO> dinnerEventDTOS = new ArrayList<>();
        dinnerevents.forEach(dinnerevent -> dinnerEventDTOS.add(new DinnerEventDTO(dinnerevent)));
        return dinnerEventDTOS;
    }
}

