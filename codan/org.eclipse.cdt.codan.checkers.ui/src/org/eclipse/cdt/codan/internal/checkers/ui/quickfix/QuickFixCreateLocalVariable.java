/*******************************************************************************
 * Copyright (c) 2010 Tomasz Wesolowski
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Tomasz Wesolowski - initial API and implementation
 *******************************************************************************/
package org.eclipse.cdt.codan.internal.checkers.ui.quickfix;

import org.eclipse.cdt.codan.core.cxx.CxxAstUtils;
import org.eclipse.cdt.codan.internal.checkers.ui.CheckersUiActivator;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.INodeFactory;
import org.eclipse.cdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ltk.core.refactoring.Change;

public class QuickFixCreateLocalVariable extends AbstractAstRewriteQuickFix {
	public String getLabel() {
		return Messages.QuickFixCreateLocalVariable_0;
	}

	/**
	 * 
	 * @param ast
	 * @param astName
	 * @param r
	 */
	public void modifyAST(IIndex index, IMarker marker) {
		CxxAstUtils utils = CxxAstUtils.getInstance();

		IASTTranslationUnit ast;
		try {
			ITranslationUnit tu = getTranslationUnitViaEditor(marker);
			ast = tu.getAST(index, ITranslationUnit.AST_SKIP_INDEXED_HEADERS);
		} catch (CoreException e) {
			CheckersUiActivator.log(e);
			return;
		}
		
		IASTName astName = getASTNameFromMarker(marker, ast);
		if (astName == null) {
			return;
		}
		ASTRewrite r = ASTRewrite.create(ast);
		INodeFactory factory = ast.getASTNodeFactory();
		IASTDeclaration declaration = utils.createDeclaration(astName, factory);
		IASTDeclarationStatement newStatement = factory
				.newDeclarationStatement(declaration);
		IASTNode targetStatement = utils.getEnclosingStatement(astName);
		if (targetStatement == null) {
			return;
		}
		r.insertBefore(targetStatement.getParent(), targetStatement,
				newStatement, null);
		Change c = r.rewriteAST();
		try {
			c.perform(new NullProgressMonitor());
		} catch (CoreException e) {
			CheckersUiActivator.log(e);
			return;
		}
	}

	@Override
	public boolean isApplicable(IMarker marker) {
		String problemArgument = getProblemArgument(marker, 1);
		return problemArgument.contains(":func"); //$NON-NLS-1$
	}
}
