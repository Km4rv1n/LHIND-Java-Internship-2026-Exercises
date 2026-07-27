package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chassis_number", nullable = false, unique = true)
    private String chassisNumber;

    @Column(name = "plate_number", nullable = false, unique = true)
    private String plateNumber;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User ownedBy;

    @OneToMany(mappedBy = "vehicle" )
    private List<Ticket> tickets = new ArrayList<>();

    @Override
    public String toString() {
        return "Vehicle{" +
                "chassisNumber='" + chassisNumber + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                '}';
    }
}
