/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.management.service;

import io.gravitee.definition.model.Path;
import io.gravitee.management.model.ImportSwaggerDescriptorEntity;
import io.gravitee.management.model.api.NewApiEntity;
import io.gravitee.management.model.PageEntity;

import java.util.Map;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public interface SwaggerService {

    /**
     * Prepare an API from a Swagger descriptor. This method does not create an API but
     * extract data from Swagger to prepare an API to create.
     *
     * @param swaggerDescriptor Swagger descriptor
     * @return The API from the Swagger descriptor
     */
    NewApiEntity prepare(ImportSwaggerDescriptorEntity swaggerDescriptor);

    /**
     * Prepare an API from a Swagger descriptor. This method does not create an API but
     * extract data with security constraints from Swagger to prepare an API to update with policies.
     *
     * @param swaggerDescriptor Swagger descriptor
     * @return The API from the Swagger descriptor
     */
    Map<String, Path> preparePolicies(ImportSwaggerDescriptorEntity swaggerDescriptor);

    void transform(PageEntity page);
}
