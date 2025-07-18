package io.github.orcunbalcilar.gpost.core;

/**
 * Core interface for context access.
 * Provides access to the test case execution context.
 */
public interface ContextAccess {
    
    /**
     * Get the test case run context.
     * 
     * @return The test case run context
     */
    TestCaseRunContext getContext();
}