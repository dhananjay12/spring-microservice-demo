package com.mynotes.microservice.cloudgateway;

import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.util.Assert;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


public class NegatedServerWebExchangeMatcher implements ServerWebExchangeMatcher {
	private final ServerWebExchangeMatcher matcher;

	public NegatedServerWebExchangeMatcher(ServerWebExchangeMatcher matcher) {
		Assert.notNull(matcher, "matcher cannot be null");
		this.matcher = matcher;
	}

	/* (non-Javadoc)s
	 * @see org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher#matches(org.springframework.web.server.ServerWebExchange)
	 */
	@Override
	public Mono<MatchResult> matches(ServerWebExchange exchange) {
		return matcher.matches(exchange)
			.flatMap(m -> m.isMatch() ? MatchResult.notMatch() : MatchResult.match());
	}

	@Override
	public String toString() {
		return "NegatedServerWebExchangeMatcher{" +
				"matcher=" + matcher +
				'}';
	}
}

