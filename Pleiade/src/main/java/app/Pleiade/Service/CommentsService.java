package app.Pleiade.Service;

import app.Pleiade.Entity.Comments;
import app.Pleiade.Entity.User;
import app.Pleiade.Repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

    public String save(Comments comments){
        this.commentsRepository.save(comments);
        return "comments saved sucessfully";
    }

    public String delete(Comments comments){
         this.commentsRepository.delete(comments);
         return "comments deleted sucessfully";
    }
    public List<Comments> getAllComments(){
        return this.commentsRepository.findAll();
    }
    public String UpdateComment(Comments comments){
        this.commentsRepository.save(comments);
        return "comments updated sucessfully";
    }
}
