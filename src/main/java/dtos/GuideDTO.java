package dtos;

import entities.Guide;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class GuideDTO {
    private Long id;
    private String name;
    private String gender;
    private String birthYear;
    private String profile;
    private String image;
    private Long tripId;

    // Constructors, getters, and setters

    public GuideDTO(Guide guide) {
        this.name = guide.getName();
        this.gender = guide.getGender();
        this.birthYear = guide.getBirth_year();
        this.profile = guide.getProfile();
        this.image = guide.getImage();
        this.tripId = guide.getTrips().getId();
    }
}
