package com.example.ishan.myapplication;


import java.io.Serializable;

public class SearchPlaces implements Serializable {

    private MyPlace _startPlace;
    private MyPlace _destinationPlace;

    public SearchPlaces() {
    }

    public SearchPlaces(
            MyPlace _startPlace,
            MyPlace _destinationPlace
    ) {
        this._startPlace = _startPlace;
        this._destinationPlace = _destinationPlace;
    }

    public MyPlace get_startPlace() {
        return _startPlace;
    }

    public void set_startPlace(MyPlace _startPlace) {
        this._startPlace = _startPlace;
    }

    public MyPlace get_destinationPlace() {
        return _destinationPlace;
    }

    public void set_destinationPlace(MyPlace _destinationPlace) {
        this._destinationPlace = _destinationPlace;
    }



}
