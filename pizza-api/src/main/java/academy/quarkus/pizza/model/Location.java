package academy.quarkus.pizza.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Location {
    public Double lat;
    public Double lon;

    public Location(){}

    public Location(Double lat, Double lon){
        this.lat = lat;
        this.lon = lon;
    }

    public static Location current(){
        return new Location();
    }
}
