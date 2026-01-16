package cat.itacademyS5_01.player.dto;

import java.util.UUID;

public record PlayerResponse(
        UUID id,
        String name
) {
}