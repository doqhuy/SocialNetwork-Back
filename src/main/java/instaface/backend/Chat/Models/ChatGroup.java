package instaface.backend.Chat.Models;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="chatgroup")


public class ChatGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stt")
    private long stt;

    @Column(name = "name")
    private String name;

    @Column(name = "admin")
    private String admin;

    @Column(name = "count")
    private long count;

    @Column(name = "groupimage")
    private String groupimage;
}
