package gezer.votecounter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BallotAllocation {
    Map<String, List<List<String>>> ballotAllocation;

    public BallotAllocation(Set<String> candidates) {
        Map<String, List<List<String>>> ballotAllocation = candidates.stream()
                .collect(Collectors.toMap(
                        v -> v,
                        v -> new ArrayList<>()));
    }
}
