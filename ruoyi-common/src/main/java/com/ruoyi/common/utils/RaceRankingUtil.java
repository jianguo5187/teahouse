package com.ruoyi.common.utils;

import com.ruoyi.common.core.vo.OptimalBetRecord;
import java.util.*;

public final class RaceRankingUtil {
    private RaceRankingUtil() {}

    private static final Random random = new Random();
    private static final int ATTEMPT_COUNT = 10000;

    public static int[] generateOptimalRanking(List<OptimalBetRecord> bets) {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        int[] bestRanking = null;
        Float minPayout = Float.MAX_VALUE;

        for (int i = 0; i < ATTEMPT_COUNT; i++) {
            Collections.shuffle(numbers);
            int[] ranking = numbers.stream().mapToInt(n -> n).toArray();
            Float payout = calculateTotalPayout(bets, ranking);

            if (payout < minPayout) {
                minPayout = payout;
                bestRanking = ranking.clone();
            }
        }

        return bestRanking != null ? bestRanking :
                numbers.stream().mapToInt(n -> n).toArray();
    }

    public static Float calculateTotalPayout(List<OptimalBetRecord> bets, int[] ranking) {
        return bets.stream()
                .map(bet -> bet.calculatePayout(ranking))
                .reduce(0f, Float::sum);
    }

    public static Map<String, Float> getPayoutDetails(List<OptimalBetRecord> bets, int[] ranking) {
        Map<String, Float> details = new LinkedHashMap<>();
        for (OptimalBetRecord bet : bets) {
            details.merge(bet.getEmployee(),
                    bet.calculatePayout(ranking),
                    Float::sum);
        }
        return details;
    }
}