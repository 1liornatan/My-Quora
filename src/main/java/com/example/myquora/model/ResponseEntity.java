package com.example.myquora.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "responses")
public class ResponseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "response_sequence")
    @SequenceGenerator(name = "response_generator", sequenceName = "response_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "local_date_time", updatable = false, nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime localDateTime;

    @Column(name = "content", updatable = false, nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author")
    private UserEntity author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread")
    private ThreadEntity thread;
}