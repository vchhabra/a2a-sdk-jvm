package io.github.vchhabra.a2a.jvm.core;

import java.net.URI;

/**
 * Describes the API endpoint for task management.
 *
 * @param type The type of API, e.g., "json_rpc_http".
 * @param url The URL endpoint for creating and managing tasks.
 */
public record Api(String type, URI url) {}
