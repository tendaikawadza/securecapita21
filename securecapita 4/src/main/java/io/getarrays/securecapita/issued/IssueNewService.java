package io.getarrays.securecapita.issued;

import io.getarrays.securecapita.issued.request.IssuedRequestDto;
import io.getarrays.securecapita.issued.response.IssueResponseDto;
import io.getarrays.securecapita.purchaserequest.Page;
import io.getarrays.securecapita.purchaserequest.response.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IssueNewService {
    IssuedRequestDto saveFile(MultipartFile file, IssuedRequestDto pEntity);

    ResponseDto delete(Long id);

    List<IssueResponseDto> findAll(Page page);

    List<IssuedEntity> findAll();

}
