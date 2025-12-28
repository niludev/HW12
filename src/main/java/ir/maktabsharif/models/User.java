package ir.maktabsharif.models;

import ir.maktabsharif.models.base.BaseDomain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// NoArgsConstructor: constructor e khali

@Entity
@Table(
        name = User.TABLE_NAME,
        indexes = {  // baraye search sari tar --> ke record ha ro tak tak check nakone
//            @Index(columnList = User.ID_COLUMN),
            @Index(columnList = User.USERNAME_COLUMN, unique = true)
        }
)
@Setter
@Getter
@NoArgsConstructor
@ToString

public class User extends BaseDomain<Long> {
    public static final String TABLE_NAME = "users";
//    public static final String ID_COLUMN = "id";
    public static final String USERNAME_COLUMN = "username";
    public static final String PASSWORD_COLUMN = "password";

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // dar zamane Insert ID ro misaze
//    @Column(name = ID_COLUMN)
//    private Long id;

    @Column(name = USERNAME_COLUMN, nullable = false, unique = true)
    private String username;

    @Column(name = PASSWORD_COLUMN, nullable = false)
    private String password;
}
