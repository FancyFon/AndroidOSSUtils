/*
 * Copyright © 2014 FancyFon Software Ltd.
 * All rights reserved.
 */
package demo.sts.provider.cert;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CRLException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;

/**
 * @author Marcin Paszylk <marcin.paszylk@fancyfon.com>
 */
public interface CrlIssuerVerifier {
    public void verify(X509CRL crl, X509Certificate certificate) throws NoSuchProviderException,
            InvalidKeyException, NoSuchAlgorithmException, CRLException, SignatureException;
}
