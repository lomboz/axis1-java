/*
 * The Apache Software License, Version 1.1
 * 
 * 
 * Copyright (c) 2001-2003 The Apache Software Foundation. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met: 1.
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. 2. Redistributions in
 * binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution. 3. The end-user documentation
 * included with the redistribution, if any, must include the following
 * acknowledgment: "This product includes software developed by the Apache
 * Software Foundation (http://www.apache.org/)." Alternately, this
 * acknowledgment may appear in the software itself, if and wherever such
 * third-party acknowledgments normally appear. 4. The names "Axis" and "Apache
 * Software Foundation" must not be used to endorse or promote products derived
 * from this software without prior written permission. For written permission,
 * please contact apache@apache.org. 5. Products derived from this software may
 * not be called "Apache", nor may "Apache" appear in their name, without prior
 * written permission of the Apache Software Foundation.
 * 
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * APACHE SOFTWARE FOUNDATION OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ====================================================================
 * 
 * This software consists of voluntary contributions made by many individuals
 * on behalf of the Apache Software Foundation. For more information on the
 * Apache Software Foundation, please see <http://www.apache.org/> .
 */

package org.apache.axis.message;

import javax.xml.soap.Text;

import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;

/**
 * Most of methods are inherited from TEXT, defined for its Interface Marker
 * only
 * 
 * @author Heejune Ahn (cityboy@tmax.co.kr)
 */

public class CommentImpl
    extends org.apache.axis.message.Text
    implements Text, Comment {

    public CommentImpl(String text) {
        super(text);
    }
    
    public boolean isComment() {
        return true;
    }

    public org.w3c.dom.Text splitText(int offset) throws DOMException {
        int length = textRep.getLength();
        // take the first part, and save the second part for new Text
        // length check and exception will be thrown here, no need to
        // duplicated check
        String tailData = textRep.substringData(offset, length);
        textRep.deleteData(offset, length);

        // insert the first part again as a new node
        Text tailText = new CommentImpl(tailData);
        org.w3c.dom.Node myParent = (org.w3c.dom.Node) getParentNode();
        if (myParent != null) {
            org.w3c.dom.NodeList brothers =
                (org.w3c.dom.NodeList) myParent.getChildNodes();
            for (int i = 0; i < brothers.getLength(); i++) {
                if (brothers.item(i).equals(this)) {
                    myParent.insertBefore(tailText, this);
                    return tailText;
                }
            }
        }
        return tailText;
    }

}