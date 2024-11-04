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
    @Column(name = "pdf_data", columnDefinition="LONGBLOB", length = 1000)
    private byte[] pdfData;

    @OneToOne(mappedBy = "pdfData")
    private Book book;

    public PdfData(String name, byte[] pdfData) {
        this.name = name;
        this.pdfData = pdfData;
    }
}

