/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.errorparsers.xlc.tests;

import junit.framework.TestCase;

import org.eclipse.cdt.core.IMarkerGenerator;

public class TestCompatibility extends TestCase {
	String err_msg;
	/**
	 * This function tests parseLine function of the
	 * XlcErrorParser class. Error message generated by
	 * xlC compiler without message number or quotes around file name
	 * which was OK in older version.
	 */
	public void testparseLine()
	{
		XlcErrorParserTester aix = new XlcErrorParserTester();
		aix.parseLine(err_msg);
		assertEquals("temp1.c", aix.getFileName());
		assertEquals(5, aix.getLineNumber());
		assertEquals(IMarkerGenerator.SEVERITY_INFO, aix.getSeverity());
		assertEquals(" Compatibility test",aix.getMessage());
	}
	public TestCompatibility( String name)
	{
		super(name);
		err_msg = "temp1.c, line 5.1: (I) Compatibility test";
	}
}
