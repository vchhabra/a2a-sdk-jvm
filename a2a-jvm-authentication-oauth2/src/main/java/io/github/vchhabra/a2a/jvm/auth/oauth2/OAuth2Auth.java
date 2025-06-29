package io.github.vchhabra.a2a.jvm.auth.oauth2;

import io.github.vchhabra.a2a.jvm.core.model.auth.AuthScheme;
import io.github.vchhabra.a2a.jvm.core.model.enums.AuthType;

import java.net.URI;
import java.util.List;

/**
 * Represents an OAuth 2.0 authentication scheme used to authorize and secure
 * API interactions. It encapsulates the client identifier, authorization
 * endpoint, token endpoint, and the set of scopes required for the
 * authentication process.
 *
 * This class is an implementation of the AuthScheme interface specific to
 * the OAuth 2.0 protocol.
 *
 * Components:
 * - clientId: The client identifier used in the OAuth 2.0 authentication process.
 * - authorizationUrl: The URL of the authorization endpoint where the client
 *   interacts to obtain an authorization code.
 * - tokenUrl: The URL of the token endpoint used to exchange an authorization
 *   code for an access token.
 * - scopes: The permissions or access levels requested for the authenticated session.
 *
 * The type of this authentication scheme is fixed as OAUTH2.
 */
public record OAuth2Auth(
        String clientId,
        URI authorizationUrl,
        URI tokenUrl,
        List<String> scopes
) implements AuthScheme {
    @Override
    public AuthType type() {
        return AuthType.OAUTH2;
    }
}
