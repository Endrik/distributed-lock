package al.jug.demo.configuration;

import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
class TomcatJndiConfiguration {

	private static final String JDBC_DRIVER = "oracle.jdbc.OracleDriver";

	private static final String JDBC_FACTORY = "org.apache.tomcat.jdbc.pool.DataSourceFactory";

	@Value("${DB_URL:''}")
	private String url;

	@Value("${spring.datasource.jndi-name:''}")
	private String jndi;

	@Value("${DB_USERNAME:''}")
	private String username;

	@Value("${DB_PASSWORD:''}")
	private String password;

	@Bean
	public EmbeddedServletContainerFactory tomcatFactory() {
		return new TomcatEmbeddedServletContainerFactory() {

			@Override
			protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
				tomcat.enableNaming();
				return super.getTomcatEmbeddedServletContainer(tomcat);
			}

			@Override
			protected void postProcessContext(Context context) {

				ContextResource resource = new ContextResource();
				resource.setName(jndi);
				resource.setType(DataSource.class.getName());
				resource.setProperty("driverClassName", JDBC_DRIVER);
				resource.setProperty("url", url);
				resource.setProperty("username", username);
				resource.setProperty("password", password);
				resource.setProperty("factory", JDBC_FACTORY);
				context.getNamingResources().addResource(resource);
			}
		};
	}

}
