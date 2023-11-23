package io.getarrays.securecapita.issued;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IssuedQueries {

    @Value("${purchase.query.find.all}")
    private String findAll;

    public String getFindAll() {
        return findAll;
    }

    public void setFindAll(String findAll) {
        this.findAll = findAll;
    }


}
