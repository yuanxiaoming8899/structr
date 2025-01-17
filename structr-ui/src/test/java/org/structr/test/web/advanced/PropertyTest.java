/*
 * Copyright (C) 2010-2023 Structr GmbH
 *
 * This file is part of Structr <http://structr.org>.
 *
 * Structr is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Structr is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Structr.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.structr.test.web.advanced;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.structr.api.schema.JsonSchema;
import org.structr.common.error.FrameworkException;
import org.structr.core.entity.SchemaNode;
import org.structr.core.entity.SchemaProperty;
import org.structr.core.graph.NodeAttribute;
import org.structr.core.graph.Tx;
import org.structr.schema.export.StructrSchema;
import org.structr.test.web.StructrUiTest;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Map;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.fail;

/**
 *
 *
 */
public class PropertyTest extends StructrUiTest {

	private static final Logger logger = LoggerFactory.getLogger(PropertyTest.class);

	/**
	 * This test creates a new type "Test" with a Notion property that references a type (User)
	 * which is only present in the ui module.
	 */
	@Test
	public void testNotionProperty() {

		// schema setup
		try (final Tx tx = app.tx()) {

			final SchemaNode test  = app.create(SchemaNode.class,
				new NodeAttribute<>(SchemaNode.name, "Test")
			);

			app.create(SchemaProperty.class,
					new NodeAttribute<>(SchemaProperty.name, "ownerPrincipalEmail"),
					new NodeAttribute<>(SchemaProperty.propertyType, "Notion"),
					new NodeAttribute<>(SchemaProperty.format, "owner, User.eMail"),
					new NodeAttribute<>(SchemaProperty.schemaNode, test)
			);

			tx.success();

		} catch (FrameworkException fex) {

			logger.warn("", fex);
			fail("Unexpected exception");
		}
	}

	@Test
	public void testCustomProperty() {

		// schema setup
		try (final Tx tx = app.tx()) {

			final JsonSchema schema       = StructrSchema.createFromDatabase(app, Arrays.asList("Image"));
			final Gson gson               = new GsonBuilder().create();
			final Map<String, Object> str = (Map<String, Object>)gson.fromJson(schema.toString(), Map.class);
			final Map<String, Object> def = (Map<String, Object>)str.get("definitions");
			final Map<String, Object> img = (Map<String, Object>)def.get("Image");
			final Map<String, Object> pro = (Map<String, Object>)img.get("properties");
			final Map<String, Object> tn  = (Map<String, Object>)pro.get("tnMid");

			assertEquals("Export of custom property should contain format string.", "300, 300, false", tn.get("format"));

			tx.success();

		} catch (FrameworkException t) {

			t.printStackTrace();
			fail("Unexpected exception");
		}

	}

}





