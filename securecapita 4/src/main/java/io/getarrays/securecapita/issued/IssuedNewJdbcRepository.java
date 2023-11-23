package io.getarrays.securecapita.issued;


import io.getarrays.securecapita.purchaserequest.Page;
import io.getarrays.securecapita.purchaserequest.PurchaseRequestEntity;
import io.getarrays.securecapita.purchaserequest.PurchaseRequestQueries;
import io.getarrays.securecapita.service.implementation.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;


@Repository
@RequiredArgsConstructor
@Slf4j


public class IssuedNewJdbcRepository implements  IssuedNewRepository{
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final IssuedQueries issuedQueries;


    private final RowMapper<IssuedEntity> rowMapper;
    private final SimpleJdbcInsert insert;
    @Autowired
    private EmailService emailService;


    @Override
    public IssuedEntity saveFile(@RequestParam("file") MultipartFile file, IssuedEntity pEntity) {
        log.info("Entering Inside Insert Statement ------ ");

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String sql = INSERT_ISSUE;

                KeyHolder keyHolder = new GeneratedKeyHolder();
                MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
                mapSqlParameterSource.addValue("isuuedDate", pEntity.getIssuedate());
                mapSqlParameterSource.addValue("issuecTo", pEntity.getIssueTo());
                mapSqlParameterSource.addValue("orderNumber", pEntity.getOrderNumber());
                mapSqlParameterSource.addValue("dispatchTo", pEntity.getDispatch());
                mapSqlParameterSource.addValue("prepairedBy", pEntity.getPreparedBY());
                mapSqlParameterSource.addValue("itemDescription", pEntity.getItemDescription());
                mapSqlParameterSource.addValue("quantity", pEntity.getQuantity());
                mapSqlParameterSource.addValue("unitPrice", pEntity.getUnitPrice());
                mapSqlParameterSource.addValue("estimatedValue", pEntity.getEstimatedValue());
                mapSqlParameterSource.addValue("name", file.getOriginalFilename());
                mapSqlParameterSource.addValue("type", file.getContentType());
                mapSqlParameterSource.addValue("profileImage", bytes);
                namedParameterJdbcTemplate.update(sql, mapSqlParameterSource, keyHolder);
                pEntity.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

                String email = pEntity.getEmailAddress();
                String subject = "Issue has has been done to you";
                String message = "Hello " + pEntity.getId() + " for Product Name " + pEntity.getQuantity() + ", "
                        + "\n A Purchase Request Email Verification Was Sent To \n" + pEntity.getEmailAddress();
                emailService.sendEmail(email, subject, message);
            } catch (IOException e) {
                log.error(
                        "Exception for Create saveFile method under PurchaseRequestNewJdbcRepository class '{}' \n '{}'",
                        e, e.getMessage());
            }

            return pEntity;
        }
        return pEntity;

    }

    @Override
    public Optional<IssuedEntity> findById(Long id) {
        log.info("Fetching Purchase Request Details from DB with ID = '{}'", id);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id);
        return namedParameterJdbcTemplate
                .query(ISSUE_FIND_BY_ID, mapSqlParameterSource,
                        (rs, row) -> PurchaseRequestEntity.builder().id(rs.getLong("id"))
                                .purchaseDate(rs.getDate("purchase_date"))
                                .requestingDepartment(rs.getString("requesting_department"))
                                .departmentCode(rs.getInt("department_code"))
                                .requestReason(rs.getString("request_reason")).itemNumber(rs.getInt("item_number"))
                                .itemDescription(rs.getString("item_description")).unitPrice(rs.getInt("unit_price"))
                                .quantity(rs.getInt("quantity")).estimatedValue(rs.getInt("estimated_value"))
                                .emailAddress(rs.getString("email_address")).name(rs.getString("name"))
                                .type(rs.getString("type")).profileImage(rs.getBytes("profileImage")).build())
                .stream().findFirst();
    }


    @Override
    public int deleteById(Long id) {
        log.info("Issue is deleted with ID = '{}'", id);
        return namedParameterJdbcTemplate.update(DELETE_ISSUE, new MapSqlParameterSource("id", id));
    }

    @Override
    public List<IssuedEntity> findAll() {
        log.info("Fetching Purchase Request All Details from DB with ID ");
        return namedParameterJdbcTemplate.query(ISSUE_LIST,
                (rs, row) -> IssuedEntity.builder().build().builder().id(rs.getLong("id"))

                        .issuedate(rs.getDate("issue_date"))
                        .issueTo(rs.getString("issue_to"))
                        .orderNumber(rs.getInt("order_number"))
                        .preparedBY(rs.getString("prepared_by"))
                        .itemDescription(rs.getString("item_description"))
                        .unitPrice(rs.getInt("unit_price"))
                        .quantity(rs.getInt("quantity"))
                        .estimatedValue(rs.getInt("estimated_value"))
                        .emailAddress(rs.getString("email_address"))
                        .name(rs.getString("name"))
                        .type(rs.getString("type"))
                        .profileImage(rs.getBytes("profileImage"))
                        .build());
    }

    @Override
    public Stream<IssuedEntity> findAll(Page page) {
        String sql = issuedQueries.getFindAll();
        log.info("Fetching Page All Request For Pagination '{}' ", sql);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("limit", page.getLimit());
        paramMap.put("offset", page.getOffset());
        return namedParameterJdbcTemplate.queryForStream(sql, paramMap, rowMapper);
    }




}
