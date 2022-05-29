package printer;


import event.Event;
import user.Ticket;
import user.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;

public class PrintingService {
    private final File file = new File("D:\\School\\VanzareBileteOnline-main\\DataLog.txt");
    Path path = Paths.get(file.getPath());

    public void logOrder(User user) throws IOException {
        Files.writeString(path, user.getName() + "\n", APPEND);

        for (Ticket ticket : user.getTickets()) {
            try {
                Files.writeString(path, ticket.toString() + "\n", APPEND);
            } catch (IOException e) {
                e.printStackTrace();

            }

        }
    }

    public void logEvents(List<Event> events) throws IOException {
        Files.writeString(path, "EVENTS\n");
            Integer ind = 1;
            for( Event event: events) {
                try {
                    Files.writeString(path, ind + ". "+event.getEventName() + ", " + event.getEventLocation() +"\n", APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ind += 1;
            }
        }

    public void logAllOrders(List<User> users) throws IOException {
        Files.writeString(path,   "USERS\n");
        Integer ind = 1;
        for( User user: users) {
            try {
                Files.writeString(path, ind + ". "+user.getName() +"\n", APPEND);
                for (Ticket ticket : user.getTickets()){
                    Files.writeString(path, ticket.toString() + "\n", APPEND);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            ind += 1;
        }

    }
}