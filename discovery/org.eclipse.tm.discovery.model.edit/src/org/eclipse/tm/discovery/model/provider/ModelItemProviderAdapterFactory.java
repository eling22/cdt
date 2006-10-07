/********************************************************************************
 * Copyright (c) 2006 Symbian Software Ltd. All rights reserved.
 * This program and the accompanying materials are made available under the terms
 * of the Eclipse Public License v1.0 which accompanies this distribution, and is 
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Javier Montalvo Orús (Symbian) - initial API and implementation
 ********************************************************************************/

package org.eclipse.tm.discovery.model.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITableItemLabelProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.tm.discovery.model.util.ModelAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * 
 * @generated
 */
public class ModelItemProviderAdapterFactory extends ModelAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
	/**
	 * This keeps track of the root adapter factory that delegates to this adapter factory.
	 * 
	 * @generated
	 */
	protected ComposedAdapterFactory parentAdapterFactory;

	/**
	 * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
	 * 
	 * @generated
	 */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

	/**
	 * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
	 * 
	 * @generated
	 */
	protected Collection supportedTypes = new ArrayList();

	/**
	 * This constructs an instance.
	 * 
	 * @generated not
	 */
	public ModelItemProviderAdapterFactory() {
		supportedTypes.add(IEditingDomainItemProvider.class);
		supportedTypes.add(IStructuredItemContentProvider.class);
		supportedTypes.add(ITreeItemContentProvider.class);
		supportedTypes.add(IItemLabelProvider.class);
		supportedTypes.add(IItemPropertySource.class);	
		supportedTypes.add(ITableItemLabelProvider.class);
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.tm.discovery.model.Device} instances.
	 * 
	 * @generated
	 */
	protected DeviceItemProvider deviceItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.tm.discovery.model.Device}.
	 * 
	 * @generated
	 */
	public Adapter createDeviceAdapter() {
		if (deviceItemProvider == null) {
			deviceItemProvider = new DeviceItemProvider(this);
		}

		return deviceItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.tm.discovery.model.Network} instances.
	 * 
	 * @generated
	 */
	protected NetworkItemProvider networkItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.tm.discovery.model.Network}.
	 * 
	 * @generated
	 */
	public Adapter createNetworkAdapter() {
		if (networkItemProvider == null) {
			networkItemProvider = new NetworkItemProvider(this);
		}

		return networkItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.tm.discovery.model.Pair} instances.
	 * 
	 * @generated
	 */
	protected PairItemProvider pairItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.tm.discovery.model.Pair}.
	 * 
	 * @generated
	 */
	public Adapter createPairAdapter() {
		if (pairItemProvider == null) {
			pairItemProvider = new PairItemProvider(this);
		}

		return pairItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.tm.discovery.model.Service} instances.
	 *
	 * @generated
	 */
	protected ServiceItemProvider serviceItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.tm.discovery.model.Service}.
	 *
	 * @generated
	 */
	public Adapter createServiceAdapter() {
		if (serviceItemProvider == null) {
			serviceItemProvider = new ServiceItemProvider(this);
		}

		return serviceItemProvider;
	}

	/**
	 * This keeps track of the one adapter used for all {@link org.eclipse.tm.discovery.model.ServiceType} instances.
	 * 
	 * @generated
	 */
	protected ServiceTypeItemProvider serviceTypeItemProvider;

	/**
	 * This creates an adapter for a {@link org.eclipse.tm.discovery.model.ServiceType}.
	 * 
	 * @generated
	 */
	public Adapter createServiceTypeAdapter() {
		if (serviceTypeItemProvider == null) {
			serviceTypeItemProvider = new ServiceTypeItemProvider(this);
		}

		return serviceTypeItemProvider;
	}

	/**
	 * This returns the root adapter factory that contains this factory.
	 * 
	 * @generated
	 */
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
	}

	/**
	 * This sets the composed adapter factory that contains this factory.
	 * 
	 * @generated
	 */
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parentAdapterFactory = parentAdapterFactory;
	}

	/**
	 * 
	 * @generated
	 */
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type) || super.isFactoryForType(type);
	}

	/**
	 * This implementation substitutes the factory itself as the key for the adapter.
	 * 
	 * @generated
	 */
	public Adapter adapt(Notifier notifier, Object type) {
		return super.adapt(notifier, this);
	}

	/**
	 *
	 * @generated
	 */
	public Object adapt(Object object, Object type) {
		if (isFactoryForType(type)) {
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class) || (((Class)type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	/**
	 * This adds a listener.
	 * 
	 * @generated
	 */
	public void addListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.addListener(notifyChangedListener);
	}

	/**
	 * This removes a listener.
	 *
	 * @generated
	 */
	public void removeListener(INotifyChangedListener notifyChangedListener) {
		changeNotifier.removeListener(notifyChangedListener);
	}

	/**
	 * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
	 * 
	 * @generated
	 */
	public void fireNotifyChanged(Notification notification) {
		changeNotifier.fireNotifyChanged(notification);

		if (parentAdapterFactory != null) {
			parentAdapterFactory.fireNotifyChanged(notification);
		}
	}

	/**
	 * This disposes all of the item providers created by this factory. 
	 * 
	 * @generated
	 */
	public void dispose() {
		if (deviceItemProvider != null) deviceItemProvider.dispose();
		if (networkItemProvider != null) networkItemProvider.dispose();
		if (pairItemProvider != null) pairItemProvider.dispose();
		if (serviceItemProvider != null) serviceItemProvider.dispose();
		if (serviceTypeItemProvider != null) serviceTypeItemProvider.dispose();
	}

}
