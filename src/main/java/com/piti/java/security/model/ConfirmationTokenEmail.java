package com.piti.java.security.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "confirmation_tokens_email")
@Entity
@NoArgsConstructor
public class ConfirmationTokenEmail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(nullable = false,name = "user_id")
    private User User;
    
    public ConfirmationTokenEmail(String token, LocalDateTime createdAt, LocalDateTime expiresAt, User User) {
		this.token = token;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
		this.User = User;
    }
}
