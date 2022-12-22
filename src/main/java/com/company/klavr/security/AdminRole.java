package com.company.klavr.security;

import com.company.klavr.entity.*;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securityui.role.UiMinimalRole;
import io.jmix.securityui.role.annotation.MenuPolicy;
import io.jmix.securityui.role.annotation.ScreenPolicy;

@ResourceRole(name = "admin", code = "admin")
public interface AdminRole extends UiMinimalRole {
    @EntityAttributePolicy(entityClass = Difficulty.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Difficulty.class, actions = EntityPolicyAction.ALL)
    void difficulty();

    @EntityAttributePolicy(entityClass = Exercise.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Exercise.class, actions = EntityPolicyAction.ALL)
    void exercise();

    @EntityAttributePolicy(entityClass = Statistics.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Statistics.class, actions = EntityPolicyAction.ALL)
    void statistics();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.ALL)
    void user();

    @MenuPolicy(menuIds = {"User.browse", "Difficulty.browse", "Exercise.browse", "Statistics_User.browse", "Statistics_Admin.browse", "ExerciseUser.browse", "InfoScreen"})
    @ScreenPolicy(screenIds = {"User.browse", "Difficulty.browse", "Exercise.browse", "Statistics_User.browse", "Statistics_Admin.browse", "ExerciseUser.browse", "ExerciseScreen", "Difficulty.edit", "Exercise.edit", "Zone_.browse", "sec_UserSubstitutionsScreen", "sec_UserSubstitutionsFragment", "sec_UserSubstitutionEntity.edit", "sec_RowLevelRoleModel.lookup", "sec_RowLevelRoleModel.browse", "sec_RowLevelRoleModel.edit", "sec_RowLevelPolicyModel.edit", "sec_RoleFilterFragment", "sec_RoleAssignmentScreen", "sec_ResourceRoleModel.lookup", "sec_ResourceRoleModel.edit", "sec_ResourceRoleModel.browse", "sec_SpecificResourcePolicyModel.edit", "sec_ScreenResourcePolicyModel.edit", "sec_ScreenResourcePolicyModel.create", "sec_ResourcePolicyModel.edit", "sec_MenuResourcePolicyModel.create", "sec_EntityResourcePolicyModel.edit", "sec_EntityAttributeResourcePolicyModel.create", "sec_MenuResourcePolicyModel.edit", "sec_GraphQLResourcePolicyModel.edit", "sec_EntityResourcePolicyModel.create", "sec_EntityAttributeResourcePolicyModel.edit", "InfoScreen"})
    void screens();

    @EntityAttributePolicy(entityClass = Zone.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Zone.class, actions = EntityPolicyAction.ALL)
    void zone();

    @SpecificPolicy(resources = "ui.filter.modifyGlobalConfiguration")
    void specific();

    @EntityAttributePolicy(entityClass = UserProgress.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = UserProgress.class, actions = EntityPolicyAction.ALL)
    void userProgress();
}