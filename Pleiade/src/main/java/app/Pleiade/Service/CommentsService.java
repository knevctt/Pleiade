package app.Pleiade.Service;

import app.Pleiade.Repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {

    @Autowired
    private CommentsRepository commentsRepository;


}
