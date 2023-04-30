package com.example.myquora.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "threads")
public class ThreadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "thread_sequence")
    @SequenceGenerator(name = "thread_generator", sequenceName = "thread_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank
    @Column(name = "title", updatable = false, nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(name = "content", updatable = false, nullable = false, columnDefinition = "TEXT")
    private String content;

    @NotNull
    private Boolean locked;

    @NotNull
    @Column(name = "local_date_time", updatable = false, nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime localDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner")
    private UserEntity owner;

    @PrePersist
    private void createdAt() {
        this.localDateTime = LocalDateTime.now();
        this.locked = false;
    }

}
