/**
 * 
 */
package io.getarrays.securecapita.purchaserequest;

import org.mapstruct.Mapper;

import io.getarrays.securecapita.purchaserequest.response.PurchaseResponseDto;


/**
 * @author Kumar.Kunal
 *
 */
@Mapper(componentModel = "spring")
public interface PurchaseRequestMapper {
	
	PurchaseRequestEntity toEntity(PurchaseResponseDto purchaseResponseDto);

	PurchaseResponseDto toDTO(PurchaseRequestEntity purchaseRequestEntity);

}
