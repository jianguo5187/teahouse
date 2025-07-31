package com.ruoyi.common.utils;

import com.ruoyi.common.core.vo.OptimalBetRecord;
import java.util.*;
import java.util.stream.IntStream;

public final class RaceRankingUtil {
    private RaceRankingUtil() {}

    private static final Random random = new Random();
    private static final int TOTAL_PERMUTATIONS = 3628800; // 10!

    /**
     * 生成满足盈利目标的赛车排序
     * @param bets 所有投注记录
     * @param profitTarget 盈利目标(0-100)
     *        -1: 全赔（赔付最多，公司亏损最大）
     *        0: 赔付最多（公司亏损最大）
     *        100: 通吃（赔付最少）
     *        其他值: 最接近该盈利比例的排序，优先匹配高于目标的
     * @return 最优赛车排序
     */
    public static int[] generateOptimalRanking(List<OptimalBetRecord> bets, float profitTarget) {
        // 处理全赔特殊情况
        if (profitTarget == -1) {
            return findExtremeRanking(bets, false); // 最大赔付
        }

        float totalBetAmount = calculateTotalBetAmount(bets);
        float targetRatio = profitTarget / 100f;

        int[] bestRanking = null;
        float bestProfitDiff = Float.MAX_VALUE;
        float bestProfitRatio = -1f;
        boolean foundAboveTarget = false;

        // 主循环尝试所有可能排列
        for (int i = 0; i < TOTAL_PERMUTATIONS; i++) {
            int[] ranking = generateRandomRanking();
            float payout = calculateTotalPayout(bets, ranking);
            float profitRatio = (totalBetAmount - payout) / totalBetAmount;

            // 计算与目标的差异
            float currentDiff = profitRatio - targetRatio;
            float absDiff = Math.abs(currentDiff);

            // 优先匹配高于目标的
            if (!foundAboveTarget) {
                if (currentDiff >= 0) { // 高于或等于目标
                    if (absDiff < bestProfitDiff) {
                        bestProfitDiff = absDiff;
                        bestProfitRatio = profitRatio;
                        bestRanking = ranking;
                        foundAboveTarget = true;
                    }
                } else { // 低于目标
                    if (bestProfitRatio < targetRatio && absDiff < bestProfitDiff) {
                        bestProfitDiff = absDiff;
                        bestProfitRatio = profitRatio;
                        bestRanking = ranking;
                    }
                }
            } else {
                // 已经找到高于目标的，只考虑高于目标的情况
                if (currentDiff >= 0 && absDiff < bestProfitDiff) {
                    bestProfitDiff = absDiff;
                    bestProfitRatio = profitRatio;
                    bestRanking = ranking;
                }
            }

            // 找到精确匹配时提前返回
            if (absDiff < 0.0001f) break;
        }

        System.out.printf("实际盈利比例: %.2f%% (目标: %.2f%%)\n",
                bestProfitRatio * 100, profitTarget);
        return bestRanking != null ? bestRanking : generateRandomRanking();
    }

    /**
     * 生成随机排序
     */
    private static int[] generateRandomRanking() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        Collections.shuffle(numbers);
        return numbers.stream().mapToInt(n -> n).toArray();
    }

    /**
     * 查找极端情况（最大/最小赔付）
     */
    private static int[] findExtremeRanking(List<OptimalBetRecord> bets, boolean findMin) {
        int[] extremeRanking = null;
        float extremePayout = findMin ? Float.MAX_VALUE : -Float.MAX_VALUE;

        for (int i = 0; i < TOTAL_PERMUTATIONS; i++) {
            int[] ranking = generateRandomRanking();
            float payout = calculateTotalPayout(bets, ranking);

            if ((findMin && payout < extremePayout) || (!findMin && payout > extremePayout)) {
                extremePayout = payout;
                extremeRanking = ranking;
            }
        }
        return extremeRanking;
    }

    /**
     * 计算总投注金额
     */
    public static float calculateTotalBetAmount(List<OptimalBetRecord> bets) {
        return bets.stream()
                .map(OptimalBetRecord::getBetAmount)
                .reduce(0f, Float::sum);
    }

    /**
     * 计算总赔付金额
     */
    public static float calculateTotalPayout(List<OptimalBetRecord> bets, int[] ranking) {
        return bets.stream()
                .map(bet -> bet.calculatePayout(ranking))
                .reduce(0f, Float::sum);
    }

    /**
     * 获取赔付详情
     */
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