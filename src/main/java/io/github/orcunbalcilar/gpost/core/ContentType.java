package io.github.orcunbalcilar.gpost.core;

/**
 * Core interface for request body content types.
 */
public interface ContentType {
    
    /**
     * JSON content type.
     */
    String APPLICATION_JSON = "application/json";
    
    /**
     * XML content type.
     */
    String APPLICATION_XML = "application/xml";
    
    /**
     * Plain text content type.
     */
    String TEXT_PLAIN = "text/plain";
    
    /**
     * HTML content type.
     */
    String TEXT_HTML = "text/html";
    
    /**
     * Form URL encoded content type.
     */
    String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";
    
    /**
     * Multipart form data content type.
     */
    String MULTIPART_FORM_DATA = "multipart/form-data";
    
    /**
     * SOAP XML content type.
     */
    String TEXT_XML_SOAP = "text/xml; charset=utf-8";
    
    /**
     * Get the content type string.
     * 
     * @return The content type string
     */
    String getContentType();
}