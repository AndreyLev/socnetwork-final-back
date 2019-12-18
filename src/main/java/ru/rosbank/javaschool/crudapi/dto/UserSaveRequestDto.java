package ru.rosbank.javaschool.crudapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveRequestDto {

    @Min(value = 0, message = "error.validation.value")
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String username;
    @NotNull
    @Size(min = 6, message = "error.validation.min_size")
    private String password;
    @NotNull
    @Email(message = "error.validation.email")
    private String email;

}
