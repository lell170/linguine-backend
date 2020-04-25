package org.lell.accent.controller;

import org.lell.accent.model.Book;
import org.lell.accent.service.BookService;
import org.lell.accent.service.FileService;
import org.lell.accent.service.VocabularyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
/* ++++++++++++++++++++++++++++
WIP. This controller is not used yet!
+++++++++++++++++++++++++++++++ */
@RestController
@RequestMapping(path = "/api/book", produces = "application/json")
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

    @GetMapping("/all")
    public ResponseEntity<List<Book>> allBooks() {
        return new ResponseEntity<>(bookService.getAllBooksForGui(), HttpStatus.OK);
    }

    @PostMapping(path = "/upload")
    public void uploadBook(@RequestParam("file") final MultipartFile multipartFile) throws IOException {
        final String bookName = multipartFile.getOriginalFilename();
        logger.info("file received {} ", bookName);

        final File file = fileService.createFileFromMultipartData(multipartFile, TMP_FILE_NAME);
        final Book book = bookService.createBook(bookName, file);
        vocabularyService.addNewWordsFromBook(book);
    }
}
