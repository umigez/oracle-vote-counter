package gezer.votecounter.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public final class CandidatesReader {
    private CandidatesReader() {
    }

    /**
     *
     * @param inputStream this stream will be closed when this method returns.
     * @return The candidates as a list of strings.
     * @throws IOException if any errors while reading the stream.
     */
    public static List<String> readCandidates(InputStream inputStream) throws IOException {
        // use default char set - better to be specific in real code
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            List<String> candidates = new ArrayList<>();
            while (reader.ready()) {
                String line = reader.readLine().trim();
                if (!line.isEmpty()) {
                    candidates.add(line);
                }
            }

            return candidates;
        }
    }
}
