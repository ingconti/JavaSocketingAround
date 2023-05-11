import java.io.Serializable;
import java.util.Date;

// must implement Serializable in order to be sent
public class Message implements Serializable {
    private final String text;
    private final Date date;

    public Message(String text) {
        this.text = text;
        this.date = new Date();
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}