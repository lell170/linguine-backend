package org.lell.linguine.service;

import com.google.common.collect.Streams;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.lell.linguine.model.Book;
import org.lell.linguine.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private final BookRepository bookRepository;

    public BookService(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public String getContentOfBook(final File file) {
        final List<String> words = new ArrayList<>();
        try {
            final PdfReader pdfReader = new PdfReader(file.getAbsolutePath());
            final int n = pdfReader.getNumberOfPages();
            for (int i = 1; i <= n; i++) {
                final String textFromPage = PdfTextExtractor.getTextFromPage(pdfReader, i);
                words.addAll(Arrays.asList(textFromPage.split("\\s+")));
            }
            pdfReader.close();
        } catch (IOException e) {
            logger.error("error occurred while reading words from pdf file");
        }
        return String.join(" ", words);
    }

    public Book createBook(final String name, final File file) {

        // create book
        final Book book = new Book();
        book.setName(name);
        book.setContent(this.getContentOfBook(file));
        book.setImportDate(LocalDateTime.now());
        // save book
        bookRepository.save(book);
        logger.info("book {} saved", book.getName());

        return book;
    }

    public List<Book> getAllBooksForGui() {
        final List<Book> originallyAllBooks = Streams.stream(bookRepository.findAll()).collect(Collectors.toList());
        return originallyAllBooks.stream().map(book -> {
            book.setContent("");
            return book;
        }).collect(Collectors.toList());
    }
}
