/*******************************************************************************
 * Copyright (c) 2016 QNX Software Systems and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.cdt.internal.qt.core.provider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.eclipse.cdt.codan.core.cxx.Activator;
import org.eclipse.cdt.internal.qt.core.QtInstall;
import org.eclipse.cdt.qt.core.IQtInstall;
import org.eclipse.cdt.qt.core.IQtInstallProvider;
import org.eclipse.cdt.utils.WindowsRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * Qt Install provider that attempts to find the Qt package as installed using Qt's own installer.
 */
public class QtInstallProvider implements IQtInstallProvider {

	@Override
	public Collection<IQtInstall> getInstalls() {
		Path root = getQtRoot();
		Path qmake = Paths.get(Platform.getOS().equals(Platform.OS_WIN32) ? "bin/qmake.exe" : "bin/qmake"); //$NON-NLS-1$ //$NON-NLS-2$
		if (root != null) {
			try {
				return Files.walk(root, 2).filter((path) -> Files.exists(path.resolve(qmake)))
						.map((path) -> new QtInstall(path.resolve(qmake))).collect(Collectors.toList());
			} catch (IOException e) {
				Activator.log(e);
			}
		}
		return Collections.emptyList();
	}

	private Path getQtRoot() {
		if (Platform.getOS().equals(Platform.OS_WIN32)) {
			WindowsRegistry registry = WindowsRegistry.getRegistry();
			String uninstallKey = "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall"; //$NON-NLS-1$
			String subkey;
			for (int i = 0; (subkey = registry.getCurrentUserKeyName(uninstallKey, i)) != null; i++) {
				String compKey = uninstallKey + '\\' + subkey;
				String displayName = registry.getCurrentUserValue(compKey, "DisplayName"); //$NON-NLS-1$
				// On Windows, look for MSYS2, MinGW 64/32 locations
				if ("Qt".equals(displayName)) { //$NON-NLS-1$
					String installLocation = registry.getCurrentUserValue(compKey, "InstallLocation"); //$NON-NLS-1$
					return Paths.get(installLocation);
				}
			}
		} else {
			Path qtDir = Paths.get(System.getProperty("user.home"), "Qt"); //$NON-NLS-1$ //$NON-NLS-2$
			if (Files.exists(qtDir)) {
				return qtDir;
			}
		}
		return null;
	}

	//   gcc is in C:\Qt\Tools\mingw492_32\bin
}
