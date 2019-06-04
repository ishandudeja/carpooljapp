package com.example.ishan.myapplication;




import java.sql.Date;
import java.sql.Time;

public class Trips {
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_vehicleDescription() {
        return _vehicleDescription;
    }

    public void set_vehicleDescription(String _vehicleDescription) {
        this._vehicleDescription = _vehicleDescription;
    }

    public int get_setAvailable() {
        return _setAvailable;
    }

    public void set_setAvailable(int _setAvailable) {
        this._setAvailable = _setAvailable;
    }

    public String get_startDate() {
        return _startDate;
    }

    public void set_startDate(String _startDate) {
        this._startDate = _startDate;
    }

    public String get_startTime() {
        return _startTime;
    }

    public void set_startTime(String _startTime) {
        this._startTime = _startTime;
    }

    public String get_duration() {
        return _duration;
    }

    public void set_duration(String _duration) {
        this._duration = _duration;
    }

    public String get_distance() {
        return _distance;
    }

    public void set_distance(String _distance) {
        this._distance = _distance;
    }

    public String get_departurePoint() {
        return _departurePoint;
    }

    public void set_departurePoint(String _departurePoint) {
        this._departurePoint = _departurePoint;
    }

    public String get_departureLatLong() {
        return _departureLatLong;
    }

    public void set_departureLatLong(String _departureLatLong) {
        this._departureLatLong = _departureLatLong;
    }

    public String get_departureAddress() {
        return _departureAddress;
    }

    public void set_departureAddress(String _departureAddress) {
        this._departureAddress = _departureAddress;
    }

    public String get_departureLocality() {
        return _departureLocality;
    }

    public void set_departureLocality(String _departureLocality) {
        this._departureLocality = _departureLocality;
    }
    public String get_arrivalPoint() {
        return _arrivalPoint;
    }

    public void set_arrivalPoint(String _arrivalPoint) {
        this._arrivalPoint = _arrivalPoint;
    }

    public String get_arrivalLatLong() {
        return _arrivalLatLong;
    }

    public void set_arrivalLatLong(String _arrivalLatLong) {
        this._arrivalLatLong = _arrivalLatLong;
    }

    public String get_arrivalAddress() {
        return _arrivalAddress;
    }

    public void set_arrivalAddress(String _arrivalAddress) {
        this._arrivalAddress = _arrivalAddress;
    }

    public String get_arrivalLocality() {
        return _arrivalLocality;
    }

    public void set_arrivalLocality(String _arrivalLocality) {
        this._arrivalLocality = _arrivalLocality;
    }

    public String get_instructions() {
        return _instructions;
    }

    public void set_instructions(String _instructions) {
        this._instructions = _instructions;
    }
    public Trips(){}
    public Trips(String name,
                 String vehicleDescription,
                 int _setAvailable,
                 String _startDate,
                 String _startTime,
                 String _duration,
                 String _distance,
                 String _departurePoint,
                 String _departureLatLong,
                 String _departureAddress,
                 String _departureLocality ,
                 String _arrivalPoint,
                 String  _arrivalLatLong ,
                 String _arrivalAddress,
                 String _arrivalLocality,
                 String _instructions
    ){
        this.  _name=name;
        this. _vehicleDescription=vehicleDescription;
        this. _setAvailable=_setAvailable;
        this. _startDate= _startDate;
        this. _startTime=_startTime;
        this. _duration=_duration;
        this.  _distance=_distance;
        this. _departurePoint=_departurePoint;
        this. _departureLatLong=_departureLatLong;
        this. _departureAddress=_departureAddress;
        this. _departureLocality=_departureLocality;
        this. _arrivalPoint=_arrivalPoint;
        this. _arrivalLatLong=_arrivalLatLong;
        this. _arrivalAddress=_arrivalAddress;
        this. _arrivalLocality=_arrivalLocality;
        this. _instructions=_instructions;

    }
    private int _id;
    private String _name;
    private String _vehicleDescription;
    private int _setAvailable;
    private String _startDate;
    private String _startTime;
    private String _duration;
    private String  _distance;
    private String _departurePoint;
    private String _departureLatLong;
    private String _departureAddress;
    private String _departureLocality;
    private String _arrivalPoint;
    private String _arrivalLatLong;
    private String _arrivalAddress;
    private String _arrivalLocality;
    private String _instructions;


}

