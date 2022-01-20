package everyone.delivery.demo.address;

import javax.persistence.Embeddable;

@Embeddable
public class InterestedAddress {
    private Address address;
    private Long radius;
}
