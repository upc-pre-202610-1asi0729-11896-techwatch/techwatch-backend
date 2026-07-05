package com.techwatch.techwatchbackend.iam.interfaces.rest.resources;

public record AuthenticatedUserResource(Long id, String email, String token) {
}
