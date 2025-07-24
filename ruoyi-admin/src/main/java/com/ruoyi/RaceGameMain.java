package com.ruoyi;

import com.ruoyi.common.core.vo.OptimalBetRecord;
import com.ruoyi.common.utils.RaceRankingUtil;

import java.util.*;

public class RaceGameMain {
    private static final String[] EMPLOYEE_NAMES = {
            "张三", "李四", "王五", "赵六", "钱七",
            "孙八", "周九", "吴十", "郑十一", "王十二"
    };
    private static final Random random = new Random();

    public static void main(String[] args) {
        // 1. 生成50条随机投注
        List<OptimalBetRecord> bets = generateRandomBets(50);

        // 2. 打印所有投注信息
        printAllBets(bets);

        // 3. 计算总投注金额
        Float totalBetAmount = calculateTotalBetAmount(bets);
        System.out.printf("\n所有员工总投注金额: %.2f元\n", totalBetAmount);

        // 4. 生成最优排序
        int[] ranking = RaceRankingUtil.generateOptimalRanking(bets);
        System.out.println("\n赛车最终排序: " + Arrays.toString(ranking));

        // 5. 计算赔付详情
        Map<String, Float> payouts = RaceRankingUtil.getPayoutDetails(bets, ranking);
        System.out.println("\n=== 赔付详情 ===");
        payouts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.printf("%s: %.2f元\n", entry.getKey(), entry.getValue()));

        Float totalPayout = payouts.values().stream().reduce(0f, Float::sum);
        System.out.printf("\n公司总赔付金额: %.2f元\n", totalPayout);
        System.out.printf("公司净收益: %.2f元\n", totalBetAmount - totalPayout);
    }

    private static List<OptimalBetRecord> generateRandomBets(int count) {
        List<OptimalBetRecord> bets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String employee = EMPLOYEE_NAMES[random.nextInt(EMPLOYEE_NAMES.length)] + (i+1);
            int position = random.nextInt(10) + 1;

            // 生成带两位小数的随机金额 (0.50-100.00)
            Float smallBet = random.nextBoolean() ? round(random.nextFloat() * 99.5f + 0.5f) : null;
            Float bigBet = random.nextBoolean() ? round(random.nextFloat() * 99.5f + 0.5f) : null;
            Float oddBet = random.nextBoolean() ? round(random.nextFloat() * 99.5f + 0.5f) : null;
            Float evenBet = random.nextBoolean() ? round(random.nextFloat() * 99.5f + 0.5f) : null;

            // 生成带一位小数的赔率 (1.5-3.0)
            Float smallRate = 1.98f;
            Float bigRate = 1.98f;
            Float oddRate = 1.98f;
            Float evenRate = 1.98f;

            bets.add(new OptimalBetRecord(
                    employee, position,
                    smallBet, smallRate,
                    bigBet, bigRate,
                    oddBet, oddRate,
                    evenBet, evenRate
            ));
        }
        return bets;
    }

    private static Float round(Float value) {
        return Math.round(value * 10) / 10.0f; // 保留1位小数
    }

    private static void printAllBets(List<OptimalBetRecord> bets) {
        System.out.println("=== 所有投注信息 ===");
        System.out.println("员工\t名次\t小(赔率)\t大(赔率)\t单(赔率)\t双(赔率)");
        System.out.println("--------------------------------------------------");

        bets.forEach(bet -> {
            System.out.printf("%s\t第%d名\t%s\t%s\t%s\t%s\n",
                    bet.getEmployee(),
                    bet.getPosition(),
                    formatBet(bet.getSmallBet(), bet.getSmallPayoutRate()),
                    formatBet(bet.getBigBet(), bet.getBigPayoutRate()),
                    formatBet(bet.getOddBet(), bet.getOddPayoutRate()),
                    formatBet(bet.getEvenBet(), bet.getEvenPayoutRate())
            );
        });
    }

    private static String formatBet(Float amount, Float rate) {
        return amount != null ? String.format("%.2f元(%.2f倍)", amount, rate) : "未投注";
    }

    private static Float calculateTotalBetAmount(List<OptimalBetRecord> bets) {
        return bets.stream()
                .map(bet ->
                        (bet.getSmallBet() != null ? bet.getSmallBet() : 0f) +
                                (bet.getBigBet() != null ? bet.getBigBet() : 0f) +
                                (bet.getOddBet() != null ? bet.getOddBet() : 0f) +
                                (bet.getEvenBet() != null ? bet.getEvenBet() : 0f)
                )
                .reduce(0f, Float::sum);
    }
}