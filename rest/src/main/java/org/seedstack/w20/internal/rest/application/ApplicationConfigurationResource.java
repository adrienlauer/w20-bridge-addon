/**
 * Copyright (c) 2013-2015, The SeedStack authors <http://seedstack.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.seedstack.w20.internal.rest.application;


import com.google.inject.Inject;
import org.seedstack.seed.web.WebResourceResolver;
import org.seedstack.w20.AnonymousFragmentDeclaration;
import org.seedstack.w20.ConfiguredFragmentDeclaration;
import org.seedstack.w20.ConfiguredModule;
import org.seedstack.w20.FragmentDeclaration;
import org.seedstack.w20.FragmentManager;
import org.seedstack.w20.internal.rest.EmptyObjectRepresentation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * This REST resource generates the W20 application configuration.
 *
 * @author adrien.lauer@mpsa.com
 */
@Path("/seed-w20/application/configuration")
public class ApplicationConfigurationResource {
    @Inject
    FragmentManager fragmentManager;

    @Inject(optional = true)
    private WebResourceResolver webResourceResolver;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getConfiguration() {
        Map<String, Object> configuredFragmentRepresentations = new HashMap<String, Object>();

        for (FragmentDeclaration declaredFragment : fragmentManager.getDeclaredFragments()) {
            if (declaredFragment instanceof AnonymousFragmentDeclaration) {
                configuredFragmentRepresentations.put("", ((AnonymousFragmentDeclaration) declaredFragment).getContents());
            } else if (declaredFragment instanceof ConfiguredFragmentDeclaration) {
                ConfiguredFragmentDeclaration configuredFragment = (ConfiguredFragmentDeclaration) declaredFragment;
                ConfiguredFragmentRepresentation value = new ConfiguredFragmentRepresentation();

                value.setPreload(configuredFragment.isPreload() == null ? true : configuredFragment.isPreload());

                Map<String, Object> modules = new HashMap<String, Object>();
                for (ConfiguredModule configuredModule : configuredFragment.getModules().values()) {
                    Object configuration = configuredModule.getConfiguration();
                    modules.put(configuredModule.getName(), configuration != null ? configuration : new EmptyObjectRepresentation());
                }

                value.setModules(modules);
                value.setVars(configuredFragment.getVars() != null ? configuredFragment.getVars() : new HashMap<String, String>());

                if (configuredFragment.getManifestLocation() != null) {
                    // Only includes scanned fragments if a resource resolver is available, otherwise they must be configured manually
                    if (webResourceResolver != null) {
                        URI resolvedUri = webResourceResolver.resolveURI(configuredFragment.getManifestLocation());

                        if (resolvedUri == null) {
                            throw new IllegalArgumentException("Unable to resolve a web serving path for fragment " + configuredFragment.getName());
                        }

                        configuredFragmentRepresentations.put(resolvedUri.toString(), value);
                    }
                } else {
                    configuredFragmentRepresentations.put(configuredFragment.getName(), value);
                }
            }
        }

        return configuredFragmentRepresentations;
    }
}
