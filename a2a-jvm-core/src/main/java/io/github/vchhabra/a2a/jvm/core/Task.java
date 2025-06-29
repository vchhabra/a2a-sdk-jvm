package io.github.vchhabra.a2a.jvm.core;

/**
 * Represents the state and result of a long-running, asynchronous task.
 *
 * @param taskId A unique identifier for the task.
 * @param state The current state of the task (e.g., "submitted", "working", "completed", "failed").
 * @param result The result of the task. Only present if the state is "completed".
 * @param error An error object. Only present if the state is "failed".
 */
public record Task(
        String taskId,
        String state,
        Object result,
        Object error // Using Object to match JSON-RPC error structure
) {}
