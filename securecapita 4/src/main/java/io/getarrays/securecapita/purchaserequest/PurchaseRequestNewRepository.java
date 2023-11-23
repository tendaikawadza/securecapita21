/**
 * @author kunal
 * 
 */
package io.getarrays.securecapita.purchaserequest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

/**
 * Kumar.Kunal
 */
public interface PurchaseRequestNewRepository {
	
	PurchaseRequestEntity saveFile(MultipartFile file, PurchaseRequestEntity pEntity);

	int save(PurchaseRequestEntity purchaseRequest);

	int update(PurchaseRequestEntity purchaseRequest);

	Optional<PurchaseRequestEntity> findById(Long id);

	int deleteById(Long id);

	List<PurchaseRequestEntity> findAll();

	Stream<PurchaseRequestEntity> findAll(io.getarrays.securecapita.
												  purchaserequest.Page page);
	
}
