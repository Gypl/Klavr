package com.company.klavr.security;

import com.company.klavr.entity.Exercise;
import com.company.klavr.entity.Statistics;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityui.role.UiMinimalRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "Common User", code = "common-user")
public interface CommonUserRole extends UiMinimalRole {
    @EntityAttributePolicy(entityClass = Exercise.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Exercise.class, actions = EntityPolicyAction.READ)
    void exercise();

    @EntityAttributePolicy(entityClass = Statistics.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Statistics.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.CREATE})
    void statistics();

    @MenuPolicy(menuIds = {"Statistics_User.browse", "ExerciseUser.browse"})
    @ScreenPolicy(screenIds = {"ExerciseScreen", "Statistics_User.browse", "ExerciseUser.browse", "MainScreen", "LoginScreen"})
    void screens();

}