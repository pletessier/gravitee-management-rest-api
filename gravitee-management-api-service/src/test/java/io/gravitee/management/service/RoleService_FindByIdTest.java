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

import io.gravitee.management.model.RoleEntity;
import io.gravitee.management.model.permissions.RolePermissionAction;
import io.gravitee.repository.management.model.RoleScope;
import io.gravitee.management.service.impl.RoleServiceImpl;
import io.gravitee.repository.exceptions.TechnicalException;
import io.gravitee.repository.management.api.RoleRepository;
import io.gravitee.repository.management.model.Role;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static io.gravitee.management.model.permissions.PortalPermission.DOCUMENTATION;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Nicolas GERAUD (nicolas.geraud at graviteesource.com)
 * @author GraviteeSource Team
 */
@RunWith(MockitoJUnitRunner.class)
public class RoleService_FindByIdTest {

    @InjectMocks
    private RoleServiceImpl roleService = new RoleServiceImpl();

    @Mock
    private RoleRepository mockRoleRepository;

    @Test
    public void shouldFindById_C() throws TechnicalException {
        test_int_to_CRUD(1108, RolePermissionAction.CREATE);
    }

    @Test
    public void shouldFindById_R() throws TechnicalException {
        test_int_to_CRUD(1104, RolePermissionAction.READ);
    }

    @Test
    public void shouldFindById_U() throws TechnicalException {
        test_int_to_CRUD(1102, RolePermissionAction.UPDATE);
    }

    @Test
    public void shouldFindById_D() throws TechnicalException {
        test_int_to_CRUD(1101, RolePermissionAction.DELETE);
    }

    @Test
    public void shouldFindById_CRUD() throws TechnicalException {
        test_int_to_CRUD(1115, RolePermissionAction.CREATE, RolePermissionAction.READ, RolePermissionAction.UPDATE, RolePermissionAction.DELETE);
    }

    private void test_int_to_CRUD(int perm, RolePermissionAction... action) throws TechnicalException {
        Role roleMock = mock(Role.class);
        when(roleMock.getScope()).thenReturn(RoleScope.PORTAL);
        when(roleMock.getName()).thenReturn("name");
        when(roleMock.getPermissions()).thenReturn(new int[]{perm});
        when(mockRoleRepository.findById(RoleScope.PORTAL, "name")).thenReturn(Optional.of(roleMock));

        RoleEntity entity = roleService.findById(RoleScope.PORTAL, "name");

        assertNotNull("no entity found", entity);
        assertEquals("invalid scope", io.gravitee.management.model.permissions.RoleScope.PORTAL , entity.getScope());
        assertFalse("no permissions found", entity.getPermissions().isEmpty());
        assertTrue("invalid Permission name", entity.getPermissions().containsKey(DOCUMENTATION.getName()));
        char[] perms = entity.getPermissions().get(DOCUMENTATION.getName());
        assertEquals("not enough permissions", action.length, perms.length);
        for (RolePermissionAction rolePermissionAction : action) {
            assertTrue("not the good permission", Arrays.asList(ArrayUtils.toObject(perms)).contains(rolePermissionAction.getId()));
        }
    }
}
