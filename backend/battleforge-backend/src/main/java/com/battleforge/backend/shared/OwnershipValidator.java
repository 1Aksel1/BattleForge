package com.battleforge.backend.shared;

import com.battleforge.backend.exceptions.ForbiddenException;
import com.battleforge.backend.model.Run;
import com.battleforge.backend.model.User;
import org.springframework.stereotype.Component;

@Component
public class OwnershipValidator {

    public void assertRunBelongsToUser(Run run, User user) {

        if (!run.getHero().getUser().getId().equals(user.getId())) {
            throw new ForbiddenException("Access denied: this run does not belong to the authenticated user.");
        }

    }

}
