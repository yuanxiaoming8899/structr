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
package org.structr.flow.impl.rels;

import org.structr.api.graph.PropagationDirection;
import org.structr.api.graph.PropagationMode;
import org.structr.common.PermissionPropagation;
import org.structr.core.entity.ManyToOne;
import org.structr.flow.impl.FlowContainer;
import org.structr.web.entity.dom.DOMNode;

/**
 *
 */
public class DOMNodeFLOWFlowContainer extends ManyToOne<DOMNode, FlowContainer> implements PermissionPropagation {

	@Override
	public Class<DOMNode> getSourceType() {
		return DOMNode.class;
	}

	@Override
	public Class<FlowContainer> getTargetType() {
		return FlowContainer.class;
	}

	@Override
	public String name() {
		return "FLOW";
	}

	@Override
	public PropagationDirection getPropagationDirection() {
		return PropagationDirection.Both;
	}

	@Override
	public PropagationMode getReadPropagation() {
		return PropagationMode.Add;
	}

	@Override
	public PropagationMode getWritePropagation() {
		return PropagationMode.Keep;
	}

	@Override
	public PropagationMode getDeletePropagation() {
		return PropagationMode.Keep;
	}

	@Override
	public PropagationMode getAccessControlPropagation() {
		return PropagationMode.Keep;
	}

	@Override
	public String getDeltaProperties() {
		return null;
	}
}
