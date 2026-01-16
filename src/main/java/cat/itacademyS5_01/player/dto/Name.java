package cat.itacademyS5_01.player.dto;

import cat.itacademyS5_01.exception.MissingNameException;
import jakarta.validation.constraints.NotBlank;


public record Name(@NotBlank String name) {
    public Name {
        if (name == null) {
            throw new MissingNameException("Name cannot be null");
        }

        name = name.strip();

        if (name.isEmpty()) {
            throw new MissingNameException("Name cannot be empty");
        }
    }


}

