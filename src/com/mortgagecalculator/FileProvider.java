package com.mortgagecalculator;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class FileProvider {
    private static final String FILE_NAME = "mortgage - report";
    private static final String FILE_EXTENSION = ".csv";

    public static File getFile() {
        String rootPath = System.getProperty("user.dir");
        return new File(rootPath + "/" + FILE_NAME + "-" + LocalDateTime.now().toString().replace(":", "") + FILE_EXTENSION);
    }
}
