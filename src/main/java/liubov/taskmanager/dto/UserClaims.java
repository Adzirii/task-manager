package liubov.taskmanager.dto;

import java.util.Collection;

public record UserClaims(Long userId, String email, String username) {
}
