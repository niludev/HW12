package ir.maktabsharif.models;

import jakarta.persistence.Column;
import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PayaBatchItem {

    private String toCardNumber;

    private Long amount;
}
