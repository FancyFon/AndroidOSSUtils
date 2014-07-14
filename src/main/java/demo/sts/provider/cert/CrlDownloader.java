/*
 * Copyright © 2014 FancyFon Software Ltd.
 * All rights reserved.
 */
package demo.sts.provider.cert;

import exceptions.CertificateVerificationException;

import java.io.File;
import java.io.IOException;
import java.security.cert.CRLException;
import java.security.cert.CertificateException;

/**
 * @author Marcin Paszylk <marcin.paszylk@fancyfon.com>
 */
public interface CrlDownloader {

    File downloadCRL(String path) throws IOException,
            CertificateException, CRLException,
            CertificateVerificationException;
}