package br.com.caiocesar.expense.tracker.api.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import br.com.caiocesar.expense.tracker.api.Constants;
import br.com.caiocesar.expense.tracker.api.domain.User;
import br.com.caiocesar.expense.tracker.api.exceptions.AuthorizationException;
import br.com.caiocesar.expense.tracker.api.repository.UserRepository;
import br.com.caiocesar.expense.tracker.api.util.Crypto;
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

					User user = userRepository.findById(id);
					httpRequest.setAttribute(USER_CONTEXT, user);
					validateServerToken(user, claim);

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

	private void validateServerToken(User user, Claims claim) {
		String serverTokenFromJWT = claim.get("serverToken").toString();
		String actualServerToken = user.getUniqueHash();
		if(serverTokenFromJWT.equals(actualServerToken)) {
			return;
		}
		
		throw new AuthorizationException("invalid Bearer token");
	}

}
