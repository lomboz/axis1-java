/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Axis" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
package org.apache.axis.components.net;

import java.util.Hashtable;

import org.apache.axis.components.logger.LogFactory;
import org.apache.axis.utils.Messages;
import org.apache.commons.logging.Log;

import com.ibm.net.ssl.SSLContext;
import com.ibm.net.ssl.TrustManager;
import com.ibm.net.ssl.X509TrustManager;

/**
 * Hook for Axis sender, allowing unsigned server certs
 */
public class IBMFakeTrustSocketFactory extends IBMJSSESocketFactory {

    /** Field log           */
    protected static Log log =
            LogFactory.getLog(IBMFakeTrustSocketFactory.class.getName());

    /**
     * Constructor FakeTrustSocketFactory
     *
     * @param attributes
     */
    public IBMFakeTrustSocketFactory(Hashtable attributes) {
        super(attributes);
    }

    /**
     * Method getContext
     *
     * @return
     *
     * @throws Exception
     */
    protected SSLContext getContext() throws Exception {

        try {
            SSLContext sc = SSLContext.getInstance("SSL");

            sc.init(null, // we don't need no stinkin KeyManager
                    new TrustManager[]{new FakeX509TrustManager()},
                    new java.security.SecureRandom());
            if (log.isDebugEnabled()) {
                log.debug(Messages.getMessage("ftsf00"));
            }
            return sc;
        } catch (Exception exc) {
            log.error(Messages.getMessage("ftsf01"), exc);
            throw new Exception(Messages.getMessage("ftsf02"));
        }
    }

    /**
     * Class FakeX509TrustManager
     */
    public static class FakeX509TrustManager implements X509TrustManager {

        /** Field log           */
        protected static Log log =
                LogFactory.getLog(FakeX509TrustManager.class.getName());

        /**
         * Method isClientTrusted
         *
         * @param chain
         *
         * @return
         */
        public boolean isClientTrusted(java.security.cert
                .X509Certificate[] chain) {

            if (log.isDebugEnabled()) {
                log.debug(Messages.getMessage("ftsf03"));
            }
            return true;
        }

        /**
         * Method isServerTrusted
         *
         * @param chain
         *
         * @return
         */
        public boolean isServerTrusted(java.security.cert
                .X509Certificate[] chain) {

            if (log.isDebugEnabled()) {
                log.debug(Messages.getMessage("ftsf04"));
            }
            return true;
        }

        /**
         * Method getAcceptedIssuers
         *
         * @return
         */
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {

            if (log.isDebugEnabled()) {
                log.debug(Messages.getMessage("ftsf05"));
            }
            return null;
        }
    }
}