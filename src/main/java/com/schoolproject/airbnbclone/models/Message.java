package com.schoolproject.airbnbclone.models;

import com.schoolproject.airbnbclone.utils.TimeManager;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private LocalDate creationDate;

    @Column(name = "is_read", nullable = false)
    private Boolean read;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private User recipient;

    public Message(String title, String body, User sender, User recipient) {
        this.title = title;
        this.body = body;
        this.sender = sender;
        this.recipient = recipient;
        this.creationDate = TimeManager.now();
        this.read = false;
    }

}
