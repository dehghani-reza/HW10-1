package ir.mctab.java32.hw10.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "userName", nullable = false, length = 22)
    private String userName;

    @Column(name = "nationalCode", nullable = false, length = 22)
    private Long nationalCode;

    @Column(name = "birthday", nullable = false, length = 22)
    private String birthday;

    @Column(name = "password", nullable = false, length = 22)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    List<Article> articles = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "User_Rols"
            , joinColumns = @JoinColumn(name = "User_Id")
            , inverseJoinColumns = @JoinColumn(name = "Role_Id"))
    Set<Role> roles = new HashSet<>();

    public User(String userName, Long nationalCode, String birthday) {
        this.userName = userName;
        this.nationalCode = nationalCode;
        this.birthday = birthday;
        this.password = String.valueOf(nationalCode);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                '}';
    }

}


