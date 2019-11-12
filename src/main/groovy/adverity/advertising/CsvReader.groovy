package adverity.advertising

class CsvReader {
    private final File file

    CsvReader(File file) {
        this.file = file
    }

    def parse() {
        def data = file.readLines()
                       .drop(1)
                       .collect { it }
        return data;
    }
}
