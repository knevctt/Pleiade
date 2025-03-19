package app.Pleiade.Dto;

import app.Pleiade.Entity.Enum.Genero;
import app.Pleiade.Entity.ImageData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String imageDatas;
    private String synopsis;
    private ImageData imageData;
    private List<Genero> generos;
}
