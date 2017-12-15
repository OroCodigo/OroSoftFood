package ec.com.orocodigo.OroSoftWeb.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@ComponentScan("ec.com.orocodigo")
@PropertySource("classpath:database.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/javax.faces.resource/**", "/resources/**", "/views/publicas/**").permitAll()

				.antMatchers("/views/privadas/**").access("isAuthenticated()")

				.and().formLogin().loginPage("/views/publicas/login.jsf")
				.defaultSuccessUrl("/views/privadas/dashboard.jsf")

				.and().logout().logoutUrl("/views/publicas/logout.jsf")
				.logoutSuccessHandler(new CustomLogoutSuccessHandler()).invalidateHttpSession(true)
				.deleteCookies("JSESSIONID", "SESSION")

				.and().exceptionHandling().accessDeniedPage("/views/publicas/accesoDenegado.jsf")

				.and().sessionManagement().maximumSessions(1).expiredUrl("/views/publicas/login.jsf");
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(this.dataSource()).passwordEncoder(new ShaPasswordEncoder(256))
				.usersByUsernameQuery(getUserQuery()).authoritiesByUsernameQuery(getAuthoritiesQuery());
	}

	@Bean
	public DataSource dataSource() {
		String bd = "postgresql";
		String driver = "org.postgresql.Driver";

		String nombreServidor = env.getProperty("jdbc.server");
		String puertoServidor = env.getProperty("jdbc.port");
		String nombreBaseDatos = env.getProperty("jdbc.database");
		String username = env.getProperty("jdbc.user");
		String password = env.getProperty("jdbc.password");

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driver);
		dataSource.setUrl("jdbc:" + bd + "://" + nombreServidor + ":" + puertoServidor + "/" + nombreBaseDatos);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		System.out.println("OroSoftWeb - " + dataSource.getUrl());
		return dataSource;
	}

	private String getAuthoritiesQuery() {
		return "select distinct trim(u.usr_nick), trim(g.gru_nombre) " + "from sistemaweb.sis_usuario as u "
				+ "inner join sistemaweb.sis_usuario_detalle ud on (ud.usr_codigo=u.usr_codigo) "
				+ "inner join sistemaweb.sis_grupo g on (g.gru_empresa=ud.gru_empresa and g.gru_codigo=ud.gru_codigo) "
				+ "where u.usr_nick=?";
	}

	private String getUserQuery() {
		return "select trim(u.usr_nick), trim(u.usr_password), u.usr_activo from sistemaweb.sis_usuario u where u.usr_nick=?";
	}
}
