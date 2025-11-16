package pl.kamilmazurek.example.item;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "items")
public class ItemEntity {

    @Id
    private Long id;

    private String name;

}