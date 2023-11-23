/**
 * @author kunal
 * 
 */
package io.getarrays.securecapita.purchaserequest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.getarrays.securecapita.purchaserequest.request.PurchaseRequestDto;
import io.getarrays.securecapita.purchaserequest.request.PurchaseRequestDtoId;
import io.getarrays.securecapita.purchaserequest.response.PurchaseResponseDto;
import io.getarrays.securecapita.purchaserequest.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Kumar.Kunal
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/purchaserequest") // giving a different name as purchase request name might be existing, please change to specific
@Slf4j
public class PurchaseRequestNewController {
	
	private final PurchaseRequestNewService purchaseRequestService;
	
	@GetMapping("/pagespage")
	public List<PurchaseResponseDto> findAll(@RequestParam(value = "page", defaultValue = "1") Long page,
            @RequestParam(value = "size", defaultValue = "10") Long size) {
            	log.info("GET - /purchase request -> finalAll For Pagination Call entered ");
            	return purchaseRequestService.findAll(Page.of(page, size));
            }
	
	@GetMapping
    public List<PurchaseResponseDto> purchaserequest() {
        log.info("GET - /purchase request -> request none");
        List<PurchaseResponseDto> purchaseResponse = purchaseRequestService.findAll();
        log.info("GET - /purchase request - response -> {}"+ purchaseResponse);
        return purchaseResponse;
    }
	
	@GetMapping("/{id}")
    public PurchaseResponseDto purchaseById(@PathVariable Long id) {
        log.info("GET - /purchase request/{} -> request", id);
        PurchaseResponseDto purchaseByIdResponse = purchaseRequestService.findById(id);
        log.info("GET - /purchase request/{} - response -> {} {} "+ id + "\n" + purchaseByIdResponse);
        return purchaseByIdResponse;
    }
	
//	@PostMapping("/upload")
//	 public ResponseEntity<PurchaseRequestDto> uploadFiles(@RequestParam("file") MultipartFile file,
//			 PurchaseRequestDto purchaseRequestDto) {
//		log.info("POST - /purchase request for Multipart Upload - request -> {}" + purchaseRequestDto);
//	 return ResponseEntity.ok(purchaseRequestService.saveFile(file, purchaseRequestDto));
//	 }
    @PostMapping("/upload")
    public ResponseEntity<PurchaseRequestDto> uploadFiles(@RequestParam("file") MultipartFile file,
                                                          @RequestBody PurchaseRequestDto purchaseRequestDto) {
        log.info("POST - /purchase request for Multipart Upload - request -> {}", purchaseRequestDto);
        return ResponseEntity.ok(purchaseRequestService.saveFile(file, purchaseRequestDto));
    }










//
//    @PostMapping("/upload")
//    public ResponseEntity<List<PurchaseRequestDto>> uploadFiles(@RequestParam("file") MultipartFile file,
//                                                                @RequestBody List<PurchaseRequestDto> purchaseRequestDtoList) {
//        log.info("POST - /purchase request for Multipart Upload - request -> {}", purchaseRequestDtoList);
//        List<PurchaseRequestDto> savedPurchaseRequests = new ArrayList<>();
//
//        for (PurchaseRequestDto purchaseRequestDto : purchaseRequestDtoList) {
//            savedPurchaseRequests.add(purchaseRequestService.saveFile(file, purchaseRequestDto));
//        }
//
//        return ResponseEntity.ok(savedPurchaseRequests);
//    }
	
	/*
	@PostMapping
    public ResponseDto insert(@RequestBody PurchaseRequestDto purchaseRequestDto) {
        log.info("POST - /purchase request - request -> {}" + purchaseRequestDto);
        ResponseDto insertResponse =  purchaseRequestService.insert(purchaseRequestDto);
        log.info("POST - /purchase request -> response none - created successfully");
        return insertResponse;
    }
    */
	
	@PutMapping
    public ResponseDto update(@RequestBody PurchaseRequestDtoId purchaseRequestDto) {
        log.info("PUT - /purchase request - request -> {}"+ purchaseRequestDto);
        ResponseDto updatedResponse =  purchaseRequestService.update(purchaseRequestDto);
        log.info("PUT - /purchase request -> response none - updated successfully");
        return updatedResponse;
    }
	
	@DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Long id) {
        log.info("DELETE - /purchase request/{} -> request", id);
        ResponseDto deleteResponse =  purchaseRequestService.delete(id);
        log.info("DELETE - /purchase request -> response none");
        return deleteResponse;
    }
	
	/*
	@PostMapping
    public ResponseEntity<AgreementCreationResponse> createAgreement(@RequestBody String userEmail) {
        try {
            AgreementCreationResponse agreement = signatureService.createAgreement(userEmail);
            return ResponseEntity.ok(agreement);
        } catch (ApiException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    */

}