package io.github.vchhabra.a2a.jvm.core;

import java.net.URI;
import java.util.List;

/**
 * Describes the authentication mechanism required by the agent.
 *
 * @param type The type of authentication (e.g., "oauth2", "bearer").
 * @param clientId The client ID for OAuth2 flows.
 * @param authorizationUrl The URL to use for obtaining authorization.
 * @param tokenUrl The URL to use for obtaining a token.
 * @param scopes A list of required permission scopes.
 */
public record Auth(
        String type,
        String clientId,
        URI authorizationUrl,
        URI tokenUrl,
        List<String> scopes
) {}
