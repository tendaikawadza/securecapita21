package io.getarrays.securecapita.issued;

import io.getarrays.securecapita.purchaserequest.Page;
import io.getarrays.securecapita.purchaserequest.PurchaseRequestEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface IssuedNewRepository {



    IssuedEntity saveFile(MultipartFile file, IssuedEntity pEntity);
    Optional<IssuedEntity> findById(Long id);
    int deleteById(Long id);
    List<IssuedEntity> findAll();
   // List<IssuedEntity> findAll(Page page);
    Stream<PurchaseRequestEntity> findAll(io.getarrays.securecapita.
                                                  issued.Page page);




}
