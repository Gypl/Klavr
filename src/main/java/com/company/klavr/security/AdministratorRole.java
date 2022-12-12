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

@ResourceRole(name = "Administrator", code = "administrator")
public interface AdministratorRole extends UiMinimalRole {
    @EntityAttributePolicy(entityClass = Difficulty.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Difficulty.class, actions = EntityPolicyAction.ALL)
    void difficulty();

    @EntityAttributePolicy(entityClass = Exercise.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Exercise.class, actions = EntityPolicyAction.ALL)
    void exercise();

    @EntityAttributePolicy(entityClass = Zone.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Zone.class, actions = EntityPolicyAction.READ)
    void zone();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = User.class, actions = EntityPolicyAction.READ)
    void user();

    @EntityAttributePolicy(entityClass = Statistics.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Statistics.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.CREATE})
    void statistics();

    @MenuPolicy(menuIds = {"User.browse", "Statistics_Admin.browse", "Difficulty.browse", "Exercise.browse", "ExerciseUser.browse", "Statistics_User.browse"})
    @ScreenPolicy(screenIds = {"User.browse", "ExerciseScreen", "Statistics_Admin.browse", "Difficulty.browse", "Zone_.browse", "Exercise.browse", "Difficulty.edit", "Exercise.edit", "LoginScreen", "MainScreen", "User.edit", "backgroundWorkProgressScreen", "bulkEditorWindow", "singleFileUploadDialog", "selectValueDialog", "ui_DateIntervalDialog", "notFoundScreen", "inputDialog", "ui_FilterConfigurationModel.fragment", "ui_LayoutAnalyzerScreen", "ui_UiDataFilterConfigurationModel.fragment", "ExerciseUser.browse", "Statistics_User.browse"})
    void screens();
}