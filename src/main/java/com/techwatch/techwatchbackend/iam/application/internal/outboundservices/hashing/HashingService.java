package com.techwatch.techwatchbackend.iam.application.internal.outboundservices.hashing;

/**
 * Outbound port for password hashing operations required by the IAM application layer.
 */
public interface HashingService {
    String encode(CharSequence rawPassword);
    boolean matches(CharSequence rawPassword, String encodedPassword);
}
