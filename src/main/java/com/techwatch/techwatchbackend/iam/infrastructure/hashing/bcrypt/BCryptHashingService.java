package com.techwatch.techwatchbackend.iam.infrastructure.hashing.bcrypt;

import com.techwatch.techwatchbackend.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Marker interface for the BCrypt hashing service, bridging the outbound {@link HashingService}
 * port with Spring Security's {@link PasswordEncoder}.
 */
public interface BCryptHashingService extends HashingService, PasswordEncoder {
}
