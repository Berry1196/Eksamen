package dtos;

import entities.Guide;
import entities.Trip;
import entities.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class TripDTO {
    private Long id;
    private String name;
    private String date;
    private String time;
    private String location;
    private String duration;
    private String packingList;
    private List<String> user_name;
    private List<String> guideNames;

    // Constructors, getters, and setters

    public TripDTO(Trip trip) {
        this.id = trip.getId();
        this.name = trip.getName();
        this.date = trip.getDate();
        this.time = trip.getTime();
        this.location = trip.getLocation();
        this.duration = trip.getDuration();
        this.packingList = trip.getPackingList();
        this.user_name = trip.getUser().stream().map(User::getUser_name).collect(Collectors.toList());
        this.guideNames = trip.getGuide().stream().map(Guide::getName).collect(Collectors.toList());
    }

    public TripDTO(String name, String date, String time, String location, String duration, String packingList) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.location = location;
        this.duration = duration;
        this.packingList = packingList;
    }

    public static List<TripDTO> getDTOs(List<Trip> trips) {
        List<TripDTO> tripDTOS = new ArrayList<>();
        trips.forEach(trip -> tripDTOS.add(new TripDTO(trip)));
        return tripDTOS;
    }



    // Getters and Setters

    // Other methods (if any)
}
