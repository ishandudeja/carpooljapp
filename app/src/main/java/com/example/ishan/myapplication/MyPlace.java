package com.example.ishan.myapplication;



import java.io.Serializable;

public class MyPlace implements Serializable {

    private String _laglong;
    private String _placeName;
    private String _address;
    private String _locality;

    public MyPlace() {
    }

    public MyPlace(
            String _laglong,
            String _placeName,
            String _address,
            String _locality
    ) {
        this._laglong = _laglong;
        this._placeName = _placeName;
        this._address = _address;
        this._locality = _locality;

    }

    public String get_laglong() {
        return _laglong;
    }

    public void set_laglong(String _laglong) {
        this._laglong = _laglong;
    }

    public String get_placeName() {
        return _placeName;
    }

    public void set_placeName(String _placeName) {
        this._placeName = _placeName;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public String get_locality() {
        return _locality;
    }

    public void set_locality(String _locality) {
        this._locality = _locality;
    }


}


