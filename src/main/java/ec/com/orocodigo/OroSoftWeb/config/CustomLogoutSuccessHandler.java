package ec.com.orocodigo.OroSoftWeb.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String refererUrl = request.getHeader("Referer");
		if (refererUrl.contains("login.jsf")) {
			String URL = request.getContextPath() + "/views/publicas/accesoDenegado.jsf";
			response.setStatus(HttpStatus.OK.value());
			response.sendRedirect(URL);
		}
		super.onLogoutSuccess(request, response, authentication);
	}
}
