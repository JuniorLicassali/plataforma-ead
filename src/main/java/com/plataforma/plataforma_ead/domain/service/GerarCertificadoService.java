package com.plataforma.plataforma_ead.domain.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.plataforma.plataforma_ead.domain.exception.MatriculaNaoEncontradaException;
import com.plataforma.plataforma_ead.domain.exception.NegocioException;
import com.plataforma.plataforma_ead.domain.model.Matricula;
import com.plataforma.plataforma_ead.domain.model.QuestionarioUsuario;
import com.plataforma.plataforma_ead.domain.model.StatusMatricula;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GerarCertificadoService {
	
	private final MatriculaService matriculaService;
	
	private static final double NOTA_MINIMA = 7.0;
    private static final String BACKGROUND_PATH = "Certificado.png";
    
    public byte[] gerarCertificado(Long matriculaId) throws Exception {
        Matricula matricula = validarMatricula(matriculaId);
        DadosCertificado dados = extrairDadosCertificado(matricula);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdf = criarDocumentoPDF(baos);
        PdfCanvas canvas = new PdfCanvas(pdf.addNewPage());
        float pageWidth = PageSize.A4.rotate().getWidth();
        float pageHeight = PageSize.A4.rotate().getHeight();
        
        adicionarImagemFundo(canvas, pageWidth, pageHeight);
        adicionarConteudoCertificado(canvas, pageWidth, dados);
        pdf.close();
        
        byte[] pdfBytes = baos.toByteArray();
        
        return pdfBytes;
    }
    
    private Matricula validarMatricula(Long matriculaId) {
        Matricula matricula = matriculaService.buscarOuFalhar(matriculaId);
        if (matricula == null) {
            throw new MatriculaNaoEncontradaException("Matrícula não encontrada com ID: " + matriculaId);
        }

        if (matricula.getStatusMatricula() != StatusMatricula.PAGAMENTO_CONFIRMADO) {
            throw new NegocioException("A matrícula não está ativa");
        }

        QuestionarioUsuario questionarioUsuario = matricula.getQuestionarioUsuario();
        if (questionarioUsuario == null || questionarioUsuario.getNota() == null) {
            throw new NegocioException("Nota não encontrada para esta matrícula");
        }

        if (questionarioUsuario.getNota() < NOTA_MINIMA) {
            throw new NegocioException("Nota média insuficiente para emitir certificado: " + questionarioUsuario.getNota());
        }

        return matricula;
    }
    
    private DadosCertificado extrairDadosCertificado(Matricula matricula) {
        String nomeUsuario = matricula.getUsuario().getNome();
        String nomeCurso = matricula.getCurso().getNome();
        int cargaHoraria = 64; // Pode ser dinâmico: matricula.getCurso().getCargaHoraria();
        String textoPrincipal = "Certificamos que o aluno " + nomeUsuario.toUpperCase() +
                " concluiu o curso de " + nomeCurso.toLowerCase() +
                " da Tal Tal, no total de " + cargaHoraria + " horas.";
        return new DadosCertificado(nomeUsuario, nomeCurso, cargaHoraria, textoPrincipal);
    }

    private PdfDocument criarDocumentoPDF(ByteArrayOutputStream baos) throws Exception {
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        pdf.setDefaultPageSize(PageSize.A4.rotate());
        return pdf;
    }
    
    private void adicionarImagemFundo(PdfCanvas canvas, float pageWidth, float pageHeight) throws Exception {
        ClassPathResource backgroundResource = new ClassPathResource(BACKGROUND_PATH);
        if (!backgroundResource.exists()) {
            throw new NegocioException("Imagem de fundo não encontrada no classpath: " + BACKGROUND_PATH);
        }

        ImageData imageData = ImageDataFactory.create(backgroundResource.getInputStream().readAllBytes());
        float imageWidth = imageData.getWidth();
        float imageHeight = imageData.getHeight();

        float scale = Math.min(pageWidth / imageWidth, pageHeight / imageHeight);
        float scaledWidth = imageWidth * scale;
        float scaledHeight = imageHeight * scale;

        float x = (pageWidth - scaledWidth) / 2;
        float y = (pageHeight - scaledHeight) / 2;

        canvas.addImageWithTransformationMatrix(imageData, scaledWidth, 0, 0, scaledHeight, x, y);
    }

    private void adicionarConteudoCertificado(PdfCanvas canvas, float pageWidth, DadosCertificado dados) throws Exception {
        PdfFont font = PdfFontFactory.createFont();
        PdfFont boldFont = PdfFontFactory.createFont();
        float centerX = pageWidth / 2;

        drawCenteredText(canvas, font, 18, "CURSO DE " + dados.nomeCurso().toUpperCase(), centerX, 410);
        drawCenteredText(canvas, boldFont, 20, dados.nomeUsuario().toUpperCase(), centerX, 330);

        float textAreaWidth = 500;
        float textAreaX = (pageWidth - textAreaWidth) / 2;
        float textAreaY = 270;
        drawWrappedText(canvas, font, 14, dados.textoPrincipal(), textAreaX, textAreaY, textAreaWidth);
    }
    
    private void drawCenteredText(PdfCanvas canvas, PdfFont font, float fontSize, String text, float x, float y) throws Exception {
        canvas.beginText();
        canvas.setFontAndSize(font, fontSize);
        float textWidth = font.getWidth(text, fontSize);
        float adjustedX = x - (textWidth / 2);
        canvas.setTextMatrix(adjustedX, y);
        canvas.showText(text);
        canvas.endText();
    }

    private void drawWrappedText(PdfCanvas canvas, PdfFont font, float fontSize, String text, float x, float y, float width) throws Exception {
        Rectangle textArea = new Rectangle(x, y - 100, width, 100);
        Canvas layoutCanvas = new Canvas(canvas, textArea);
        Paragraph paragraph = new Paragraph(text)
                .setFont(font)
                .setFontSize(fontSize)
                .setTextAlignment(TextAlignment.CENTER);
        layoutCanvas.add(paragraph);
        layoutCanvas.close();
    }
    
    private record DadosCertificado(String nomeUsuario, String nomeCurso, int cargaHoraria, String textoPrincipal) {
    }

}
