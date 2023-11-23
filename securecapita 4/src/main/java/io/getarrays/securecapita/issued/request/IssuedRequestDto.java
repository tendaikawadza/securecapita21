package io.getarrays.securecapita.issued.request;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
public class IssuedRequestDto {
    private long id;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date issueDate;
    private String issueTo;
    private int orderNumber;
    private String dispatch;
    private String preparedBy;
    private String itemDescription;
    private int quantity;
    private int unitPrice;
    private int estimatedValue;
    private String name;
    private String type;

    @Column(name = "profileImage", nullable = false, columnDefinition = "BINARY(256)", length = 256)
    @Lob
    private byte[] profileImage;
}
