package io.getarrays.securecapita.issued;

import io.getarrays.securecapita.issued.request.IssuedRequestDto;
import io.getarrays.securecapita.issued.response.IssueResponseDto;
import io.getarrays.securecapita.purchaserequest.PurchaseRequestEntity;
import io.getarrays.securecapita.purchaserequest.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class IssuedNewServiceImpl  implements IssueNewService {


    private final IssuedNewRepository  issuedNewRepository;
    private final ModelMapper modelMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public IssuedRequestDto saveFile(MultipartFile file, IssuedRequestDto pEntity) {
        log.info("saveFile method value for fileName from Service class '{}' " , file.getOriginalFilename());
        issuedNewRepository.saveFile(file, modelMapper.map(pEntity, IssuedEntity.class));
        return pEntity;

    }

    @Override
    public ResponseDto delete(Long id) {

        return issuedNewRepository.deleteById(id) > 0 ? new ResponseDto("Issued Deleted Successfully!")
                : new ResponseDto("Issue Deleted Failed!");
    }

    @Override
    public List<IssueResponseDto> findAll(io.getarrays.securecapita.issued.Page page) {
        Stream<PurchaseRequestEntity> stream = issuedNewRepository.findAll(page);
        return stream.map(mapper::toDTO).collect(Collectors.toList());
    }



}
