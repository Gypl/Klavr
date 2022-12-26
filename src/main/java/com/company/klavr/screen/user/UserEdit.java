package com.company.klavr.screen.user;

import io.jmix.security.impl.role.assignment.InMemoryRoleAssignmentProvider;
import io.jmix.security.role.assignment.RoleAssignment;
import com.company.klavr.entity.User;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.core.security.event.SingleUserPasswordChangeEvent;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import io.jmix.ui.Notifications;
import io.jmix.ui.component.PasswordField;
import io.jmix.ui.component.TextField;
import io.jmix.ui.model.DataContext;
import io.jmix.ui.navigation.Route;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.management.relation.Role;
import java.util.Objects;

@UiController("User.edit")
@UiDescriptor("user-edit.xml")
@EditedEntityContainer("userDc")
@Route(value = "users/edit", parentPrefix = "users")
public class UserEdit extends StandardEditor<User> {

    @Autowired
    private DataManager dataManager;
    @Autowired
    private EntityStates entityStates;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordField passwordField;

    @Autowired
    private TextField<String> usernameField;

    @Autowired
    private PasswordField confirmPasswordField;

    @Autowired
    private Notifications notifications;

    @Autowired
    private MessageBundle messageBundle;

    private boolean isNewEntity;

    @Subscribe
    public void onAfterCommitChanges(AfterCommitChangesEvent event) {
        RoleAssignmentEntity role = dataManager.create(RoleAssignmentEntity.class);
        role.setRoleCode("user");
        role.setRoleType("resource");
        role.setUsername(Objects.requireNonNull(usernameField.getValue()));
        role.setVersion(1);
        dataManager.save(role);
    }

    @Subscribe
    public void onInitEntity(InitEntityEvent<User> event) {
        usernameField.setEditable(true);
        passwordField.setVisible(true);
        confirmPasswordField.setVisible(true);
        isNewEntity = true;
    }

    @Subscribe
    public void onAfterShow(AfterShowEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            usernameField.focus();
        }
    }

    @Subscribe
    protected void onBeforeCommit(BeforeCommitChangesEvent event) {
        if (passwordField.getValue().length() < 6) {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption(messageBundle.getMessage("passwordsLow"))
                    .show();
            event.preventCommit();
        } else
        if (usernameField.getValue().length() < 4) {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption(messageBundle.getMessage("usernameLow"))
                    .show();
            event.preventCommit();
        } else if (entityStates.isNew(getEditedEntity())) {
            if (!Objects.equals(passwordField.getValue(), confirmPasswordField.getValue())) {
                notifications.create(Notifications.NotificationType.WARNING)
                        .withCaption(messageBundle.getMessage("passwordsDoNotMatch"))
                        .show();
                event.preventCommit();
            }
            /*RoleAssignment roleAssignment = new RoleAssignment (Objects.requireNonNull(usernameField.getValue()), "user", RoleAssignmentRoleType.RESOURCE);
            InMemoryRoleAssignmentProvider inMemoryRoleAssignmentProvider = new InMemoryRoleAssignmentProvider();
            inMemoryRoleAssignmentProvider.addAssignment(roleAssignment);*/

            getEditedEntity().setPassword(passwordEncoder.encode(passwordField.getValue()));
        }
    }

    @Subscribe(target = Target.DATA_CONTEXT)
    public void onPostCommit(DataContext.PostCommitEvent event) {
        if (isNewEntity) {
            getApplicationContext().publishEvent(new SingleUserPasswordChangeEvent(getEditedEntity().getUsername(), passwordField.getValue()));

        }
    }
}