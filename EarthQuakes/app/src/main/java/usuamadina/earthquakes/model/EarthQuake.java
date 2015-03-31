package usuamadina.earthquakes.model;

import java.util.Date;

import usuamadina.earthquakes.model.Coordinate;

/**
 * Created by cursomovil on 25/03/15.
 */
public class EarthQuake {

    private String _id;

    private String place;

    private Date time;

    private Coordinate coords;

    private double magnitude;

    private String url;

    private Double longitude;

    private Double latitude;

    private Double depth;

    public EarthQuake(String _id, String place, Date date, Coordinate coords, double magnitude) {
        this();

        this._id = _id;
        this.place = place;
        this.time = date;
        this.coords = coords;
        this.magnitude = magnitude;
    }

    public EarthQuake() {
        this.coords = new Coordinate(0.0, 0.0, 0.0);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return this.getPlace();

    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Date getDate() {
        return time;
    }

    public void setDate(long date) {
        this.time = new Date(date);

    }

    public void setDate(Date date) {
        this.time = date;
    }

    public Coordinate getCoords() {return coords;}

    public void setCoords(Coordinate coords) {
        this.coords = coords;
    }

  //  public getLatitude(){return this.coords.getLat();}

    public void setLatitude(double latitude) {this.coords.setLat(latitude);}

    public void setLongitude(Double longitude){this.coords.setLng(longitude);}

  //  public void getLongitude(){return coords.getLng();}

    public void setDepth(Double depth){ this.coords.setDepth(depth);}

  //  public void getDepth(){return coords.getDepth();}

    public double getMagnitude() { return magnitude;}

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

    public void insertEarthQuake(EarthQuake earthQuake) {

    }
}
