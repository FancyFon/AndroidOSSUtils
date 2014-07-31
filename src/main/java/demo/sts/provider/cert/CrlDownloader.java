/*
 * Copyright © 2014 FancyFon Software Ltd.
 * All rights reserved.
 */
package demo.sts.provider.cert;

import java.io.File;
import java.net.URL;
import java.security.cert.CRLException;

/**
 * @author Marcin Paszylk <marcin.paszylk@fancyfon.com>
 */
public interface CrlDownloader {

    File downloadCRL(URL url) throws CRLException;
}
