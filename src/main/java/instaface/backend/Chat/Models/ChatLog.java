package instaface.backend.Chat.Models;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="chatlog")

public class ChatLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stt2")
    private long stt2;


    @Column(name = "stt")
    private long stt;

    @Column(name = "idsender")
    private String idsender;


    @Column(name = "text")
    private String text;

    @Column(name = "image")
    private boolean image = false;


    @Column(name = "time")
    @DateTimeFormat(pattern = "dd-MM-yyyy'T'HH:mm:ss")
    private String time;



    public void setTime(String time) {
        this.time = time;
    }

    public ChatLog(String text, String time) {
        this.text = text;
        this.time = time;
    }
    public String getName() {
        return text;
    }
}
