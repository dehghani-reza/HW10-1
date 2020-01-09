package ir.mctab.java32.hw10.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true)
    private Long zipCode;

    private String city;

    private String street;

    private String alley;

    @Column(name = "user_id" )
    private Long userId;

    public Address(Long zipCode , String city , String street , String alley , Long userId) {
        this.zipCode = zipCode;
        this.city=city;
        this.street=street;
        this.alley=alley;
        this.userId=userId;
    }
}
