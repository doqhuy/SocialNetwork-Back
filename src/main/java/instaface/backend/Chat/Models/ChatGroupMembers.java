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
@Table(name="chatgroupmembers")


public class ChatGroupMembers {

    @Column(name = "stt")
    private long stt;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stt2")
    private long stt2;

    @Column(name = "idmember")
    private String idmember;

    @Column(name = "namemember")
    private String namemember;

    @Column(name = "avatamember")
    private String avatamember;

}
