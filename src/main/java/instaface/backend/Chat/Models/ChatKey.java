package instaface.backend.Chat.Models;

import javax.persistence.*;



import java.io.Serializable;

@Embeddable
public class ChatKey implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stt2")
    private long stt2;
    @Column(name = "stt")
    private long stt;


    // Constructors, getters, and setters
}

