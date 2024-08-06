package microservices.menu_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Setter;

import java.util.List;

@Setter
@Entity
public class Menu {
    @Id
    @GeneratedValue
    private Long id;

    private Long restaurantId;

    private Boolean active = true;

    @Transient
    private List<MenuItem> menuItems;
}
