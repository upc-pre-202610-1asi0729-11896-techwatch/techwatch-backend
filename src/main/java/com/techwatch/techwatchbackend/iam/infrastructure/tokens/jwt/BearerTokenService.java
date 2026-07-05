package com.techwatch.techwatchbackend.iam.infrastructure.tokens.jwt;

import com.techwatch.techwatchbackend.iam.application.internal.outboundservices.tokens.TokenService;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Marker interface for the JWT token service.
 */
public interface BearerTokenService extends TokenService {
    String getBearerTokenFrom(HttpServletRequest request);
}
