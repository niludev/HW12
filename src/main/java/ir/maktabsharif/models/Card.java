package ir.maktabsharif.models;

import ir.maktabsharif.models.base.BaseDomain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
        name = Card.TABLE_NAME,
        indexes = {
                @Index(columnList = Card.BANK_NAME_COLUMN),
                @Index(columnList = Card.CARD_NUMBER_COLUMN)
        }
)
@Getter
@Setter
@NoArgsConstructor
@ToString

// har card faghat male ye user hast (ManyToOne)
public class Card extends BaseDomain<Long> {

    public static final String TABLE_NAME = "cards";
    public static final String CARD_NUMBER_COLUMN = "card_number";
    public static final String BANK_NAME_COLUMN = "bank_name";
    public static final String BALANCE_COLUMN = "balance";
    public static final String USERID_COLUMN = "userId";

    @Column(name = CARD_NUMBER_COLUMN)
    private String cardNumber;

    @Column(name = BANK_NAME_COLUMN)
    private String bankName;

    @Column(name = BALANCE_COLUMN)
    private Long balance;

    //foreign key
    // har card be ye user vasl hast
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = USERID_COLUMN, nullable = false) // customize sotune foreign key
    private User user;
}
