/**
 * Kumar.Kunal
 */
package io.getarrays.securecapita.purchaserequest.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
/**
 * Kumar.Kunal
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExceptionResponse {
    
	LocalDateTime localDateTime;
    String message;
}
