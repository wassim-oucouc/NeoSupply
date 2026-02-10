package org.example.neosupply.dto.request;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.neosupply.entity.Shipment;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarrierDTO {

    private Long id;
    private String name;
    private Boolean active;
}
