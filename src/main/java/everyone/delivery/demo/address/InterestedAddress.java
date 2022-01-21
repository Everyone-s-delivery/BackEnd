package everyone.delivery.demo.address;

import everyone.delivery.demo.address.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterestedAddress {
    private Address address;
    private Long radius;
}
