package it.tcgroup.vilear.partner.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "logistics")
@Getter
@Setter
public class LogisticEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "partner_id",nullable = false)
    private PartnerEntity partner;

    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "city",nullable = false)
    private String city;

    @Column(name = "persons_capacity",nullable = false)
    private Integer personCapacity;

    @Column(name = "daily_cost",nullable = false)
    private Float dailyCost;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

}
