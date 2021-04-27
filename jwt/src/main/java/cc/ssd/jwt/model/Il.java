package cc.ssd.jwt.model;


import javax.persistence.*;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(	name = "il",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "kodu"),
                @UniqueConstraint(columnNames = "adi")
        })

public class Il {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ilid;

    //@Column(name = "\"kodu\"")
    @NotBlank
    @Size(max = 2)
    private String kodu;

    //@Column(name = "\"adi\"")
    @NotBlank
    @Size(max = 50)
    private String adi;

    @OneToMany(mappedBy = "il")
    Set<User> user=new HashSet<User>();

    public Il() {
    }

    public int getId() {
        return ilid;
    }

    public void setId(int id) {
        this.ilid = id;
    }

    public String getKodu() {
        return kodu;
    }

    public void setKodu(String kodu) {
        this.kodu = kodu;
    }

    public String getAdi() {
        return adi;
    }

    public void setAdi(String adi) {
        this.adi = adi;
    }
}
