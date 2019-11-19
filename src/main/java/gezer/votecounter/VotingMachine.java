package gezer.votecounter;

import gezer.votecounter.exceptions.InvalidBallot;

import java.util.*;
import java.util.stream.Collectors;

public class VotingMachine {
    private final Map<String, String> initialCandidates;
    private final List<Ballot> ballots = new ArrayList<>();

    private VotingMachine(List<String> candidateNames) {
        // Would use an immutable builder now from Guava
        Map<String, String> candidates = new HashMap<>();
        for (int i = 0; i < candidateNames.size(); i++) {
            String name = candidateNames.get(i);
            candidates.put(toAlphabetic(i), name);
        }

        this.initialCandidates = Collections.unmodifiableMap(candidates);
    }

    public static VotingMachine createInstance(List<String> candidateNames) {
        return new VotingMachine(candidateNames);
    }

    private static String toAlphabetic(int i) {
        if (i < 0) {
            return "-" + toAlphabetic(-i - 1);
        }

        int quot = i / 26;
        int rem = i % 26;
        char letter = (char) ((int) 'A' + rem);
        if (quot == 0) {
            return "" + letter;
        } else {
            return toAlphabetic(quot - 1) + letter;
        }
    }

    public void registerBallot(Ballot ballot) throws InvalidBallot {
        // validate ballot is valid
        for (String vote : ballot) {
            if (vote == null || getInitialCandidates().get(vote) == null) {
                throw new InvalidBallot("Invalid ballot option: " + vote);
            }
        }

        this.ballots.add(ballot);
    }

    public VotingResults tallyVotes() {
        VotingResults r = new VotingResults();

        Map<String, Collection<Ballot>> ballotAllocation = getInitialCandidates().keySet().stream()
                .collect(Collectors.toMap(
                        v -> v,
                        v -> Collections.emptyList()));

        tallySingleRound(ballotAllocation, ballots);

        return r;
    }

    /**
     * @param ballotAllocation   can initially be empty - but must contain all candidates still in the running.
     * @param unallocatedBallots not yet allocated ballots.
     */
    private void tallySingleRound(Map<String, Collection<Ballot>> ballotAllocation, List<Ballot> unallocatedBallots) {
        // Create copy so we can modify
        ballotAllocation = deepCopy(ballotAllocation);

        for (Ballot ballot : unallocatedBallots) {
            // Loop through preference
            for (String vote : ballot) {
                // if the candidate is still in the running, allocate the ballot
                Collection<Ballot> allocatedBallots = ballotAllocation.get(vote);
                if (allocatedBallots != null) {
                    allocatedBallots.add(ballot);
                    break;
                }
            }
        }

        final int numberToWin = (countNumOfBallots(ballotAllocation) / 2) + 1;

        // Did anybody win?
        //TODO
    }

    private static <V> int countNumOfBallots(Map<?, Collection<V>> ballotAllocation) {
        int count = 0;
        for (Collection<V> ballots : ballotAllocation.values()) {
            count += ballots.size();
        }
        return count;
    }

    private static <V> Map<String, Collection<V>> deepCopy(Map<String, Collection<V>> map) {
        return map.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> new ArrayList<V>(e.getValue())
        ));
    }

    public Map<String, String> getInitialCandidates() {
        return initialCandidates;
    }
}
