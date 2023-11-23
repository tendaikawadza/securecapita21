/**
 * @author kunal
 * 
 */
package io.getarrays.securecapita.purchaserequest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.util.ImageUtils;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Service
@RequiredArgsConstructor
@Slf4j
public class PurchaseRequestNewServiceImpl implements PurchaseRequestNewService {

	private final PurchaseRequestNewRepository purchaseRequestRepository;
	private final ModelMapper modelMapper;
	private final PurchaseRequestMapper mapper;

	@Override
	public List<PurchaseResponseDto> findAll() {

		return purchaseRequestRepository.findAll().stream()
				.map(purchase -> modelMapper.map(purchase, PurchaseResponseDto.class)).collect(Collectors.toList());
	}

	@Override
	public PurchaseResponseDto findById(Long id) {

		return modelMapper.map(
				purchaseRequestRepository.findById(id).orElseThrow(PurchaseRequestNotFoundException::new),
				PurchaseResponseDto.class);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public PurchaseRequestDto saveFile(MultipartFile file, PurchaseRequestDto pEntity) {
		log.info("saveFile method value for fileName from Service class '{}' " , file.getOriginalFilename());
		purchaseRequestRepository.saveFile(file, modelMapper.map(pEntity, PurchaseRequestEntity.class));
		return pEntity;



	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseDto insert(PurchaseRequestDto purchaseRequest) {

		return purchaseRequestRepository.save(modelMapper.map(purchaseRequest, PurchaseRequestEntity.class)) > 0
				? new ResponseDto("Purchase Request Created Successfully!")
				: new ResponseDto("Purchase Request Created Failed!");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseDto update(PurchaseRequestDtoId purchaseRequest) {

		return purchaseRequestRepository.update(modelMapper.map(purchaseRequest, PurchaseRequestEntity.class)) > 0
				? new ResponseDto("Purchase Request Updated Successfully!")
				: new ResponseDto("Purchase Request Updated Failed!");
	}

	@Override
	public ResponseDto delete(Long id) {

		return purchaseRequestRepository.deleteById(id) > 0 ? new ResponseDto("Purchase Request Deleted Successfully!")
				: new ResponseDto("Purchase Request Deleted Failed!");
	}

	@Override
	public List<PurchaseResponseDto> findAll(io.getarrays.securecapita.purchaserequest.Page page) {
		Stream<PurchaseRequestEntity> stream = purchaseRequestRepository.findAll(page);
		return stream.map(mapper::toDTO).collect(Collectors.toList());
	}

}
