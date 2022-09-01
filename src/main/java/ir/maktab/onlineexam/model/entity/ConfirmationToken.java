package ir.maktab.onlineexam.model.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private Date expiryDate;

    @NonNull
    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;


    public ConfirmationToken(User user) {
        this.user = user;
        createdDate = new Date();
        expiryDate = calculateExpiryDate();
        token = UUID.randomUUID().toString();
    }

    public ConfirmationToken() {
    }

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(createdDate);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return new Date(cal.getTime().getTime());
    }
}