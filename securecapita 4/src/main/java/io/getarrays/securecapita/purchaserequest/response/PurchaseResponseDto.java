/**
 * Kumar.Kunal
 */
package io.getarrays.securecapita.purchaserequest.response;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.*;
import lombok.experimental.FieldDefaults;
/**
 * Kumar.Kunal
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PurchaseResponseDto {
	
	private long id;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date purchaseDate;
	
	private  String requestingDepartment;
	private int departmentCode;
	private String requestReason;
	private int itemNumber;
	private String itemDescription;
	private int unitPrice;
	private int quantity;
	private int estimatedValue;
	private String emailAddress;
	
	private String name;
	private String type;
	
	@Column(name = "profileImage", nullable = false, columnDefinition = "BINARY(256)", length = 256)
	@Lob
	private byte[] profileImage;
}
