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
@Table(name="chatdefine")


public class ChatDefine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stt")
    private long stt;

    @Column(name = "ida")
    private String ida;

    @Column(name = "idb")
    private String idb;




}
