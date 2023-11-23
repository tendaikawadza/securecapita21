/**
 * @author kunal
 * 
 */
package io.getarrays.securecapita.purchaserequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.getarrays.securecapita.service.implementation.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Kumar.Kunal
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class PurchaseRequestNewJdbcRepository implements PurchaseRequestNewRepository {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final PurchaseRequestQueries purchaseRequestQueries;
	private final RowMapper<PurchaseRequestEntity> rowMapper;
	private final SimpleJdbcInsert insert;

	@Autowired
	private EmailService emailService;

	@Autowired
	public PurchaseRequestNewJdbcRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate,
			PurchaseRequestQueries purchaseRequestQueries) {
		this.namedParameterJdbcTemplate 	= namedParameterJdbcTemplate;
		this.rowMapper 						= new PurchaseRequestRowMapper();
		this.purchaseRequestQueries 		= purchaseRequestQueries;
		this.insert 						= new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate());
		this.insert.setTableName("purchaserequestnew");
		this.insert.usingGeneratedKeyColumns("id");
	}

	public static final String PURCHASE_REQUEST_LIST = "select * from purchaserequestnew";
	public static final String PURCHASE_REQUEST_FIND_BY_ID = "select * from purchaserequestnew where id = :id";
	public static final String INSERT_PURCHASE_REQUEST = "insert into purchaserequestnew(purchase_date , requesting_department, department_code, request_reason, item_number, "
			+ "item_description, unit_price, quantity, estimated_value, email_address, name, type, profile_image) values (:purchaseDate, :requestingDepartment, "
			+ ":departmentCode, :requestReason, :itemNumber, :itemDescription, :unitPrice, :quantity, :estimatedValue, :emailAddress, :name, :type, :profileImage)";
	public static final String UPDATE_PURCHASE_REQUEST = "update purchaserequestnew set requesting_department = :requestingDepartment, department_code = :departmentCode, quantity = :quantity where id = :id";
	public static final String DELETE_PURCHASE_REQUEST = "delete from purchaserequestnew where id = :id";

	@Override
	public PurchaseRequestEntity saveFile(@RequestParam("file") MultipartFile file, PurchaseRequestEntity pEntity) {
		
		log.info("Entering Inside Insert Statement ------ ");

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				String sql = INSERT_PURCHASE_REQUEST;

				KeyHolder keyHolder = new GeneratedKeyHolder();
				MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

				mapSqlParameterSource.addValue("purchaseDate", pEntity.getPurchaseDate());
				mapSqlParameterSource.addValue("requestingDepartment", pEntity.getRequestingDepartment());
				mapSqlParameterSource.addValue("departmentCode", pEntity.getDepartmentCode());
				mapSqlParameterSource.addValue("requestReason", pEntity.getRequestReason());
				mapSqlParameterSource.addValue("itemNumber", pEntity.getItemNumber());
				mapSqlParameterSource.addValue("itemDescription", pEntity.getItemDescription());
				mapSqlParameterSource.addValue("unitPrice", pEntity.getUnitPrice());
				mapSqlParameterSource.addValue("quantity", pEntity.getQuantity());
				mapSqlParameterSource.addValue("estimatedValue", pEntity.getEstimatedValue());
				mapSqlParameterSource.addValue("emailAddress", pEntity.getEmailAddress());

				mapSqlParameterSource.addValue("name", file.getOriginalFilename());
				mapSqlParameterSource.addValue("type", file.getContentType());
				mapSqlParameterSource.addValue("profileImage", bytes);
				namedParameterJdbcTemplate.update(sql, mapSqlParameterSource, keyHolder);
				pEntity.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

				String email = pEntity.getEmailAddress();
				String subject = "Purchase Request  Sent for you";
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
	public int save(PurchaseRequestEntity purchaseRequest) {
		/*
		 * String email = purchaseRequest.getEmailAddress(); String subject =
		 * "Purchase Request Email Verification Sent By Kumar Kunal"; String message =
		 * "Hello " + purchaseRequest.getId() + " for Product Name " +
		 * purchaseRequest.getItemDescription() + ", " +
		 * "\n A Purchase Request Email Verification Was Sent To \n" +
		 * purchaseRequest.getEmailAddress(); emailService.sendEmail(email, subject,
		 * message);
		 */
		return namedParameterJdbcTemplate.update(INSERT_PURCHASE_REQUEST,
				new BeanPropertySqlParameterSource(purchaseRequest));
	}

	@Override
	public int update(PurchaseRequestEntity purchaseRequest) {

		return namedParameterJdbcTemplate.update(UPDATE_PURCHASE_REQUEST,
				new BeanPropertySqlParameterSource(purchaseRequest));
	}

	@Override
	public Optional<PurchaseRequestEntity> findById(Long id) {
		log.info("Fetching Purchase Request Details from DB with ID = '{}'", id);
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", id);
		return namedParameterJdbcTemplate
				.query(PURCHASE_REQUEST_FIND_BY_ID, mapSqlParameterSource,
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
		log.info("Purchase Request is deleted with ID = '{}'", id);
		return namedParameterJdbcTemplate.update(DELETE_PURCHASE_REQUEST, new MapSqlParameterSource("id", id));
	}

	@Override
	public List<PurchaseRequestEntity> findAll() {
		log.info("Fetching Purchase Request All Details from DB with ID ");
		return namedParameterJdbcTemplate.query(PURCHASE_REQUEST_LIST,
				(rs, row) -> PurchaseRequestEntity.builder().id(rs.getLong("id"))

						.purchaseDate(rs.getDate("purchase_date"))
						.requestingDepartment(rs.getString("requesting_department"))
						.departmentCode(rs.getInt("department_code")).requestReason(rs.getString("request_reason"))
						.itemNumber(rs.getInt("item_number")).itemDescription(rs.getString("item_description"))
						.unitPrice(rs.getInt("unit_price")).quantity(rs.getInt("quantity"))
						.estimatedValue(rs.getInt("estimated_value")).emailAddress(rs.getString("email_address"))
						.name(rs.getString("name")).type(rs.getString("type")).profileImage(rs.getBytes("profileImage"))
						.build());
	}

	@Override
	public Stream<PurchaseRequestEntity> findAll(Page page) {
		String sql = purchaseRequestQueries.getFindAll();
		log.info("Fetching Page All Request For Pagination '{}' ", sql);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("limit", page.getLimit());
		paramMap.put("offset", page.getOffset());
		return namedParameterJdbcTemplate.queryForStream(sql, paramMap, rowMapper);
	}

}