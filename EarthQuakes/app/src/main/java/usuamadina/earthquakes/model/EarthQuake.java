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

    public EarthQuake(String _id, String place, Date date, Coordinate coords, double magnitude) {
        this._id = _id;
        this.place = place;
        this.time = date;
        this.coords = coords;
        this.magnitude = magnitude;
    }

    public EarthQuake() {
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

    public Coordinate getCoords() {
        return coords;
    }

    public void setCoords(Coordinate coords) {
        this.coords = coords;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(double magnitude) {
        this.magnitude = magnitude;
    }

}
