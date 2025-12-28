package ir.maktabsharif.models;

import ir.maktabsharif.models.base.BaseDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(
        name = Card.TABLE_NAME,
        indexes = {
                @Index(columnList = Card.BANK_NAME_COLUMN)
        }
)
@Getter
@Setter
@NoArgsConstructor
@ToString
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

    @Column(name = USERID_COLUMN)
    private Long userId;
}
