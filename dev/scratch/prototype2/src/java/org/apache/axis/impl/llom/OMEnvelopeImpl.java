package org.apache.axis.impl.llom;

import org.apache.axis.om.*;
import org.apache.axis.om.OMConstants;
import org.apache.axis.impl.llom.OMElementImpl;
import org.apache.xml.utils.QName;

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
 * User: Eran Chinthaka - Lanka Software Foundation
 * Date: Oct 29, 2004
 * Time: 3:41:59 PM
 */
public class OMEnvelopeImpl extends OMElementImpl implements OMEnvelope {

    /**
     *
     * @param builder
     */
    public OMEnvelopeImpl(OMXMLParserWrapper builder){
         super("",null,null,builder);
    }

    public OMEnvelopeImpl(String localName, OMNamespace ns, OMElement parent, OMXMLParserWrapper builder) {
        super(localName, ns, parent, builder);
    }

    /**
     * @param localName
     * @param ns
     */
    public OMEnvelopeImpl(String localName, OMNamespace ns) {
        super(localName, ns);
    }

    /**
     * Creates a new <CODE>Name</CODE> object initialized with the
     * given local name, namespace prefix, and namespace URI.
     * <p/>
     * <P>This factory method creates <CODE>Name</CODE> objects
     * for use in the SOAP/XML document.
     *
     * @param localName a <CODE>String</CODE> giving
     *                  the local name
     * @param prefix    a <CODE>String</CODE> giving
     *                  the prefix of the namespace
     * @param uri       a <CODE>String</CODE> giving the
     *                  URI of the namespace
     * @return a <CODE>OMNamespace</CODE> object initialized with the given
     *         local name, namespace prefix, and namespace URI
     * @throws org.apache.axis.om.OMException if there is a SOAP error
     */
    public OMNamespace createNamespace(String localName, String prefix, String uri) throws OMException {
        return this.createNamespace(localName, prefix, uri);
    }

    /**
     * Returns the <CODE>OMHeader</CODE> object for this <CODE>
     * OMEnvelope</CODE> object.
     * <p/>
     * <P> This OMHeader will just be a container for all the headers in the
     * <CODE>OMMessage</CODE>
     * </P>
     *
     * @return the <CODE>OMHeader</CODE> object or <CODE>
     *         null</CODE> if there is none
     * @throws org.apache.axis.om.OMException if there is a problem
     *                                        obtaining the <CODE>OMHeader</CODE> object
     */
    public OMHeader getHeader() throws OMException {
        Iterator headerIterator = this.getChildrenWithName(new QName(OMConstants.HEADER_NAMESPACEURI, OMConstants.HEADER_LOCAL_NAME));
        OMHeader omHeader = null;
        if (headerIterator.hasNext()) {
            omHeader = (OMHeader) headerIterator.next();
        }

        return omHeader;
    }

    /**
     * Returns the <CODE>OMBody</CODE> object associated with
     * this <CODE>OMEnvelope</CODE> object.
     * <p/>
     * <P> This OMBody will just be a container for all the BodyElements in the
     * <CODE>OMMessage</CODE>
     * </P>
     *
     * @return the <CODE>OMBody</CODE> object for this <CODE>
     *         OMEnvelope</CODE> object or <CODE>null</CODE> if there
     *         is none
     * @throws org.apache.axis.om.OMException if there is a problem
     *                                        obtaining the <CODE>OMBody</CODE> object
     */
    public OMBody getBody() throws OMException {
        Iterator bodyIterator = this.getChildrenWithName(new QName(OMConstants.BODY_NAMESPACE_URI, OMConstants.BODY_LOCAL_NAME));
        OMBody omBody = null;
        if (bodyIterator.hasNext()) {
            omBody = (OMBody) bodyIterator.next();
        }

        return omBody;
    }


}