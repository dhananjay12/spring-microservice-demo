package com.mynotes.microservice.common.jwtresoureserver;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.stereotype.Component;

@Component
public class JwtConverter extends DefaultAccessTokenConverter {
	
	@Value("${security.jwt.authority.key}")
	private String AUTHORITY_KEY;

	@Override
	public OAuth2Authentication extractAuthentication(Map<String, ?> claims) {

		/*for (Entry<String, ?> entry : claims.entrySet()) {
			System.out.println(entry.getKey() + "/" + entry.getValue());
		}*/
		String[] authorities= {};
		Object authorityObj=claims.get(AUTHORITY_KEY);
		if(authorityObj!=null) {
			authorities = ((Collection<String>) authorityObj).toArray(new String[0]);
		}
		
		final Collection<GrantedAuthority> grantedAuthority = AuthorityUtils.createAuthorityList(authorities);

		OAuth2Authentication authentication = super.extractAuthentication(claims);
		authentication.setDetails(claims);

		final OAuth2Request originalOAuth2Request = authentication.getOAuth2Request();
		final OAuth2Request request = new OAuth2Request(originalOAuth2Request.getRequestParameters(),
				originalOAuth2Request.getClientId(), grantedAuthority, originalOAuth2Request.isApproved(),
				originalOAuth2Request.getScope(), originalOAuth2Request.getResourceIds(),
				originalOAuth2Request.getRedirectUri(), originalOAuth2Request.getResponseTypes(),
				originalOAuth2Request.getExtensions());

		return new OAuth2Authentication(request, authentication.getUserAuthentication());
	}

}