package com.company.klavr.security;

import com.company.klavr.entity.*;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.UiMinimalRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "User", code = "user")
public interface UserRole extends UiMinimalRole {
    @EntityAttributePolicy(entityClass = Statistics.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Statistics.class, actions = {EntityPolicyAction.CREATE, EntityPolicyAction.READ})
    void statistics();

    @EntityAttributePolicy(entityClass = Exercise.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Exercise.class, actions = EntityPolicyAction.READ)
    void exercise();

    @MenuPolicy(menuIds = {"ExerciseUser.browse", "Statistics_User.browse"})
    @ScreenPolicy(screenIds = {"ExerciseScreen", "ExerciseUser.browse", "Statistics_User.browse"})
    void screens();

    @EntityAttributePolicy(entityClass = Difficulty.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Difficulty.class, actions = EntityPolicyAction.READ)
    void difficulty();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
    void user();

    @EntityAttributePolicy(entityClass = Zone.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Zone.class, actions = EntityPolicyAction.READ)
    void zone();
}