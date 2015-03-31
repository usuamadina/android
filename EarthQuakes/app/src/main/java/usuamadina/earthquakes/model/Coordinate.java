package usuamadina.earthquakes.model;

/**
 * Created by cursomovil on 25/03/15.
 */
public class Coordinate {
    private double lat;
    private double lng;
    private double depth;

    public Coordinate(double lat, double lng, double depth) {
        this.lat = lat;
        this.lng = lng;
        this.depth = depth;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) { this.lat = lat;}

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }
}
