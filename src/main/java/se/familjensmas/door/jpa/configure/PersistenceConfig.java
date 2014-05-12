package se.familjensmas.door.jpa.configure;

/**
 * @author jorgen.smas@entercash.com
 */
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import se.familjensmas.door.dt.User;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:hibernate.properties" })
@ComponentScan({ "com.ec.overlay.hibernate.configuration" })
public class PersistenceConfig {

	@Autowired
	private Environment env;
	private static final Logger logger = LoggerFactory.getLogger(PersistenceConfig.class);

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setNamingStrategy(new org.hibernate.cfg.ImprovedNamingStrategy());
		sessionFactory.setDataSource(restDataSource());
		sessionFactory.setPackagesToScan(User.class.getPackage().getName());
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}

	@Bean
	public DataSource restDataSource() {
		logger.debug("ENV=" + env);
		com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource dataSource = new com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource();
		dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
		dataSource.setUser(env.getRequiredProperty("jdbc.user"));
		dataSource.setPassword(env.getRequiredProperty("jdbc.pass"));
		return dataSource;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	Properties hibernateProperties() {
		Properties p = new Properties();
		p.setProperty("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
		p.setProperty("hibernate.dialect", org.hibernate.dialect.MySQL5InnoDBDialect.class.getName());
		return p;
	}
}