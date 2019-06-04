package com.example.ishan.myapplication;


public class MyTrip {
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_user_id() {
        return _user_id;
    }

    public void set_user_id(int _user_id) {
        this._user_id = _user_id;
    }

    public int get_trip_id() {
        return _trip_id;
    }

    public void set_trip_id(int _trip_id) {
        this._trip_id = _trip_id;
    }

    public boolean is_tripOwner() {
        return _tripOwner;
    }

    public void set_tripOwner(boolean _tripOwner) {
        this._tripOwner = _tripOwner;
    }

    public boolean is_conformTrip() {
        return _conformTrip;
    }

    public void set_conformTrip(boolean _conformTrip) {
        this._conformTrip = _conformTrip;
    }

    public int get_setBooked() {
        return _setBooked;
    }

    public void set_setBooked(int _setBooked) {
        this._setBooked = _setBooked;
    }

    private int _id;
    private int _user_id;
    private int _trip_id;
    private boolean _tripOwner;
    private boolean _conformTrip;
    private int _setBooked;

    public MyTrip(){}
    public  MyTrip(int _user_id,int _trip_id,boolean _tripOwner, boolean _conformTrip, int _setBooked){
        this._user_id=_user_id;
        this._trip_id=_trip_id;
        this._tripOwner=_tripOwner;
        this._conformTrip=_conformTrip;
        this._setBooked=_setBooked;
    }



}
