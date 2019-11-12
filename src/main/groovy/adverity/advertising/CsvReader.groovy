package adverity.advertising

import java.nio.file.Files

class CsvReader {
    private final File file

    CsvReader(File file) {
        this.file = file
    }

    def parse() {
        def data = file.readLines().collect { it }
        return data;
    }
}
