/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package demo.sts.provider.cert;

import exceptions.CertificateVerificationException;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x509.*;

import java.io.*;
import java.security.cert.*;
import java.util.ArrayList;
import java.util.List;

public class CrlVerifier {

    /**
     * Extracts the CRL distribution points from the certificate (if available)
     * and checks the certificate revocation status against the CRLs coming from
     * the distribution points. Supports HTTP, HTTPS, FTP and LDAP based URLs.
     *
     * @throws CertificateVerificationException if the certificate is revoked
     */

    private CrlDownloader crlDownloader;

    public CrlVerifier(CrlDownloader crlDownloader) {
        this.crlDownloader = crlDownloader;
    }

    public void verifyCertificateCRLs(X509Certificate cert) throws CertificateVerificationException {
        File file = null;
        FileInputStream fileInputStream = null;
        try {
            List<String> crlDistPoints = getCrlDistributionPoints(cert);
            for (String crlDP : crlDistPoints) {
                try {
                    file = crlDownloader.downloadCRL(crlDP);
                    fileInputStream = new FileInputStream(file);

                    X509CRL crl = getCrlFromStream(fileInputStream);
                    if (crl.isRevoked(cert)) {
                        throw new CertificateVerificationException(
                                "The certificate is revoked by CRL: " + crlDP);
                    }
                }finally {
                    IOUtils.closeQuietly(fileInputStream);
                    deleteFile(file);
                }
            }
        } catch(Exception e){
            throw new CertificateVerificationException(
                    "Can not verify CRL for certificate: "
                            + cert.getSubjectX500Principal());
        }
    }

    private X509CRL getCrlFromStream(InputStream is) throws CertificateException, CRLException {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            return (X509CRL)certificateFactory.generateCRL(is);
    }
    /**
     * Extracts all CRL distribution point URLs from the
     * "CRL Distribution Point" extension in a X.509 certificate. If CRL
     * distribution point extension is unavailable, returns an empty list.
     */
    private static List<String> getCrlDistributionPoints(X509Certificate cert) throws CertificateParsingException, IOException {
        byte[] crldpExt = cert
                .getExtensionValue(X509Extensions.CRLDistributionPoints.getId());
        if (crldpExt == null) {
            return new ArrayList<String>();
        }
        ASN1InputStream oAsnInStream = new ASN1InputStream(
                new ByteArrayInputStream(crldpExt));
        DERObject derObjCrlDP = oAsnInStream.readObject();
        DEROctetString dosCrlDP = (DEROctetString) derObjCrlDP;
        byte[] crldpExtOctets = dosCrlDP.getOctets();
        ASN1InputStream oAsnInStream2 = new ASN1InputStream(
                new ByteArrayInputStream(crldpExtOctets));
        DERObject derObj2 = oAsnInStream2.readObject();
        CRLDistPoint distPoint = CRLDistPoint.getInstance(derObj2);
        List<String> crlUrls = new ArrayList<String>();
        for (DistributionPoint dp : distPoint.getDistributionPoints()) {
            DistributionPointName dpn = dp.getDistributionPoint();
            // Look for URIs in fullName
            if (dpn != null
                    && dpn.getType() == DistributionPointName.FULL_NAME) {
                GeneralName[] genNames = GeneralNames.getInstance(
                        dpn.getName()).getNames();
                // Look for an URI
                for (int j = 0; j < genNames.length; j++) {
                    if (genNames[j].getTagNo() == GeneralName.uniformResourceIdentifier) {
                        String url = DERIA5String.getInstance(
                                genNames[j].getName()).getString();
                        crlUrls.add(url);
                    }
                }
            }
        }
        return crlUrls;
    }

    private void deleteFile(File f){
        if(f.exists()){
            f.delete();
        }
    }
}
