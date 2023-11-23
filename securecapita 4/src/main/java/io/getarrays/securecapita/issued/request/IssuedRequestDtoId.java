package io.getarrays.securecapita.issued.request;


import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class IssuedRequestDtoId {
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
