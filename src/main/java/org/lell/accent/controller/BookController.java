package org.lell.accent.controller;

import org.lell.accent.model.Book;
import org.lell.accent.service.BookService;
import org.lell.accent.service.VocabularyService;
import org.lell.accent.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);
    private final BookService bookService;
    private final FileService fileService;
    private final VocabularyService vocabularyService;
    private static final String TMP_FILE_NAME = "tmpFile";

    public BookController(final VocabularyService vocabularyService, final BookService bookService, final FileService fileService) {
        this.bookService = bookService;
        this.fileService = fileService;
        this.vocabularyService = vocabularyService;
    }

    @GetMapping("/books/all")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Book> allBooks() {
        return bookService.getAllBooksForGui();
    }

    @PostMapping("/uploadFile")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin(origins = "http://localhost:4200")
    public void uploadBook(@RequestParam("file") final MultipartFile multipartFile) throws IOException {
        final String bookName = multipartFile.getOriginalFilename();
        logger.info("file received {} ", bookName);

        final File file = fileService.createFileFromMultipartData(multipartFile, TMP_FILE_NAME);
        final Book book = bookService.createBook(bookName, file);
        vocabularyService.addNewWords(book);
    }
}
