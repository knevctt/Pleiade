package app.Pleiade.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PdfData")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
    public class PdfData
    {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String type;

        @Lob
        private byte[] pdfData;

    }

