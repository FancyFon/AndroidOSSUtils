/*
 * Copyright © 2014 FancyFon Software Ltd.
 * All rights reserved.
 */
package exceptions;

/**
 * @author Marcin Paszylk <marcin.paszylk@fancyfon.com>
 */
public class CertificateVerificationException extends Exception {

    public CertificateVerificationException() {
    }

    public CertificateVerificationException(String message) {
        super(message);
    }

    public CertificateVerificationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CertificateVerificationException(Throwable cause) {
        super(cause);
    }
}
