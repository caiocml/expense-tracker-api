package br.com.caiocesar.expense.tracker.api.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import br.com.caiocesar.expense.tracker.api.Constants;
import br.com.caiocesar.expense.tracker.api.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class AuthFilter extends GenericFilterBean{

	private UserRepository userRepository;
	
	public AuthFilter(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static String USER_CONTEXT = "userContextAccess";
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		String authHeader = httpRequest.getHeader("Authorization");
		if (authHeader != null) {
			String[] headers = authHeader.split("Bearer ");
			if (headers.length > 1 && headers[1] != null) {
				String token = headers[1];
				try {
					Claims claim = Jwts.parser()
							.setSigningKey(Constants.API_SECRET_KEY)
							.parseClaimsJws(token)
							.getBody();

					Integer id = Integer.parseInt(claim.get("userId").toString());

					httpRequest.setAttribute(USER_CONTEXT, userRepository.findById(id));

				} catch (Exception e) {
					httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "invalid or expired token");
					return;
				}
			} else {
				httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "provide a valid Bearer [token]");
				return;
			}
		} else {
			httpResponse.sendError(HttpStatus.UNAUTHORIZED.value(), "must provide a Bearer token");
			return;
		}
		filterChain.doFilter(httpRequest, httpResponse);
	}

}
