/**
 * kunal
 * securecapita 4
 * io.getarrays.securecapita.purchaserequest
 */
package io.getarrays.securecapita.purchaserequest;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Kumar.Kunal
 */
@Configuration
public class MapperConfig {

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}


}
