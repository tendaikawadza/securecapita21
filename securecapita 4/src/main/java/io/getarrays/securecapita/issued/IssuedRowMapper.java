package io.getarrays.securecapita.issued;

import io.getarrays.securecapita.purchaserequest.PurchaseRequestEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;



@Slf4j
public class IssuedRowMapper implements RowMapper<IssuedEntity> {


    @Override
    public IssuedEntity mapRow(ResultSet resultSet, int rowNum) throws SQLException {

        log.info("Entering Row Mapper - /purchase request/{} -> request");

        Long id = resultSet.getLong("id");

        Date purchaseDate = resultSet.getDate("issued_date");
        String requestingDepartment = resultSet.getString("issued_to");
        int departmentCode = resultSet.getInt("department_code");
        String requestReason = resultSet.getString("request_reason");
        int itemNumber = resultSet.getInt("item_number");
        String itemDescription = resultSet.getString("item_description");
        int unitPrice = resultSet.getInt("unit_price");
        int quantity = resultSet.getInt("quantity");
        int estimatedValue = resultSet.getInt("estimated_value");
        String emailAddress = resultSet.getString("email_address");
        String name = resultSet.getString("name");
        String type = resultSet.getString("type");
        byte[] profileImage = resultSet.getBytes("profile_image");



        return IssuedEntity.builder()
                .id(id)
                .issuedate(issuedate)
                .issueTo(i)

                .build();
    }

}
