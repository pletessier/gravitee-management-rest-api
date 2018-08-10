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
package io.gravitee.management.rest.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gravitee.common.http.HttpStatusCode;
import io.gravitee.definition.jackson.datatype.GraviteeMapper;
import io.gravitee.definition.model.Path;
import io.gravitee.management.model.ImportSwaggerDescriptorEntity;
import io.gravitee.management.model.PolicyEntity;
import io.gravitee.management.service.impl.SwaggerServiceImpl;
import org.junit.Test;
import org.mockito.InjectMocks;

import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Nicolas Geraud (nicolas.geraud at gmail.com)
 */
public class PoliciesResourceTest extends AbstractResourceTest {

    protected String contextPath() {
        return "policies";
    }

    @InjectMocks
    private SwaggerServiceImpl swaggerService = new SwaggerServiceImpl();

    @Test
    public void shouldGetPoliciesemptyList() {
        when(policyService.findAll()).thenReturn(Collections.emptySet());

        final Response response = target().request().get();

        assertEquals(HttpStatusCode.OK_200, response.getStatus());
        assertTrue("empty", response.readEntity(Set.class).isEmpty());
    }

    @Test
    public void shouldGetPoliciesList() {
        HashSet<PolicyEntity> policyEntities = new HashSet<>();
        PolicyEntity policyEntity = new PolicyEntity();
        policyEntities.add(policyEntity);
        policyEntity.setId("my-api");
        policyEntity.setName("My Api");

        when(policyService.findAll()).thenReturn(policyEntities);

        final Response response = target().request().get();

        assertEquals(HttpStatusCode.OK_200, response.getStatus());
        Set entity = response.readEntity(Set.class);
        assertFalse("not empty", entity.isEmpty());
        assertEquals("one element", 1, entity.size());
        Object o = entity.iterator().next();
        assertTrue(o instanceof LinkedHashMap);
        LinkedHashMap<String, String> elt = (LinkedHashMap<String, String>)o;
        assertEquals("id", "my-api", elt.get("id"));
        assertEquals("name", "My Api", elt.get("name"));
    }

    @Test
    public void shouldGetPoliciesListWithSchema() {
        HashSet<PolicyEntity> policyEntities = new HashSet<>();
        PolicyEntity policyEntity = new PolicyEntity();
        policyEntities.add(policyEntity);
        policyEntity.setId("my-api");

        when(policyService.findAll()).thenReturn(policyEntities);
        when(policyService.getSchema(any())).thenReturn("policy schema");

        final Response response = target().queryParam("expand", "schema").request().get();

        assertEquals(HttpStatusCode.OK_200, response.getStatus());
        Set entity = response.readEntity(Set.class);
        assertFalse("not empty", entity.isEmpty());
        assertEquals("one element", 1, entity.size());
        Object o = entity.iterator().next();
        assertTrue(o instanceof LinkedHashMap);
        LinkedHashMap<String, String> elt = (LinkedHashMap<String, String>)o;
        assertEquals("id", "my-api", elt.get("id"));
        assertEquals("schema", "policy schema", elt.get("schema"));
    }


    @Test
    public void shouldGetPoliciesListWithUnknownExpand() {
        HashSet<PolicyEntity> policyEntities = new HashSet<>();
        PolicyEntity policyEntity = new PolicyEntity();
        policyEntities.add(policyEntity);
        policyEntity.setId("my-api");

        when(policyService.findAll()).thenReturn(policyEntities);

        final Response response = target().queryParam("expand", "unknown").request().get();

        assertEquals(HttpStatusCode.OK_200, response.getStatus());
        Set entity = response.readEntity(Set.class);
        assertFalse("not empty", entity.isEmpty());
        assertEquals("one element", 1, entity.size());
        Object o = entity.iterator().next();
        assertTrue(o instanceof LinkedHashMap);
        LinkedHashMap<String, String> elt = (LinkedHashMap<String, String>)o;
        assertEquals("id", "my-api", elt.get("id"));
        assertFalse("unknown expand", elt.containsKey("schema"));
        assertFalse("unknown expand", elt.containsKey("unknown"));
    }

    @Test
    public void shouldImportSwaggerPathsWithPolicies() {

        ImportSwaggerDescriptorEntity swaggerDescriptor = new ImportSwaggerDescriptorEntity();
        swaggerDescriptor.setType(ImportSwaggerDescriptorEntity.Type.URL);
        swaggerDescriptor.setPayload("http://petstore.swagger.io/v2/swagger.json");
        Map<String, Path> paths = swaggerService.preparePolicies(swaggerDescriptor);

        ObjectMapper objectMapper = new GraviteeMapper();
        try {
            objectMapper.writeValue(new File("entity.json"), paths);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }
}
