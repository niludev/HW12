package ir.maktabsharif.models.base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@MappedSuperclass // chon BaseDomain khodesh entity nist va Table ham nadare dar DB ama mikhaim field haii ke dare tavasote child hash vaghti javaii daran be ers miresan dar DB map beshan dar un Table.
public class BaseDomain<ID extends Number> implements Serializable {

    public static final String ID_COLUMN = "id";


    @Id
    @Column(name = ID_COLUMN)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;

//    private LocalDate createDate;
}
