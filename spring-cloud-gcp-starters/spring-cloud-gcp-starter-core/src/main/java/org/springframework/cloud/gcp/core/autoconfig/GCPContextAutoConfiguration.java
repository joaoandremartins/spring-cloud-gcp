package org.springframework.cloud.gcp.core.autoconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gcp.core.GCPProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import com.google.auth.oauth2.GoogleCredentials;

/**
 *
 * Base starter for Google Cloud Projects. Provide defaults for {@link GoogleCredentials}.
 * Binds properties from {@link GCPProperties}
 *
 * @author Vinicius Carvalho
 */
@Configuration
@ConditionalOnClass(GoogleCredentials.class)
@EnableConfigurationProperties(GCPProperties.class)
public class GCPContextAutoConfiguration {


	@Autowired
	private GCPProperties gcpProperties;

	@Bean
	public GoogleCredentials googleCredentials(ResourceLoader resourceLoader) throws Exception {
		if (!StringUtils.isEmpty(this.gcpProperties.getJsonKeyFile())) {
			return GoogleCredentials.fromStream(
					resourceLoader.getResource(this.gcpProperties.getJsonKeyFile())
							.getInputStream());
		}
		else {
			return GoogleCredentials.getApplicationDefault();
		}
	}

}
