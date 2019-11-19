package gezer.votecounter;

import gezer.votecounter.exceptions.InvalidBallot;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class VotingMachineTest {
    private static final String FIRST = "First";
    private static final String SECOND = "Second";
    private static final String THIRD = "Third";
    private static final String FORTH = "Forth";
    private static final String A = "A";
    private static final String B = "B";
    private static final String C = "C";
    private static final String D = "D";

    private static List<String> candidates = new ArrayList<String>() {{
       add(FIRST);
       add(SECOND);
       add(THIRD);
       add(FORTH);
    }};

    private VotingMachine votingMachine;

    @Before
    public void createInstance() {
        votingMachine = VotingMachine.createInstance(candidates);
    }

    @Test
    public void registerVote() throws InvalidBallot {
        Ballot p = Ballot.from(A, B, C, D);
        votingMachine.registerBallot(p);

        //TODO ?
    }

    @Test(expected = InvalidBallot.class)
    public void registerUnknownVote() throws InvalidBallot {
        votingMachine.registerBallot(Ballot.from("Z", B, C, D));
    }

    @Test
    public void tallySingleVote() throws InvalidBallot {
        votingMachine.registerBallot(Ballot.from(A, B, C, D));

        VotingResults votingResults = votingMachine.tallyVotes();

        assertEquals(FIRST, votingResults.getWinner());
    }

    @Test
    public void tallyTwoSameVotes() throws InvalidBallot {
        Ballot p = Ballot.from(A, B, C, D);
        votingMachine.registerBallot(p);
        votingMachine.registerBallot(p);

        VotingResults votingResults = votingMachine.tallyVotes();

        assertEquals(FIRST, votingResults.getWinner());
    }

    @Test
    public void tallyTwoSimpleVotes() throws InvalidBallot {
        votingMachine.registerBallot(Ballot.from(A, B, C, D));
        votingMachine.registerBallot(Ballot.from(A, B));

        VotingResults votingResults = votingMachine.tallyVotes();

        assertEquals(FIRST, votingResults.getWinner());
    }

    @Test
    public void tallyComplexVote() throws InvalidBallot {
        votingMachine.registerBallot(Ballot.from(A, B, D, C));
        votingMachine.registerBallot(Ballot.from(B, A, D));
        votingMachine.registerBallot(Ballot.from(C, A, B, D));
        votingMachine.registerBallot(Ballot.from(C, D, B, A));

        votingMachine.registerBallot(Ballot.from(D, A));
        votingMachine.registerBallot(Ballot.from(D, B));
        votingMachine.registerBallot(Ballot.from(B, A, C));
        votingMachine.registerBallot(Ballot.from(C, B, A, D));

        VotingResults votingResults = votingMachine.tallyVotes();

        assertEquals(SECOND, votingResults.getWinner());
    }

    @Test
    public void testCandidateList() {
        Map<String, String> candidates = votingMachine.getInitialCandidates();
        assertEquals(4, candidates.size());

        assertEquals(FIRST, candidates.get(A));
        assertEquals(SECOND, candidates.get(B));
        assertEquals(THIRD, candidates.get(C));
        assertEquals(FORTH, candidates.get(D));
    }

    @Test
    public void testCandidateListLong() {
        List<String> c = new ArrayList<>();
        for (int i = 0; i < 53; i++) {
            c.add(String.valueOf(i));
        }

        VotingMachine v = VotingMachine.createInstance(c);

        Map<String, String> candidates = v.getInitialCandidates();
        assertEquals(53, candidates.size());

        assertEquals("0", candidates.get(A));
        assertEquals("25", candidates.get("Z"));
        assertEquals("26", candidates.get("AA"));
        assertEquals("49", candidates.get("AX"));
        assertEquals("51", candidates.get("AZ"));
        assertEquals("52", candidates.get("BA"));
    }
}