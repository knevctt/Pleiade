package app.Pleiade.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PdfData")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PdfData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    @Lob
    @Column(name = "pdf_data", columnDefinition = "LONGBLOB")
    private byte[] pdfData;

    @OneToOne(mappedBy = "pdfData")
    private Book book;
}

