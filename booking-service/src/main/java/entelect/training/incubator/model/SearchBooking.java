package entelect.training.incubator.model;

import lombok.Getter;

@Getter
public class SearchBooking {
    private Integer customerId;
    private String referenceNumber;
    private SearchType searchType;
}
