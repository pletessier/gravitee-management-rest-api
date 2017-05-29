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
package io.gravitee.management.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.gravitee.management.model.permissions.RoleScope;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;
import java.util.Objects;

/**
 * @author Nicolas GERAUD (nicolas.geraud at graviteesource.com) 
 * @author GraviteeSource Team
 */
@JsonIgnoreProperties("system")
public class UpdateRoleEntity {

    @NotNull
    @Size(min = 1)
    private String name;

    private String description;

    @NotNull
    private RoleScope scope;

    @JsonProperty(value = "default")
    private boolean defaultRole;

    private Map<String, char[]> permissions;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoleScope getScope() {
        return scope;
    }
    public void setScope(RoleScope scope) {
        this.scope = scope;
    }

    public Map<String, char[]> getPermissions() {
        return permissions;
    }
    public void setPermissions(Map<String, char[]> permissions) {
        this.permissions = permissions;
    }

    public boolean isDefaultRole() {
        return defaultRole;
    }
    public void setDefaultRole(boolean defaultRole) {
        this.defaultRole = defaultRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateRoleEntity that = (UpdateRoleEntity) o;

        return Objects.equals(scope, that.scope) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scope, name);
    }
}
