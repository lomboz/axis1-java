package org.apache.axis.impl.llom;

import org.apache.axis.om.*;

import javax.xml.namespace.QName;
import java.util.Iterator;

/**
 * Copyright 2001-2004 The Apache Software Foundation.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 */
public class SOAPEnvelopeImpl extends OMElementImpl implements SOAPEnvelope, OMConstants {

    /**
     * @param builder
     */
    public SOAPEnvelopeImpl(OMXMLParserWrapper builder) {
        super(SOAPENVELOPE_LOCAL_NAME, null, null, builder);
    }

    public SOAPEnvelopeImpl(OMNamespace ns, OMXMLParserWrapper builder) {
        super(SOAPENVELOPE_LOCAL_NAME, ns, null, builder);
    }

    /**
     * @param ns
     */
    public SOAPEnvelopeImpl(OMNamespace ns) {
        super(SOAPENVELOPE_LOCAL_NAME, ns);
    }

        /**
     * Returns the <CODE>SOAPHeader</CODE> object for this <CODE>
     * SOAPEnvelope</CODE> object.
     * <p/>
     * <P> This SOAPHeader will just be a container for all the headers in the
     * <CODE>OMMessage</CODE>
     * </P>
     *
     * @return the <CODE>SOAPHeader</CODE> object or <CODE>
     *         null</CODE> if there is none
     * @throws org.apache.axis.om.OMException if there is a problem
     *                                        obtaining the <CODE>SOAPHeader</CODE> object
     */
    public SOAPHeader getHeader() throws OMException {
        Iterator headerIterator = this.getChildrenWithName(new QName(OMConstants.HEADER_NAMESPACEURI, OMConstants.HEADER_LOCAL_NAME));
        SOAPHeader soapHeader = null;
        if (headerIterator.hasNext()) {
            soapHeader = (SOAPHeader) headerIterator.next();
        }

        return soapHeader;
    }

    /**
     * Returns the <CODE>SOAPBody</CODE> object associated with
     * this <CODE>SOAPEnvelope</CODE> object.
     * <p/>
     * <P> This SOAPBody will just be a container for all the BodyElements in the
     * <CODE>OMMessage</CODE>
     * </P>
     *
     * @return the <CODE>SOAPBody</CODE> object for this <CODE>
     *         SOAPEnvelope</CODE> object or <CODE>null</CODE> if there
     *         is none
     * @throws org.apache.axis.om.OMException if there is a problem
     *                                        obtaining the <CODE>SOAPBody</CODE> object
     */
    public SOAPBody getBody() throws OMException {
        Iterator bodyIterator = this.getChildrenWithName(new QName(OMConstants.BODY_NAMESPACE_URI, OMConstants.BODY_LOCAL_NAME));
        SOAPBody soapBody = null;
        if (bodyIterator.hasNext()) {
            soapBody = (SOAPBody) bodyIterator.next();
        }

        return soapBody;
    }


}
