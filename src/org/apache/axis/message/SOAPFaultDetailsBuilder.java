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
package org.apache.axis.message;

import org.apache.axis.Constants;
import org.apache.axis.MessageContext;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.FaultDesc;
import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Callback;
import org.apache.axis.encoding.CallbackTarget;
import org.apache.axis.encoding.DeserializerImpl;
import org.apache.axis.utils.ClassUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;

/**
 * Handle deserializing fault details.
 * 
 * @author Glen Daniels (gdaniels@macromedia.com)
 * @author Tom Jordahl (tomj@macromedia.com)
 */
public class SOAPFaultDetailsBuilder extends SOAPHandler implements Callback
{
    protected SOAPFaultBuilder builder;
    
    public SOAPFaultDetailsBuilder(SOAPFaultBuilder builder) {
        this.builder = builder;
    }

    public SOAPHandler onStartChild(String namespace,
                                    String name,
                                    String prefix,
                                    Attributes attributes,
                                    DeserializationContext context)
        throws SAXException
    {
        // Get QName of element
        QName qn = new QName(namespace, name); 
                        
        // Look for <exceptionName> element and create a class
        // with that name - this is Axis specific and is
        // replaced by the Exception map 
        if (name.equals("exceptionName")) {
            // Set up deser of exception name string
            Deserializer dser = context.getDeserializerForType(Constants.XSD_STRING);
            dser.registerValueTarget(new CallbackTarget(this, "exceptionName"));
            return (SOAPHandler)dser;
        }
                        
        // Look up this element in our faultMap
        // if we find a match, this element is the fault data
        MessageContext msgContext = context.getMessageContext();
        OperationDesc op = msgContext.getOperation();
        if (op != null) {
            FaultDesc faultDesc = op.getFaultByQName(qn);
            if (faultDesc != null) {
                // Set the class
                builder.setFaultClass(faultDesc.getClass());
                builder.setWaiting(true);
                // register callback for the data, use the xmlType from fault info
                Deserializer dser = null;
                if (attributes.getValue("href") == null) {
                    dser = context.getDeserializerForType(faultDesc.getXmlType());
                } else {
                    dser = new DeserializerImpl();
                    dser.setDefaultType(faultDesc.getXmlType());
                }
                if (dser != null) {
                    dser.registerValueTarget(new CallbackTarget(this, "faultData"));
                }
                return (SOAPHandler)dser;
            }
        }
        
        return null;
    }

    /* 
     * Defined by Callback.
     * This method gets control when the callback is invoked.
     * @param is the value to set.
     * @param hint is an Object that provide additional hint information.
     */
    public void setValue(Object value, Object hint)
    {
        if ("faultData".equals(hint)) {
            builder.setFaultData(value);
        } else if ("exceptionName".equals(hint)) {
            String faultClassName = (String) value;
            try {
                Class faultClass = ClassUtils.forName(faultClassName);
                builder.setFaultClass(faultClass);
            } catch (ClassNotFoundException e) {
                // Just create an AxisFault, no custom exception
            }
        }
        
    }
}