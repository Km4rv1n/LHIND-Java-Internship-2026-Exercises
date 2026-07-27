package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.TicketStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ticket_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "issue_date", nullable = false)
    private LocalDateTime issueDate;

    @Column(name = "date_paid")
    private LocalDateTime datePaid;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issued_by_id", nullable = false)
    private User issuedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    public Ticket(BigDecimal price, LocalDateTime issueDate, LocalDateTime datePaid, TicketStatus status, String description) {
        this.price = price;
        this.issueDate = issueDate;
        this.datePaid = datePaid;
        this.status = status;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "price=" + price +
                ", issueDate=" + issueDate +
                ", datePaid=" + datePaid +
                ", status=" + status +
                ", description='" + description + '\'' +
                '}';
    }
}
