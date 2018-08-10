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
package io.gravitee.management.service.exceptions;

import io.gravitee.common.http.HttpStatusCode;
import io.swagger.models.HttpMethod;

/**
 * @author Pierre LETESSIER (pierre.letessier at ina.fr)
 */
public class UnknownHttpMethodException extends AbstractNotFoundException {

    private final HttpMethod httpMethod;

    public UnknownHttpMethodException(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    @Override
    public String getMessage() {
        return "HttpMethod [" + httpMethod + "] is unknown.";
    }

    @Override
    public int getHttpStatusCode() {
        return HttpStatusCode.INTERNAL_SERVER_ERROR_500;
    }
}
