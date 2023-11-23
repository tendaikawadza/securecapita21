/**
 * @author kunal
 * 
 */
package io.getarrays.securecapita.purchaserequest;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import io.getarrays.securecapita.purchaserequest.request.PurchaseRequestDto;
import io.getarrays.securecapita.purchaserequest.request.PurchaseRequestDtoId;
import io.getarrays.securecapita.purchaserequest.response.PurchaseResponseDto;
import io.getarrays.securecapita.purchaserequest.response.ResponseDto;

/**
 * Kumar.Kunal
 */
public interface PurchaseRequestNewService {
	
	PurchaseRequestDto saveFile(MultipartFile file, PurchaseRequestDto pEntity);

	List<PurchaseResponseDto> findAll();

	PurchaseResponseDto findById(Long id);

	ResponseDto insert(PurchaseRequestDto purchaseRequest);

	ResponseDto update(PurchaseRequestDtoId purchaseRequest);

	ResponseDto delete(Long id);

	List<PurchaseResponseDto> findAll(Page page);

}