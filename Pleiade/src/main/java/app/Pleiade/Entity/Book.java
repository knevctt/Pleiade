package app.Pleiade.Entity;

import app.Pleiade.Enum.Genero;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String author;

    @Lob
    @Column(columnDefinition = "LONGTEXT") // Para grandes quantidades de texto
    private String imageDatas;

    @Lob
    @Column(columnDefinition = "LONGTEXT") // Para grandes quantidades de texto
    private String pdfDatas;

    @Lob
    @Column(name = "synopsis", columnDefinition = "TEXT")
    private String synopsis;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pdf_id", referencedColumnName = "id")
    private PdfData pdfData;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private ImageData imageData;

    @ElementCollection(targetClass = Genero.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "book_generos")
    @Column(name = "genero")
    private List<Genero> generos;
}
