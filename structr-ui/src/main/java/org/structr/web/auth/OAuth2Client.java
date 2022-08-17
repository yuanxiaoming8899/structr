/*
 * Copyright (C) 2010-2022 Structr GmbH
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
package org.structr.web.auth;


import com.github.scribejava.core.model.OAuth2AccessToken;
import org.structr.common.error.FrameworkException;
import org.structr.core.entity.Principal;

public interface OAuth2Client {
    String getAuthorizationURL(final String state);
    OAuth2AccessToken getAccessToken(final String authorizationReplyCode);

    String getClientCredentials(final OAuth2AccessToken accessToken);

    String getReturnURI();

    String getErrorURI();

    void invokeOnLoginMethod(Principal user) throws FrameworkException;

    String getCredentialKey();

    void initializeAutoCreatedUser(Principal user);

    String getLogoutURI();
}