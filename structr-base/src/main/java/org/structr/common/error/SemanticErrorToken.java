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
package org.structr.common.error;

import org.structr.core.property.PropertyKey;

/**
 * Abstract base class for semantic error tokens.
 *
 *
 */
public class SemanticErrorToken extends ErrorToken {

	public SemanticErrorToken(final String type, final PropertyKey propertyKey, final String token) {
		this(type, propertyKey, token, null, null);
	}
	public SemanticErrorToken(final String type, final PropertyKey propertyKey, final String token, final Object detail) {
		this(type, propertyKey, token, detail, null);
	}

	public SemanticErrorToken(final String type, final PropertyKey propertyKey, final String token, final Object detail, final Object value) {
		super(type, propertyKey != null ? propertyKey.jsonName() : null, token, detail, value);
	}
}
