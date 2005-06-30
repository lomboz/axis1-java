/*
 * Copyright 2001-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.axis.wsdl.codegen.extension;

import java.util.Map;

import org.apache.axis.wsdl.codegen.CodeGenConfiguration;
import org.apache.axis.wsdl.codegen.CommandLineOption;
import org.apache.axis.wsdl.codegen.CommandLineOptionConstants;
import org.apache.axis.wsdl.util.URLProcessor;
import org.apache.wsdl.WSDLBinding;

/**
 * @author chathura@opensource.lk
 *  
 */
public class PackageFinder extends AbstractCodeGenerationExtension implements
		CodeGenExtension {

	public void init(CodeGenConfiguration configuration) {
		this.configuration = configuration;

	}

	public void engage() {
		Map allOptions = this.configuration.getParser().getAllOptions();
        CommandLineOption packageOption = (CommandLineOption)(allOptions.get(CommandLineOptionConstants.PACKAGE_OPTION));
        String packageName = packageOption==null?null:packageOption.getOptionValue();

		if (packageName == null) {
			WSDLBinding binding = configuration.getWom().getBinding(AxisBindingBuilder.AXIS_BINDING_QNAME);
			String temp = binding.getBoundInterface().getName().getNamespaceURI();
			packageName = URLProcessor.getNameSpaceFromURL(temp);
		}	
		
		if(null == packageName || "".equals(packageName))
			packageName = URLProcessor.DEFAULT_PACKAGE;
		
		this.configuration.setPackageName(packageName.toLowerCase());

	}

	

}