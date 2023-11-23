package io.getarrays.securecapita.issued;

import io.getarrays.securecapita.issued.request.IssuedRequestDto;
import io.getarrays.securecapita.purchaserequest.PurchaseRequestNewService;
import io.getarrays.securecapita.purchaserequest.request.PurchaseRequestDto;
import io.getarrays.securecapita.purchaserequest.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Issued") // giving a different name as purchase request name might be existing, please change to specific
@Slf4j
public class IssuedNewController {

    private final IssueNewService   issueNewService;


    @PostMapping("/upload")
    public ResponseEntity<IssuedRequestDto> uploadFiles(@RequestParam("file") MultipartFile file,
                                                                   @RequestBody IssuedRequestDto issuedRequestDto) {
        log.info("POST - /purchase request for Multipart Upload - request -> {}", issuedRequestDto);
        return ResponseEntity.ok(issueNewService.saveFile(file, issuedRequestDto));
    }




    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Long id) {
        log.info("DELETE - /purchase request/{} -> request", id);
        ResponseDto deleteResponse =  issueNewService.delete(id);
        log.info("DELETE - /purchase request -> response none");
        return deleteResponse;
    }



}
