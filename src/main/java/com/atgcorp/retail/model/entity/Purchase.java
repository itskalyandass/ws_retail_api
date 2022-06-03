package com.atgcorp.retail.model.entity;

import com.atgcorp.retail.util.OffsetDateTimeConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "purchase")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PURCHASEID", nullable = false)
    private Long purchaseId;

    @Column(name = "AMOUNT")
    private Double amount;

    @ManyToOne
    @JoinColumn(
            name = "CUSTOMERID",
            referencedColumnName = "CUSTOMERID"
    )
    private Customer customerPurchase;

    @Column(name = "CREATETIMESTAMP", nullable = false, updatable = false)
    @CreatedDate
    @Convert(converter = OffsetDateTimeConverter.class)
    private OffsetDateTime createTimestamp;
}