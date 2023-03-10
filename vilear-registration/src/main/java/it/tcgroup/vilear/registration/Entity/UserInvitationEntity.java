package it.tcgroup.vilear.registration.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "users_invitations")
@Getter
@Setter
public class UserInvitationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "user_id",referencedColumnName = "id",nullable = true)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "role_code",nullable = false)
    private RoleEntity role;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "landing_at")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp landingAt;

    @Column(name = "expiration_date",nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp expirationDate;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "invitation_token", unique = true,nullable = false,columnDefinition = "uuid")
    private UUID invitationToken=UUID.randomUUID();

    @Column(name = "active")
    private Boolean active;

}
