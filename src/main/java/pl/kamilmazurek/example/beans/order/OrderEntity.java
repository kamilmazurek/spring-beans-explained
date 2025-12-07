package pl.kamilmazurek.example.beans.order;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {

    @Id
    private Long id;

    private LocalDate orderDate;

    private double totalAmount;

    @ElementCollection
    @Column(name = "product")
    @CollectionTable(name = "order_products", joinColumns = @JoinColumn(name = "order_id"))
    private List<String> products;


}
