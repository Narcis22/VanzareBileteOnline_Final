package repo;

import event.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepository {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/vanzarebileteonline";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "+StartMyBrain02";

    private static final String RETRIEVE_EVENTS =   " SELECT x.eventName, x.eventType, x.author, x.bookTitle, x.hasBookRead, x.price, x.ticketsLeft, prize, artists, listOfPlays, groupName, y.name, y.address, y.dateTime, y.isOutside " +
                                                    " FROM vanzarebileteonline.events x JOIN vanzarebileteonline.locations y " +
                                                    " ON x.id = y.eventId ";

    private static final String ADD_EVENT =  " INSERT INTO vanzarebileteonline.events (id, eventName, eventType, author, bookTitle, hasBookRead, price, ticketsLeft, prize, artists, listOfPlays, groupName) " +
                                             " VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String DELETE_EVENT =  " DELETE FROM vanzarebileteonline.events " +
                                                " WHERE id = ?";

    private static final String RETRIEVE_EVENTS_BY_LOCATION = " SELECT x.eventName, x.eventType, x.author, x.bookTitle, x.hasBookRead, x.price, x.ticketsLeft, prize, artists, listOfPlays, groupName, y.name, y.address, y.dateTime, y.isOutside " +
                                                              " FROM vanzarebileteonline.events x JOIN vanzarebileteonline.locations y " +
                                                              " ON x.id = y.eventId " +
                                                              " WHERE y.name = ? ";

    private static final String RETRIEVE_EVENTS_BY_DATE = " SELECT x.eventName, x.eventType, x.author, x.bookTitle, x.hasBookRead, x.price, x.ticketsLeft, prize, artists, listOfPlays, groupName, y.name, y.address, y.dateTime, y.isOutside " +
                                                          " FROM vanzarebileteonline.events x JOIN vanzarebileteonline.locations y " +
                                                          " ON x.id = y.eventId " +
                                                          " WHERE y.dateTime = ? ";


    private static final String DELETE_EVENTS_SOLD_OUT = " DELETE FROM vanzarebileteonline.events " +
                                                         " WHERE ticketsLeft = 0 ";

    private static final String UPDATE_TICKETS_NUMBER = " UPDATE vanzarebileteonline.events " +
                                                        " SET ticketsLeft = ? " +
                                                        " WHERE eventName = ? ";

    private static final String ADD_TICKET = " INSERT INTO tickets (id, eventName, eventPrice, location, time) " +
                                             " VALUES (NULL, ?, ?, ?, ?) ";

    private static final String ADD_ORDER =  " INSERT INTO orders (id, noTickets, ticketId, customerName) " +
                                             " VALUES (NULL, ?, ?, ?); ";

    private static final String GET_TICKET_ID = " SELECT id " +
                                                " FROM vanzarebileteonline.tickets " +
                                                " WHERE eventName = ? ";

    private static final String GET_EVENT_ID =  " SELECT id " +
                                                " FROM vanzarebileteonline.events " +
                                                " WHERE eventName = ? ";

    private static final String ADD_LOCATION =  " INSERT INTO locations (id, name, address, dateTime, isOutside, eventId) " +
                                                " VALUES (NULL, ?, ?, ?, ?, ?)";

    public static Connection getDBConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }

    public Integer deleteSoldOutEvents() throws SQLException{
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(DELETE_EVENTS_SOLD_OUT);
        return preparedStatement.executeUpdate();
    }

    public List<Event> retrieveAllEvents() throws SQLException{
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(RETRIEVE_EVENTS);

        List<Event> events = new ArrayList<>();

        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()){
            String eventType = rs.getString(2);

            LocalDateTime newEventLocationDateTime = LocalDateTime.parse(rs.getString(14), DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"));

            Location location = new Location(rs.getString(12), rs.getString(13), newEventLocationDateTime, rs.getBoolean(15));

            if(eventType.equals("CarMeet")){
                CarMeet carMeet = new CarMeet(rs.getString(1), location, rs.getDouble(6), rs.getInt(7), rs.getString(8));
                events.add(carMeet);
            }
            else if(eventType.equals("BookSigning")){
                BookSigning bookSigning = new BookSigning(rs.getString(1), location, rs.getString(3), rs.getString(4), rs.getBoolean(5));
                events.add(bookSigning);
            }
            else if(eventType.equals("MusicConcert")){
                List<String> artisti = List.of(rs.getString(9).split(","));
                MusicConcert musicConcert = new MusicConcert(rs.getString(1), location, artisti, rs.getDouble(6), rs.getInt(7));
                events.add(musicConcert);
            }
            else if(eventType.equals("Play")){
                List<String> plays = List.of(rs.getString(10).split(","));
                Play play = new Play(rs.getString(1), location, rs.getString(11), rs.getDouble(6), rs.getInt(7), plays);
                events.add(play);
            }

        }
        return events;
    }

    public boolean updateTicketsNumber(Integer tickets, String name) throws SQLException{
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(UPDATE_TICKETS_NUMBER);

        preparedStatement.setInt(1, tickets);
        preparedStatement.setString(2, name);
        return preparedStatement.executeUpdate() > 0;
    }

    public boolean addTicket(String eventName, Double eventPice, String location, String time) throws SQLException{
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(ADD_TICKET);

        preparedStatement.setString(1, eventName);
        preparedStatement.setDouble(2, eventPice);
        preparedStatement.setString(3, location);
        preparedStatement.setString(4, time);

        return preparedStatement.executeUpdate() > 0;
    }

    public boolean addOrder(Integer noTickets, Integer ticketId, String customerName) throws SQLException{
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(ADD_ORDER);

        preparedStatement.setInt(1, noTickets);
        preparedStatement.setInt(2, ticketId);
        preparedStatement.setString(3, customerName);

        return preparedStatement.executeUpdate() > 0;
    }

    public Integer getTicketId(String name) throws SQLException{
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(GET_TICKET_ID);
        preparedStatement.setString(1, name);

        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()) {
            return rs.getInt(1);
        }
        return null;
    }

    public List<Event> getEventByLocation(String locationx) throws SQLException{
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(RETRIEVE_EVENTS_BY_LOCATION);
        preparedStatement.setString(1, locationx);

        List<Event> events = new ArrayList<>();

        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()){
            String eventType = rs.getString(2);

            LocalDateTime newEventLocationDateTime = LocalDateTime.parse(rs.getString(14), DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"));

            Location location = new Location(rs.getString(12), rs.getString(13), newEventLocationDateTime, rs.getBoolean(15));

            if(eventType.equals("CarMeet")){
                CarMeet carMeet = new CarMeet(rs.getString(1), location, rs.getDouble(6), rs.getInt(7), rs.getString(8));
                events.add(carMeet);
            }
            else if(eventType.equals("BookSigning")){
                BookSigning bookSigning = new BookSigning(rs.getString(1), location, rs.getString(3), rs.getString(4), rs.getBoolean(5));
                events.add(bookSigning);
            }
            else if(eventType.equals("MusicConcert")){
                List<String> artisti = List.of(rs.getString(9).split(","));
                MusicConcert musicConcert = new MusicConcert(rs.getString(1), location, artisti, rs.getDouble(6), rs.getInt(7));
                events.add(musicConcert);
            }
            else if(eventType.equals("Play")){
                List<String> plays = List.of(rs.getString(10).split(","));
                Play play = new Play(rs.getString(1), location, rs.getString(11), rs.getDouble(6), rs.getInt(7), plays);
                events.add(play);
            }

        }
        return events;
    }

    public List<Event> getEventByDate(String date) throws SQLException{
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(RETRIEVE_EVENTS_BY_DATE);
        preparedStatement.setString(1, date);

        List<Event> events = new ArrayList<>();

        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()){
            String eventType = rs.getString(2);

            LocalDateTime newEventLocationDateTime = LocalDateTime.parse(rs.getString(14), DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm"));

            Location location = new Location(rs.getString(12), rs.getString(13), newEventLocationDateTime, rs.getBoolean(15));

            if(eventType.equals("CarMeet")){
                CarMeet carMeet = new CarMeet(rs.getString(1), location, rs.getDouble(6), rs.getInt(7), rs.getString(8));
                events.add(carMeet);
            }
            else if(eventType.equals("BookSigning")){
                BookSigning bookSigning = new BookSigning(rs.getString(1), location, rs.getString(3), rs.getString(4), rs.getBoolean(5));
                events.add(bookSigning);
            }
            else if(eventType.equals("MusicConcert")){
                List<String> artisti = List.of(rs.getString(9).split(","));
                MusicConcert musicConcert = new MusicConcert(rs.getString(1), location, artisti, rs.getDouble(6), rs.getInt(7));
                events.add(musicConcert);
            }
            else if(eventType.equals("Play")){
                List<String> plays = List.of(rs.getString(10).split(","));
                Play play = new Play(rs.getString(1), location, rs.getString(11), rs.getDouble(6), rs.getInt(7), plays);
                events.add(play);
            }

        }
        return events;
    }

    public boolean addEvent(String eventName, String eventType, String author, String bookTitle, Boolean hasBookRead, Double price,
                            Integer ticketsLeft, String prize, String artists, String listOfPlays, String groupName) throws SQLException{
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(ADD_EVENT);
        preparedStatement.setString(1, eventName);
        preparedStatement.setString(2, eventType);
        preparedStatement.setString(3, author);
        preparedStatement.setString(4, bookTitle);
        if(hasBookRead == null)
            preparedStatement.setString(5, null);
        else
            preparedStatement.setBoolean(5, hasBookRead);
        if(price == null)
            preparedStatement.setString(6, null);
        else
            preparedStatement.setDouble(6, price);
        if(ticketsLeft == null)
            preparedStatement.setString(7, null);
        else
            preparedStatement.setInt(7, ticketsLeft);
        preparedStatement.setString(8, prize);
        preparedStatement.setString(9, artists);
        preparedStatement.setString(10, listOfPlays);
        preparedStatement.setString(11, groupName);
        return preparedStatement.executeUpdate() > 0;
    }

    public Integer getEventId(String name) throws SQLException{
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(GET_EVENT_ID);
        preparedStatement.setString(1, name);

        ResultSet rs = preparedStatement.executeQuery();

        while(rs.next()) {
            return rs.getInt(1);
        }

        return 0;
    }

    public boolean addLocation(String name, String address, String date, Boolean outside, Integer idEvent) throws SQLException {
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(ADD_LOCATION);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, address);
        preparedStatement.setString(3, date);
        preparedStatement.setBoolean(4, outside);
        preparedStatement.setInt(5, idEvent);

        return preparedStatement.executeUpdate() > 0;
    }

    public boolean deleteEventById(Integer id) throws SQLException {
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(DELETE_EVENT);
        preparedStatement.setInt(1, id);

        return preparedStatement.executeUpdate() > 0;
    }
}
