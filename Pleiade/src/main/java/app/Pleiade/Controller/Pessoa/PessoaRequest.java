package app.Pleiade.Controller.Pessoa;

import java.util.List;

public record PessoaRequest(
        String nome,
        int idade,
        String doc,
        List<Long> roles,
        String senha
) {
}
