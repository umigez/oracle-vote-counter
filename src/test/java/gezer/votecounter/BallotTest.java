package gezer.votecounter;

import gezer.votecounter.exceptions.InvalidBallot;
import org.junit.Test;

public class BallotTest {
    private static final String A = "A";
    private static final String B = "B";
    private static final String C = "C";
    private static final String D = "D";

    @Test(expected = InvalidBallot.class)
    public void empty() throws InvalidBallot {
        Ballot.from();
    }

    @Test(expected = InvalidBallot.class)
    public void doubleVote() throws InvalidBallot {
        Ballot.from(A, B, B, D);
    }

    @Test(expected = InvalidBallot.class)
    public void singleEmptyVote() throws InvalidBallot {
        Ballot.from("", B, C, D);
    }

}