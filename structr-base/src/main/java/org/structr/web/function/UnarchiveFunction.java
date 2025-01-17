/*
 * Copyright (C) 2010-2023 Structr GmbH
 *
 * This file is part of Structr <http://structr.org>.
 *
 * Structr is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Structr is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Structr.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.structr.web.function;

import org.apache.commons.lang3.StringUtils;
import org.structr.common.error.FrameworkException;
import org.structr.core.app.StructrApp;
import org.structr.core.property.PropertyMap;
import org.structr.schema.ConfigurationProvider;
import org.structr.schema.action.ActionContext;
import org.structr.web.common.FileHelper;
import org.structr.web.entity.File;
import org.structr.web.entity.Folder;

public class UnarchiveFunction extends UiAdvancedFunction {

	public static final String ERROR_MESSAGE_UNARCHIVE    = "Usage: ${unarchive(archiveFile [, parentFolder])}. Example: ${unarchive(first(find('File', 'name', 'archive.zip')), first(find('Folder', 'name', 'parent')) )}";
	public static final String ERROR_MESSAGE_UNARCHIVE_JS = "Usage: ${{$.unarchive(archiveFile [, parentFolder])}}. Example: ${{ $.unarchive($.first($.find('File', 'name', 'archive.zip')), $.first($.find('Folder', 'name', 'parent')) )}}";

	@Override
	public String getName() {
		return "unarchive";
	}

	@Override
	public String getSignature() {
		return "file, [, parentFolder ]";
	}

	@Override
	public Object apply(ActionContext ctx, Object caller, Object[] sources) throws FrameworkException {


		if (sources == null || sources.length < 1 || sources.length > 2
				|| (sources[0] != null && !(sources[0] instanceof File)
				|| (sources.length == 2 && sources[1] != null && !(sources[1] instanceof Folder)))) {

			logParameterError(caller, sources, ctx.isJavaScriptContext());

			return usage(ctx.isJavaScriptContext());
		}

		final ConfigurationProvider config = StructrApp.getConfiguration();

		try {
			final File archiveFile    = (File) sources[0];
			Folder parentFolder;

			if (sources.length == 2 && sources[1] != null) {

				parentFolder = (Folder) sources[2];

			} else {

				final PropertyMap props = new PropertyMap();
				props.put(StructrApp.key(Folder.class, "name"), StringUtils.substringBeforeLast(archiveFile.getName(), "."));
				props.put(StructrApp.key(Folder.class, "parent"), archiveFile.getParent());

				// Create folder with same name (without extension) and in same folder as file
				parentFolder = StructrApp.getInstance(ctx.getSecurityContext()).create(Folder.class, props);
			}

			FileHelper.unarchive(ctx.getSecurityContext(), (File) sources[0], parentFolder == null ? null : parentFolder.getUuid());

		} catch (final Exception e) {

			logException(caller, e, sources);
		}
		return null;
	}

	@Override
	public String usage(boolean inJavaScriptContext) {

		return (inJavaScriptContext ? ERROR_MESSAGE_UNARCHIVE_JS : ERROR_MESSAGE_UNARCHIVE);
	}

	@Override
	public String shortDescription() {

		return "Unarchives given file to an optional parent folder.";
	}

}