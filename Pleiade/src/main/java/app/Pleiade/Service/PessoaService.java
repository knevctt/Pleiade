package app.Pleiade.Service;

import app.Pleiade.Entity.PessoasEntity;
import app.Pleiade.Repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;


    public PessoasEntity findPessoaById(Long idPessoa) throws UserPrincipalNotFoundException {
        return pessoaRepository.findById(idPessoa).orElseThrow(
                ()-> new UserPrincipalNotFoundException("user not found")
        );
    }

    public List<PessoasEntity> findAllPessoa(){
        return pessoaRepository.findAll();
    }

    public PessoasEntity createPessoa(PessoasEntity pessoasEntity){
        pessoasEntity.setId(null);
        return pessoaRepository.save(
                pessoasEntity
        );
    }

    public void deletePessoa(Long idPessoa) throws UserPrincipalNotFoundException {
        try{
            pessoaRepository.deleteById(idPessoa);
        }catch (Exception e){
            throw new UserPrincipalNotFoundException("user not found");
        }
    }

    public void atualizarPessoa(PessoasEntity pessoasEntity){
        pessoaRepository.save(pessoasEntity);
    }

    private Boolean credentialMatch(PessoasEntity pessoasEntity, String login, String senha){
        if(pessoasEntity.getDocumento().equalsIgnoreCase(login)
                && pessoasEntity.getSenha().equalsIgnoreCase(senha)){
            return true;
        }else {
            return false;
        }
    }
}
