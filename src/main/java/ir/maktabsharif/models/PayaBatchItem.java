package ir.maktabsharif.models;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class PayaBatchItem {

    private String toCardNumber;

    private Long amount;
}
