/**********************************************************************
 * Copyright (c) 2005 Symbian Ltd and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors: 
 * Symbian Ltd - Initial API and implementation
 **********************************************************************/

package org.eclipse.cdt.managedbuilder.core;


/**
 * This class implements the default managed option value handler for MBS.
 * It is also be intended to be used as a base class for other value handlers.
 */
public class ManagedOptionValueHandler implements
		IManagedOptionValueHandler {

	/*
	 *  E N A B L E   U S E   A S   B A S E   C L A S S   A N D
	 *  D E F A U L T   I M P L E M E N T A T I O N
	 */
	
	private static ManagedOptionValueHandler mbsValueHandler;
	
	protected ManagedOptionValueHandler() {		
		mbsValueHandler = null;
	}
	
	public static ManagedOptionValueHandler getManagedOptionValueHandler() {
		if( mbsValueHandler == null ) {
			mbsValueHandler = new ManagedOptionValueHandler();
		}
		return mbsValueHandler;
	}
	
	/*
	 *  D E F A U L T   I M P L E M E N T A T I O N S   O F   I N T E R F A C E
	 */
	
	/* (non-Javadoc)
	 * @see org.eclipse.cdt.managedbuilder.core.IManagedOptionValueHandler#handleValue(IConfiguration,IToolChain,IOption,String,int)
	 */
	public boolean handleValue(IBuildObject configuration, 
                   IHoldsOptions holder, 
                   IOption option,
                   String extraArgument, int event)
	{
	/*	
		// The following is for debug purposes and thus normally commented out		
		String configLabel = "???"; //$NON-NLS-1$
		String holderLabel = "???"; //$NON-NLS-1$
		String eventLabel  = "???"; //$NON-NLS-1$
		
		if (configuration instanceof IConfiguration) {
			configLabel = "IConfiguration"; //$NON-NLS-1$
		} else if (configuration instanceof IResourceConfiguration) {
			configLabel = "IResourceConfiguration"; //$NON-NLS-1$
		}
		
		if (holder instanceof IToolChain) {
			holderLabel = "IToolChain"; //$NON-NLS-1$
		} else if (holder instanceof ITool) {
			holderLabel = "ITool"; //$NON-NLS-1$
		}
		
		switch (event) {
		case EVENT_OPEN:       eventLabel = "EVENT_OPEN"; break;       //$NON-NLS-1$
		case EVENT_APPLY:      eventLabel = "EVENT_APPLY"; break;      //$NON-NLS-1$
		case EVENT_SETDEFAULT: eventLabel = "EVENT_SETDEFAULT"; break; //$NON-NLS-1$
		case EVENT_CLOSE:      eventLabel = "EVENT_CLOSE"; break;      //$NON-NLS-1$
		}
		
		// Print the event
		System.out.println(eventLabel + "(" +              //$NON-NLS-1$
				           configLabel + " = " +           //$NON-NLS-1$
				           configuration.getId() + ", " +  //$NON-NLS-1$
				           holderLabel + " = " +           //$NON-NLS-1$
						   holder.getId() + ", " +         //$NON-NLS-1$
						   "IOption = " +                  //$NON-NLS-1$
						   option.getId() + ", " +         //$NON-NLS-1$
						   "String = " +                   //$NON-NLS-1$
						   extraArgument + ")");           //$NON-NLS-1$
	*/	
		// The event was not handled, thus return false
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.managedbuilder.core.IManagedOptionValueHandler#isDefaultValue(IConfiguration,IToolChain,IOption,String)
	 */
	public boolean isDefaultValue(IBuildObject configuration, 
                      IHoldsOptions holder, 
                      IOption option, String extraArgument) {
		// Implement default behavior
	   if (option.getDefaultValue() == option.getValue()) {
		   return true;
	   } else {
		   return false;
	   }
	}

	/* (non-Javadoc)
	 * @see org.eclipse.cdt.managedbuilder.core.IManagedOptionValueHandler#isEnumValueAppropriate(IConfiguration,IToolChain,IOption,String,String)
	 */
	public boolean isEnumValueAppropriate(IBuildObject configuration, 
                              IHoldsOptions holder, 
                              IOption option,
                              String extraArgument, String enumValue)
	{
		// By default return true for all the enum values.
		return true;
	}
}
