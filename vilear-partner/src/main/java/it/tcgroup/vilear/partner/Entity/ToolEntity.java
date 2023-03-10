package it.tcgroup.vilear.partner.Entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tools")
@Setter
@Getter
public class ToolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id",nullable = false)
    private PartnerEntity partner;

    @Column(name = "name_tool",nullable = false)
    private String name;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "daily_cost",nullable = false)
    private Float dailyCost;

    @Column(name = "availability",nullable = false)
    private Integer availability;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;



}