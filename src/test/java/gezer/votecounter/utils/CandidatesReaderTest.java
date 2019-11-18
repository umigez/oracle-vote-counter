package gezer.votecounter.utils;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CandidatesReaderTest {

    @Test
    public void readCandidatesSingle() throws IOException {
        String s = "single";
        InputStream stream = IOUtils.toInputStream(s);
        List<String> candidates = CandidatesReader.readCandidates(stream);

        Assert.assertEquals(1, candidates.size());
        Assert.assertEquals(s, candidates.get(0));
    }

    @Test
    public void readCandidatesFour() throws IOException {
        String s = "1\n2\n3\n4\n";
        InputStream stream = IOUtils.toInputStream(s);
        List<String> candidates = CandidatesReader.readCandidates(stream);

        Assert.assertEquals(4, candidates.size());
        for (int i = 0; i < 4; i++) {
            Assert.assertEquals(String.valueOf(i+1), candidates.get(i));
        }
    }

    @Test
    public void trimWhiteSpace() throws IOException {
        String s = "  \n  1 1   \n   2 2\t   \n3 3  \n  \n   \n    4 4\n";
        InputStream stream = IOUtils.toInputStream(s);
        List<String> candidates = CandidatesReader.readCandidates(stream);

        Assert.assertEquals(4, candidates.size());
        for (int i = 0; i < 4; i++) {
            Assert.assertEquals((i+1) + " " + (i+1), candidates.get(i));
        }
    }

    @Test
    public void ignoreEmptyLine() throws IOException {
        String s = "1\n2\n3\n  \n   \n4\n";
        InputStream stream = IOUtils.toInputStream(s);
        List<String> candidates = CandidatesReader.readCandidates(stream);

        Assert.assertEquals(4, candidates.size());
        for (int i = 0; i < 4; i++) {
            Assert.assertEquals(String.valueOf(i+1), candidates.get(i));
        }
    }
}