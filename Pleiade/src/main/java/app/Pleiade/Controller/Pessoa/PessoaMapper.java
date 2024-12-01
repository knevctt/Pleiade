package app.Pleiade.Controller.Pessoa;

import app.Pleiade.Entity.PessoasEntity;

public class PessoaMapper {

    public static PessoaResponse toResponse(PessoasEntity pessoa){
        return new PessoaResponse(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getIdade(),
                pessoa.getDocumento()

        );
    }

    public static PessoasEntity toEntityFromRequest(PessoaRequest pessoaRequest){
        return new PessoasEntity(
                null,
                pessoaRequest.nome(),
                pessoaRequest.idade(),
                pessoaRequest.doc(),
                pessoaRequest.senha()
        );
    }

    public static PessoasEntity toEntityFromRequestExisting(PessoaRequestExisting pessoaRequest){
        return new PessoasEntity(
                pessoaRequest.id(),
                pessoaRequest.nome(),
                pessoaRequest.idade(),
                pessoaRequest.doc(),
                pessoaRequest.senha()
        );
    }


}
