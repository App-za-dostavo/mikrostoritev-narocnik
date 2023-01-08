package si.fri.rso.narocnik.models.entities;

import javax.persistence.*;

@Entity
@Table(name = "narocnik")
@NamedQueries(value =
        {
                @NamedQuery(name = "ClientEntity.getAll", query = "SELECT person FROM ClientEntity person"),
                @NamedQuery(name = "ClientEntity.getById", query = "SELECT person FROM ClientEntity person WHERE person.id=:id")
        })
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "vip")
    private Boolean vip;

    @Column(name = "location")
    private Float location;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }

    public Float getLocation() {
        return location;
    }

    public void setLocation(Float location) {
        this.location = location;
    }
}