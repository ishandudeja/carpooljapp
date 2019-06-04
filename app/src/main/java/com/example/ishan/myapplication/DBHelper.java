package com.example.ishan.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "carpool.db";

    //We need to pass database information along to superclass
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String user = " CREATE TABLE users (" +
                "  id  INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  name TEXT," +
                "  email TEXT," +
                "  password TEXT" +
                " ) ";


        String trip = "CREATE TABLE trips ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT," +
                "  vehicleDescription TEXT," +
                "  setAvailable INTEGER," +
                "  startDate TEXT ," +
                "  startTime TEXT ," +
                "  duration TEXT," +
                "  distance TEXT," +
                "  departurePoint TEXT," +
                "  departureLatLong TEXT," +
                "  departureAddress TEXT," +
                "  departureLocality TEXT," +
                "  arrivalPoint TEXT," +
                "  arrivalLatLong TEXT," +
                "  arrivalAddress TEXT," +
                "  arrivalLocality TEXT," +
                "  instructions TEXT" +
                ") ";

        String myTrip = "CREATE TABLE my_trips ( id integer primary key AUTOINCREMENT," +
                "  user_id integer ," +
                "  trip_id integer ," +
                "  tripOwner BOOLEAN," +
                "  conformTrip BOOLEAN," +
                "  setBooked integer," +
                " foreign key (user_id) references users(id), " +
                " foreign key (trip_id) references trips(id)" +
                "  ) ";

        sqLiteDatabase.execSQL(user);
        sqLiteDatabase.execSQL(trip);
        sqLiteDatabase.execSQL(myTrip);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS users");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS trips");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS my_trips");
        onCreate(sqLiteDatabase);
    }

    public long createUser(Users user) {
        ContentValues values = new ContentValues();
        values.put("name", user.get_name());
        values.put("email", user.get_email());
        values.put("password", user.get_password());
        SQLiteDatabase db = getWritableDatabase();
        long rowid = db.insert("users", null, values);
        db.close();

        return rowid;
    }

    public long createTrips(Trips trip) {
        ContentValues values = new ContentValues();
        values.put("name", trip.get_name());
        values.put("startDate", trip.get_startDate().toString());
        values.put("startTime", trip.get_startTime().toString());
        values.put("distance", trip.get_distance());
        values.put("duration", trip.get_duration());
        values.put("arrivalLatLong", trip.get_arrivalLatLong());
        values.put("arrivalAddress", trip.get_arrivalAddress());
        values.put("arrivalLocality", trip.get_arrivalLocality());
        values.put("arrivalPoint", trip.get_arrivalPoint());
        values.put("departureLatLong", trip.get_departureLatLong());
        values.put("departureAddress", trip.get_departureAddress());
        values.put("departureLocality", trip.get_departureLocality());
        values.put("departurePoint", trip.get_departurePoint());
        values.put("setAvailable", trip.get_setAvailable());
        values.put("instructions", trip.get_instructions());
        SQLiteDatabase db = getWritableDatabase();
        long rowid = db.insert("trips", null, values);
        db.close();

        return rowid;
    }

    public long createMyTrip(MyTrip myTrip) {
        ContentValues values = new ContentValues();
        values.put("trip_id", myTrip.get_trip_id());
        values.put("user_id", myTrip.get_user_id());
        values.put("setBooked", myTrip.get_setBooked());
        values.put("conformTrip", myTrip.is_conformTrip());
        values.put("tripOwner", myTrip.is_tripOwner());

        SQLiteDatabase db = getWritableDatabase();
        long rowid = db.insert("my_trips", null, values);
        db.close();

        return rowid;

    }

    public boolean isUserExist(String UserName) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM users WHERE name ='" + UserName + "'";// why not leave out the WHERE  clause?

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results

        return recordSet.getCount() > 0;

    }

    public Users LoginUser(String userName, String password) {
        if (isUserExist(userName)) {
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT * FROM users WHERE name ='" + userName + "'";// why not leave out the WHERE  clause?

            //Cursor points to a location in your results
            Cursor recordSet = db.rawQuery(query, null);
            recordSet.moveToFirst();
            String pwd = recordSet.getString(recordSet.getColumnIndex("password"));
            Log.i("search", "password:" + pwd);
            if (pwd.equals(password)) {
                Users u = new Users();
                u.set_id(recordSet.getInt(recordSet.getColumnIndex("id")));
                u.set_name(recordSet.getString(recordSet.getColumnIndex("name")));
                u.set_email(recordSet.getString(recordSet.getColumnIndex("email")));

                return u;

            }
            return null;

        }

        return null;

    }

    public ArrayList<Trips> getTrips(SearchPlaces sp) {


        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM trips WHERE departurePoint like '"
                + sp.get_startPlace().get_placeName() + "' or arrivalPoint like '"
                + sp.get_destinationPlace().get_placeName() + "' or arrivalAddress like '"
                + sp.get_destinationPlace().get_address() + "' or departureAddress like '" + sp.get_startPlace().get_address() + "' ";// why not leave out the WHERE  clause?

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        ArrayList<Trips> trips = new ArrayList<>();
        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {

            Trips trip = new Trips(
                    recordSet.getString(recordSet.getColumnIndex("name"))
                    , recordSet.getString(recordSet.getColumnIndex("vehicleDescription"))
                    , recordSet.getInt(recordSet.getColumnIndex("setAvailable"))
                    , recordSet.getString(recordSet.getColumnIndex("startDate"))
                    , recordSet.getString(recordSet.getColumnIndex("startTime"))
                    , recordSet.getString(recordSet.getColumnIndex("duration"))
                    , recordSet.getString(recordSet.getColumnIndex("distance"))
                    , recordSet.getString(recordSet.getColumnIndex("departurePoint"))
                    , recordSet.getString(recordSet.getColumnIndex("departureLatLong"))
                    , recordSet.getString(recordSet.getColumnIndex("departureAddress"))
                    , recordSet.getString(recordSet.getColumnIndex("departureLocality"))
                    , recordSet.getString(recordSet.getColumnIndex("arrivalPoint"))
                    , recordSet.getString(recordSet.getColumnIndex("arrivalLatLong"))
                    , recordSet.getString(recordSet.getColumnIndex("arrivalAddress"))
                    , recordSet.getString(recordSet.getColumnIndex("arrivalLocality"))
                    , recordSet.getString(recordSet.getColumnIndex("instructions"))


            );
            trip.set_id(recordSet.getInt(recordSet.getColumnIndex("id")));

            trips.add(trip);
            recordSet.moveToNext();
        }
        db.close();

        return trips;
    }

    public Trips getTripById(int id) {
        Trips trip = new Trips();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM trips WHERE id=" + id + "";// why not leave out the WHERE  clause?

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        if (recordSet.getCount() > 0) {

            trip.set_id(recordSet.getInt(recordSet.getColumnIndex("id")));
            trip.set_name(recordSet.getString(recordSet.getColumnIndex("name")));
            trip.set_vehicleDescription(recordSet.getString(recordSet.getColumnIndex("vehicleDescription")));
            trip.set_setAvailable(recordSet.getInt(recordSet.getColumnIndex("setAvailable")));
            trip.set_startDate(recordSet.getString(recordSet.getColumnIndex("startDate")));
            trip.set_startTime(recordSet.getString(recordSet.getColumnIndex("startTime")));
            trip.set_duration(recordSet.getString(recordSet.getColumnIndex("duration")));
            trip.set_distance(recordSet.getString(recordSet.getColumnIndex("distance")));
            trip.set_departurePoint(recordSet.getString(recordSet.getColumnIndex("departurePoint")));
            trip.set_departureLatLong(recordSet.getString(recordSet.getColumnIndex("departureLatLong")));
            trip.set_departureAddress(recordSet.getString(recordSet.getColumnIndex("departureAddress")));
            trip.set_departureLocality(recordSet.getString(recordSet.getColumnIndex("departureLocality")));
            trip.set_arrivalPoint(recordSet.getString(recordSet.getColumnIndex("arrivalPoint")));
            trip.set_arrivalLatLong(recordSet.getString(recordSet.getColumnIndex("arrivalLatLong")));
            trip.set_arrivalAddress(recordSet.getString(recordSet.getColumnIndex("arrivalAddress")));
            trip.set_arrivalLocality(recordSet.getString(recordSet.getColumnIndex("arrivalLocality")));
            trip.set_instructions(recordSet.getString(recordSet.getColumnIndex("instructions")));

            return trip;

        }
        return null;
    }

    public ArrayList<Trips> myTripsById(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT t.id,t.name,t.vehicleDescription,t.setAvailable,t.startDate,t.startTime,t.duration,t.distance,t.departurePoint,t.departureLatLong,t.departureAddress,t.departureLocality,t.arrivalPoint,t.arrivalLatLong,t.arrivalAddress,t.arrivalLocality,t.instructions FROM trips t join my_trips mt on t.id=mt.trip_id where mt.user_id="+id+";";// why not leave out the WHERE  clause?

        //Cursor points to a location in your results
        Cursor recordSet = db.rawQuery(query, null);
        //Move to the first row in your results
        recordSet.moveToFirst();
        ArrayList<Trips> trips = new ArrayList<>();
        //Position after the last row means the end of the results
        while (!recordSet.isAfterLast()) {

            Trips trip = new Trips(
                    recordSet.getString(recordSet.getColumnIndex("name"))
                    , recordSet.getString(recordSet.getColumnIndex("vehicleDescription"))
                    , recordSet.getInt(recordSet.getColumnIndex("setAvailable"))
                    , recordSet.getString(recordSet.getColumnIndex("startDate"))
                    , recordSet.getString(recordSet.getColumnIndex("startTime"))
                    , recordSet.getString(recordSet.getColumnIndex("duration"))
                    , recordSet.getString(recordSet.getColumnIndex("distance"))
                    , recordSet.getString(recordSet.getColumnIndex("departurePoint"))
                    , recordSet.getString(recordSet.getColumnIndex("departureLatLong"))
                    , recordSet.getString(recordSet.getColumnIndex("departureAddress"))
                    , recordSet.getString(recordSet.getColumnIndex("departureLocality"))
                    , recordSet.getString(recordSet.getColumnIndex("arrivalPoint"))
                    , recordSet.getString(recordSet.getColumnIndex("arrivalLatLong"))
                    , recordSet.getString(recordSet.getColumnIndex("arrivalAddress"))
                    , recordSet.getString(recordSet.getColumnIndex("arrivalLocality"))
                    , recordSet.getString(recordSet.getColumnIndex("instructions"))


            );
            trip.set_id(recordSet.getInt(recordSet.getColumnIndex("id")));

            trips.add(trip);
            recordSet.moveToNext();
        }
        db.close();

        return trips;
    }

}
