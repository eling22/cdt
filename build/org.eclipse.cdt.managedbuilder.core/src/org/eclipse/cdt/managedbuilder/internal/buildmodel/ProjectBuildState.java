/*******************************************************************************
 * Copyright (c) 2007 Intel Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Intel Corporation - Initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.managedbuilder.internal.buildmodel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.eclipse.cdt.core.settings.model.util.CDataUtil;
import org.eclipse.cdt.managedbuilder.core.ManagedBuilderCorePlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;

public class ProjectBuildState implements IProjectBuildState {
	private Properties fCfgIdToFileNameProps;
	private Map fCfgIdToStateMap = new HashMap();
	private IProject fProject; 
	private boolean fIsMapInfoDirty;
	
	public ProjectBuildState(IProject project){
		fProject = project;
	}
	
	void setProject(IProject project){
		fProject = project;
		for(Iterator iter = fCfgIdToStateMap.values().iterator(); iter.hasNext();){
			ConfigurationBuildState cbs = (ConfigurationBuildState)iter.next();
			cbs.setProject(project);
		}
	}

	public IConfigurationBuildState getConfigurationBuildState(String id, boolean create) {
		ConfigurationBuildState state = (ConfigurationBuildState)fCfgIdToStateMap.get(id);
		if(state == null){
			state = loadState(id, create);
			if(state.exists() || create)
				fCfgIdToStateMap.put(id, state);
			else
				state = null;
		}
		return state;
	}
	
	private ConfigurationBuildState loadState(String id, boolean create){
		File file = getFileForCfg(id, create);
		ConfigurationBuildState bs = new ConfigurationBuildState(fProject, id);
		if(file != null && file.exists()){
			try {
				InputStream iStream = new FileInputStream(file);
				bs.load(iStream);
				iStream.close();
			} catch (FileNotFoundException e) {
				ManagedBuilderCorePlugin.log(e);
			} catch (IOException e) {
				ManagedBuilderCorePlugin.log(e);
			}
		}
		return bs;
	}

	public IConfigurationBuildState[] getConfigurationBuildStates() {
		Properties props = getIdToNameProperties();
		List list = new ArrayList(props.size());
		for(Iterator iter = props.keySet().iterator(); iter.hasNext();){
			String id = (String)iter.next();
			IConfigurationBuildState state = getConfigurationBuildState(id, false);
			if(state != null)
				list.add(state);
		}
		return (ConfigurationBuildState[])list.toArray(new ConfigurationBuildState[list.size()]);
	}

	public void removeConfigurationBuildState(String id) {
		ConfigurationBuildState cbs = (ConfigurationBuildState)getConfigurationBuildState(id, false);
		if(cbs != null){
			cbs.setState(IRebuildState.NEED_REBUILD);
		}
	}

	public int getState() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setState(int state) {
		// TODO Auto-generated method stub
	}
	private static final int OP_CREATE = 1;
	private static final int OP_REMOVE = 2;
	
	private String getFileName(String id, int op){
		Properties props = getIdToNameProperties();
		String name = props.getProperty(id);
		if(name == null){
			if(op == OP_CREATE){
				name = new Integer(CDataUtil.genRandomNumber()).toString();
				props.setProperty(id, name);
				fIsMapInfoDirty = true;
	//			saveMapFile();
			}
		} else if (op == OP_REMOVE){
			props.remove(id);
			fIsMapInfoDirty = true;
		}
		return name;
	}
	
	private File getFileForCfg(String id, boolean create){
		String name = getFileName(id, create ? OP_CREATE : 0);
		if(name == null)
			return null;
		
		IPath path = BuildStateManager.getInstance().getPrefsDirPath(fProject);
		path = path.append(name);
		return path.toFile();
	}
	
	private void saveMapFile(){
		if(fCfgIdToFileNameProps == null)
			return;
		
		File file = getMapFile();
		File parent = file.getParentFile();
		if(!parent.exists())
			parent.mkdirs();
		
		try {
			OutputStream oStream = new FileOutputStream(file);
			fCfgIdToFileNameProps.store(oStream, "");
			oStream.close();
		} catch (FileNotFoundException e) {
			ManagedBuilderCorePlugin.log(e);
		} catch (IOException e) {
			ManagedBuilderCorePlugin.log(e);
		}
	}
	
	private File getMapFile(){
		IPath path = BuildStateManager.getInstance().getPrefsDirPath(fProject);
		path = path.append(getProjFileName());
		File file = path.toFile();
		return file;
	}

	private Properties getIdToNameProperties(){
		if(fCfgIdToFileNameProps == null){
			fCfgIdToFileNameProps = new Properties();
			File file = getMapFile();
			if(file.exists()){
				try {
					InputStream iStream = new FileInputStream(file);
					fCfgIdToFileNameProps.load(iStream);
					iStream.close();
				} catch (FileNotFoundException e) {
					ManagedBuilderCorePlugin.log(e);
				} catch (IOException e) {
					ManagedBuilderCorePlugin.log(e);
				}
			}
		}
		return fCfgIdToFileNameProps;
	}
	
	private String getProjFileName(){
		return fProject.getName();
	}

	public IProject getProject() {
		return fProject;
	}
	
	void serialize(){
		
		for(Iterator iter = fCfgIdToStateMap.values().iterator(); iter.hasNext();){
			ConfigurationBuildState s = (ConfigurationBuildState)iter.next();
			String id = s.getConfigurationId();
			if(!s.exists()){
				File file = getFileForCfg(id, false);
				if(file != null && file.exists()){
					file.delete();
					getFileName(id, OP_REMOVE);
				}
			} else {
				File file = getFileForCfg(id, true);
				File parent = file.getParentFile();
				if(!parent.exists())
					parent.mkdirs();
	
				try {
					FileOutputStream oStream = new FileOutputStream(file);
					s.store(oStream);
					oStream.close();
				} catch (FileNotFoundException e) {
					ManagedBuilderCorePlugin.log(e);
				} catch (IOException e) {
					ManagedBuilderCorePlugin.log(e);
				}
			}
		}

		if(fIsMapInfoDirty)
			saveMapFile();
	}
}
