package service;

import event.*;
import interfaces.MissInput;
import repo.DatabaseRepository;
import user.Ticket;
import user.User;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventService implements MissInput {
    private static class SINGLETON_HOLDER{
        private static final EventService INSTANCE = new EventService();
    }

    public static EventService getInstance(){
        return SINGLETON_HOLDER.INSTANCE;
    }

    DatabaseRepository databaseRepository = new DatabaseRepository();

    @Override
    public void missInputMessage(String insertedText) {
        System.out.println("This text: " + insertedText + " is not in conformity with the requirement for this field.");
    }

    public void listAllEvents() throws SQLException {

        Integer i = -1;
        for (Event event : databaseRepository.retrieveAllEvents()){
            if(event instanceof CarMeet){

                System.out.println(" Venue name: " + ((CarMeet) event).eventName + " at location:");
                Location location = ((CarMeet) event).getEventLocation();
                ++i;
                System.out.println("         " + i + ".Location: " + location.getName());
                System.out.println("           Address: " + location.getAddress());
                System.out.println("           Date and time: " + location.getDateTime());
                System.out.println("           Price: " + ((CarMeet) event).getEnteryFee());
                System.out.println("           Prize: " + ((CarMeet) event).getPrize());
                System.out.println("           No. tickets left: " + ((CarMeet) event).getticketsLeft());
            }
            else if(event instanceof BookSigning){

                System.out.println(" Venue name: " + ((BookSigning) event).eventName + " at location:");
                Location location = ((BookSigning) event).getEventLocation();
                ++i;
                System.out.println("         " + i + ".Location: " + location.getName());
                System.out.println("           Address: " + location.getAddress());
                System.out.println("           Date and time: " + location.getDateTime());
                System.out.println("           Free admittance!");
                System.out.println("           Author: " + ((BookSigning) event).getAuthor());
                System.out.println("           Book: " + ((BookSigning) event).getBookTitle());
                if(((BookSigning) event).getHasBookRead())
                    System.out.println("           BONUS! Book reading with the author.");
            }
            else if(event instanceof MusicConcert){

                System.out.println(" Venue name: " + ((MusicConcert) event).eventName + " at location:");
                Location location = ((MusicConcert) event).getEventLocation();
                ++i;
                System.out.println("         " + i + ".Location: " + location.getName());
                System.out.println("           Address: " + location.getAddress());
                System.out.println("           Date and time: " + location.getDateTime());
                System.out.println("           Price: " + ((MusicConcert) event).getPrice());
                System.out.println("           Artists: " + ((MusicConcert) event).getArtists());
                System.out.println("           No. tickets left: " + ((MusicConcert) event).getticketsLeft());
            }
            else if(event instanceof Play){

                System.out.println(" Venue name: " + ((Play) event).eventName + " at location:");
                Location location = ((Play) event).getEventLocation();
                ++i;
                System.out.println("         " + i + ".Location: " + location.getName());
                System.out.println("           Address: " + location.getAddress());
                System.out.println("           Date and time: " + location.getDateTime());
                System.out.println("           Price: " + ((Play) event).getPrice());
                System.out.println("           Group: " + ((Play) event).getGroupName());
                System.out.println("           List of Plays: " + ((Play) event).getlistOfPlays());
                System.out.println("           No. tickets left: " + ((Play) event).getticketsLeft());
            }

        }
    }

    public void updateOrderBookSigning(BookSigning event, User user) throws SQLException {
        Location location = event.getEventLocation();
        Ticket ticket = new Ticket(event.getEventName(), 0.0, location.getName(), location.getDateTime());
        databaseRepository.addTicket(event.getEventName(), 0.0, location.getName(), location.getDateTime().toString());

        user.addTicket(ticket);
    }

    public void updateOrderCarMeet(CarMeet event, Integer noTickets, User user) throws SQLException {
        Location location = event.getEventLocation();
        while (noTickets > 0){
            --noTickets;

            Ticket ticket = new Ticket(event.getEventName(), event.getEnteryFee(), location.getName(), location.getDateTime());
            databaseRepository.addTicket(event.getEventName(), event.getEnteryFee(), location.getName(),location.getDateTime().toString());

            user.addTicket(ticket);
        }
    }

    public void updateOrderMusicConcert(MusicConcert event, Integer noTickets, User user) throws SQLException {
        Location location = event.getEventLocation();
        while (noTickets > 0){
            --noTickets;

            Ticket ticket = new Ticket(event.getEventName(), event.getPrice(), location.getName(), location.getDateTime());
            databaseRepository.addTicket(event.getEventName(), event.getPrice(), location.getName(), location.getDateTime().toString());

            user.addTicket(ticket);
        }
    }

    public void updateOrderPlay(Play event, Integer noTickets, User user) throws SQLException {
        Location location = event.getEventLocation();
        while (noTickets > 0) {
            --noTickets;

            Ticket ticket = new Ticket(event.getEventName(), event.getPrice(), location.getName(), location.getDateTime());
            databaseRepository.addTicket(event.getEventName(), event.getPrice(), location.getName(), location.getDateTime().toString());

            user.addTicket(ticket);
        }
    }

    public void updateTicketsLeft(Event event, Integer noTickets, User user) throws SQLException {

        if(event instanceof CarMeet){

            if( ((CarMeet) event).getticketsLeft() < noTickets ){
                throw new RuntimeException("Baby we ain't got that many tickets!");
            }
            else
            {
                Integer leftTickets = ((CarMeet) event).getticketsLeft() - noTickets;
                ((CarMeet) event).setticketsLeft(leftTickets);
                databaseRepository.updateTicketsNumber(leftTickets, event.getEventName());

                updateOrderCarMeet(((CarMeet) event), noTickets, user);
            }
        }
        else if(event instanceof MusicConcert){

            if( ((MusicConcert) event).getticketsLeft() < noTickets ){
                throw new RuntimeException("Baby we ain't got that many tickets!");
            }
            else
            {
                Integer leftTickets = ((MusicConcert) event).getticketsLeft() - noTickets;
                ((MusicConcert) event).setticketsLeft(leftTickets);
                databaseRepository.updateTicketsNumber(leftTickets, event.getEventName());
                updateOrderMusicConcert(((MusicConcert) event), noTickets, user);
            }
        }
        else if(event instanceof Play) {
            if (((Play) event).getticketsLeft() < noTickets) {
                throw new RuntimeException("Baby we ain't got that many tickets!");
            } else {
                Integer leftTickets = ((Play) event).getticketsLeft() - noTickets;
                ((Play) event).setticketsLeft(leftTickets);
                databaseRepository.updateTicketsNumber(leftTickets, event.getEventName());
                updateOrderPlay(((Play) event), noTickets, user);
            }
        }
    }

    public void printOrder(List<User> allUsers, User user, String name) throws SQLException {
        user.setName(name);
        allUsers.add(user);

        System.out.println("  \nThank you for your order " + name + "!\nHere's a sneak peak of what's waiting for you:");
        Integer i = 0;
        Integer count = 0;
        String lastEvent = null;
        String currentEvent = null;

        for ( Ticket ticket : user.getTickets()) {
            ++i;
            ++count;
            System.out.println("    " + i + ". " + ticket.getEventName() + "  Located at:" + ticket.getLocation() + "   on: " + ticket.getTime());

            currentEvent = ticket.getEventName();

            if (!currentEvent.equals(lastEvent) && currentEvent != null) {

                Integer id = databaseRepository.getTicketId(ticket.getEventName());
                databaseRepository.addOrder(count, id, ticket.getEventName());
                count = 0;
            }

            lastEvent = currentEvent;
        }


        Double totalCost =  user.getTickets().stream()
                                .map(ticket -> ticket.getEventPrice())
                                .reduce(0.0, (a,b) -> a + b);

        System.out.println("     And for all this fun you only spent: " + totalCost + "$");
    }

    public void showEventsFromDate(String dateTime, List<Event> allEvents) throws SQLException {

//        List<Event> events = allEvents.stream()
//                .filter(event -> event.getEventLocation().getDateTime().isEqual(dateTime)).toList();

        List<Event> events = databaseRepository.getEventByDate(dateTime);

        System.out.println(" We found: " + events.size() + " matches.");

        if(events.size() > 0) {
            events.forEach(System.out::println);
        }
        else {
            System.out.println("What are you still waiting for? 0 means NADA, ZIP, ZERO.");
        }
    }

    public void showEventsFromLocation(String input, List<Event> allEvents) throws SQLException {

//        List<Event> events = allEvents.stream()
//                .filter(event -> event.getEventLocation().getAddress().equalsIgnoreCase(input)).toList();

        List<Event> events = databaseRepository.getEventByLocation(input);

        System.out.println(" We found: " + events.size() + " matches.");

        if(events.size() > 0) {
            events.forEach(System.out::println);
        }
        else {
            System.out.println("What are you still waiting for? 0 means NADA, ZIP, ZERO.");
        }
    }

    public void deleteSoldOutEvents() throws SQLException {

//        Integer count = 0;
//        for (Event event : allEvents){
//            if (event instanceof CarMeet){
//                if(((CarMeet) event).getticketsLeft() == 0) {
//                    allEvents.remove(((CarMeet) event));
//                    count++;
//                }
//            }
//            else
//            if (event instanceof MusicConcert){
//                if(((MusicConcert) event).getticketsLeft() == 0) {
//                    allEvents.remove(((MusicConcert) event));
//                    count++;
//                }
//            }
//            else
//            if (event instanceof Play) {
//                if (((Play) event).getticketsLeft() == 0) {
//                    allEvents.remove(((Play) event));
//                    count++;
//                }
//            }
//        }
//
//        System.out.println("There have been eliminated : " + count + " events.");

        Integer count = databaseRepository.deleteSoldOutEvents();
        System.out.println("There have been eliminated: " + count + " events.");
    }
}


























