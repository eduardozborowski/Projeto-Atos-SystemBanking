package br.com.atm.dto;

import br.com.atm.entity.UserAtm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAtmDTO {
    private Long id;
    private String username;
    private String login;
    private String cpf;

    public static UserAtmDTO convert(UserAtm userAtm){
        UserAtmDTO userAtmDTO = new UserAtmDTO();
        userAtmDTO.setId(userAtm.getId());
        userAtmDTO.setUsername(userAtm.getUsername());
        userAtmDTO.setLogin(userAtm.getLogin());

        return userAtmDTO;
    }
}
