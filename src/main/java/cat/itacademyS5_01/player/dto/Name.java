package cat.itacademyS5_01.player.dto;

import jakarta.validation.constraints.NotBlank;

public record Name(@NotBlank(message = "Name can't be empty") String name) {
    public Name(String name){
        this.name = name.strip();
    }

}

