package ir.maktabsharif.models;

import ir.maktabsharif.models.base.BaseDomain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = Transaction.TABLE_NAME,
        indexes = {
                @Index(columnList = Transaction.ID_COLUMN, unique = true),
                @Index(columnList = Transaction.BATCH_ID_COLUMN)
        }
)
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Transaction extends BaseDomain<Long> {

    public static final String TABLE_NAME = "transactions";
    public static final String FROM_CARD_ID_COLUMN = "from_card_id";
    public static final String TO_CARD_ID_COLUMN = "to_card_id";
    public static final String AMOUNT_COLUMN = "amount";
    public static final String FEE_COLUMN = "fee";
    public static final String TYPE_COLUMN = "type";
    public static final String STATUS_COLUMN = "status";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String BATCH_ID_COLUMN = "batch_id";
    public static final String CREATED_AT_COLUMN = "created_at";


    @Column(name = FROM_CARD_ID_COLUMN, nullable = false)
    private Long fromCardId;

    @Column(name = TO_CARD_ID_COLUMN, nullable = false)
    private Long toCardId;

    @Column(name = AMOUNT_COLUMN, nullable = false)
    private Long amount;

    @Column(name = FEE_COLUMN)
    private Long fee;

    @Column(name = TYPE_COLUMN)
    private String type;

    @Column(name = STATUS_COLUMN)
    private String status;

    @Column(name = DESCRIPTION_COLUMN, columnDefinition = "VARCHAR")
    private String description;

    @Column(name = BATCH_ID_COLUMN)
    private Long batchId;

    @CreationTimestamp
    @Column(name = CREATED_AT_COLUMN, nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // ya in:
    // in method ghabl az persist shodan dar DB seda zade mishe
    // persist: amade shodan baraye zakhire / vared shodan be chakheye JPA
    // (ye object baraye avalinbar be halate modiriat shode tabdil beshe va amadeye zakhire shodan dar DB beshe)
//    @PrePersist
//    protected void onCreate() {
//        if (createdAt == null) {
//            createdAt = LocalDateTime.now();
//        }
//    }
}
