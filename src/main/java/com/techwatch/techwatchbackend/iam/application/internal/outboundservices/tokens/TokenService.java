package com.techwatch.techwatchbackend.iam.application.internal.outboundservices.tokens;

/**
 * Outbound port for bearer token issuance and validation used by IAM commands and queries.
 */
public interface TokenService {
    String generateToken(String email);
    String getEmailFromToken(String token);
    boolean validateToken(String token);
}
