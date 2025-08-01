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
        // 1. 生成50条随机投注（每条记录只包含一个投注）
//        List<OptimalBetRecord> bets = generateRandomBets(50);
        List<OptimalBetRecord> bets = new ArrayList<>();
        bets.add(new OptimalBetRecord("3", 1, OptimalBetRecord.BetType.BIG, 200f, 1.96f));
        bets.add(new OptimalBetRecord("3", 1, OptimalBetRecord.BetType.EVEN, 200f, 1.96f));
        bets.add(new OptimalBetRecord("3", 2, OptimalBetRecord.BetType.SMALL, 200f, 1.96f));
        bets.add(new OptimalBetRecord("3", 3, OptimalBetRecord.BetType.ODD, 200f, 1.96f));
        bets.add(new OptimalBetRecord("3", 3, OptimalBetRecord.BetType.SMALL, 200f, 1.96f));
        bets.add(new OptimalBetRecord("3", 4, OptimalBetRecord.BetType.EVEN, 200f, 1.96f));
        bets.add(new OptimalBetRecord("3", 4, OptimalBetRecord.BetType.BIG, 200f, 1.96f));
        bets.add(new OptimalBetRecord("3", 5, OptimalBetRecord.BetType.BIG, 200f, 1.96f));
        bets.add(new OptimalBetRecord("3", 6, OptimalBetRecord.BetType.EVEN, 200f, 1.96f));
        bets.add(new OptimalBetRecord("3", 7, OptimalBetRecord.BetType.BIG, 200f, 1.96f));
        bets.add(new OptimalBetRecord("3", 7, OptimalBetRecord.BetType.EVEN, 200f, 1.96f));
        bets.add(new OptimalBetRecord("3", 8, OptimalBetRecord.BetType.ODD, 200f, 1.96f));
        bets.add(new OptimalBetRecord("3", 9, OptimalBetRecord.BetType.ODD, 200f, 1.96f));
        bets.add(new OptimalBetRecord("3", 10, OptimalBetRecord.BetType.ODD, 200f, 1.96f));
        bets.add(new OptimalBetRecord("3", OptimalBetRecord.BetType.RUNNER_UP_NUMBER, 6, 200f, 9.80f));
        bets.add(new OptimalBetRecord("3", OptimalBetRecord.BetType.THIRD_PLACE_NUMBER, 2, 200f, 9.80f));
        bets.add(new OptimalBetRecord("3", OptimalBetRecord.BetType.FOURTH_PLACE_NUMBER, 8, 200f, 9.80f));
        bets.add(new OptimalBetRecord("3", OptimalBetRecord.BetType.FIFTH_PLACE_NUMBER, 2, 200f, 9.80f));
        bets.add(new OptimalBetRecord("3", OptimalBetRecord.BetType.SIXTH_PLACE_NUMBER, 4, 200f, 9.80f));
        bets.add(new OptimalBetRecord("3", OptimalBetRecord.BetType.EIGHTH_PLACE_NUMBER, 5, 200f, 9.80f));
//        bets.add(new OptimalBetRecord("3", 2, OptimalBetRecord.BetType.ODD, 300f, 1.96f));
//        bets.add(new OptimalBetRecord("3", 1, OptimalBetRecord.BetType.EVEN, 300f, 1.96f));
//        bets.add(new OptimalBetRecord("3", 2, OptimalBetRecord.BetType.SMALL, 400f, 1.96f));
////        bets.add(new OptimalBetRecord("3", 1, OptimalBetRecord.BetType.BIG, 100f, 1.96f));
////        bets.add(new OptimalBetRecord("3", 4, OptimalBetRecord.BetType.BIG, 100f, 1.96f));
////        bets.add(new OptimalBetRecord("3", 5, OptimalBetRecord.BetType.BIG, 100f, 1.96f));
//        bets.add(new OptimalBetRecord("3", OptimalBetRecord.BetType.CHAMPION_NUMBER, 1, 100f, 9.80f));
//        bets.add(new OptimalBetRecord("3", OptimalBetRecord.BetType.THIRD_PLACE_NUMBER, 4, 200f, 9.80f));
//        bets.add(new OptimalBetRecord("3", OptimalBetRecord.BetType.FIFTH_PLACE_NUMBER, 6, 200f, 9.80f));
//        bets.add(new OptimalBetRecord("3", OptimalBetRecord.BetType.SEVENTH_PLACE_NUMBER, 7, 200f, 9.80f));
//        bets.add(new OptimalBetRecord("3", OptimalBetRecord.BetType.NINTH_PLACE_NUMBER, 8, 200f, 9.80f));
//        bets.add(new OptimalBetRecord("3", OptimalBetRecord.BetType.TENTH_PLACE_NUMBER, 10, 200f, 9.80f));

        // 2. 打印所有投注信息
        printAllBets(bets);

        // 3. 计算总投注金额
        Float totalBetAmount = calculateTotalBetAmount(bets);
        System.out.printf("\n所有员工总投注金额: %.2f元\n", totalBetAmount);

        // 4. 生成最优排序
        int[] ranking = RaceRankingUtil.generateOptimalRanking(bets,60f);
        printRanking(ranking);

        // 5. 计算赔付详情
        Map<String, Float> payouts = RaceRankingUtil.getPayoutDetails(bets, ranking);
        printPayouts(payouts);

        // 6. 财务统计
        printFinancialSummary(totalBetAmount, payouts);
    }

    private static List<OptimalBetRecord> generateRandomBets(int count) {
        List<OptimalBetRecord> bets = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String employee = EMPLOYEE_NAMES[random.nextInt(EMPLOYEE_NAMES.length)] + (i+1);
            bets.add(generateSingleBet(employee));
        }
        return bets;
    }

    private static OptimalBetRecord generateSingleBet(String employee) {
        // 随机决定是属性投注还是号码预测
        if (random.nextBoolean()) {
            // 属性投注
            int position = random.nextInt(10) + 1; // 1-10名
            OptimalBetRecord.BetType type = OptimalBetRecord.BetType.values()[
                    random.nextInt(4)]; // SMALL/BIG/ODD/EVEN
            Float amount = round(random.nextFloat() * 100 + 10); // 10-110元
            Float rate = 1.98f; // 1-3倍
            return new OptimalBetRecord(employee, position, type, amount, rate);
        } else {
            // 号码预测
            OptimalBetRecord.BetType type = OptimalBetRecord.BetType.values()[
                    random.nextInt(10) + 4]; // CHAMPION_NUMBER到TENTH_PLACE_NUMBER
            int number = random.nextInt(10) + 1;
            Float amount = round(random.nextFloat() * 100 + 10); // 10-110元
            Float rate = 9.8f; // 5-10倍
            return new OptimalBetRecord(employee, type, number, amount, rate);
        }
    }

    private static Float round(Float value) {
        return Math.round(value * 100) / 100.0f; // 保留两位小数
    }

    private static void printAllBets(List<OptimalBetRecord> bets) {
        System.out.println("=== 所有投注信息（共" + bets.size() + "条） ===");
        for (int i = 0; i < bets.size(); i++) {
            System.out.printf("%d. %s\n", i+1, bets.get(i).toString());
        }
    }

    private static Float calculateTotalBetAmount(List<OptimalBetRecord> bets) {
        return bets.stream()
                .map(OptimalBetRecord::getBetAmount)
                .reduce(0f, Float::sum);
    }

    private static void printRanking(int[] ranking) {
        System.out.println("\n=== 赛车最终排序 ===");
        System.out.println("冠军: " + ranking[0] + "号车");
        System.out.println("亚军: " + ranking[1] + "号车");
        System.out.println("季军: " + ranking[2] + "号车");
        System.out.println("4-10名: " +
                Arrays.toString(Arrays.copyOfRange(ranking, 3, ranking.length)));
    }

    private static void printPayouts(Map<String, Float> payouts) {
        System.out.println("\n=== 赔付详情 ===");
        payouts.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.printf("%s: %.2f元\n",
                        entry.getKey(), entry.getValue()));
    }

    private static void printFinancialSummary(Float totalBet, Map<String, Float> payouts) {
        Float totalPayout = payouts.values().stream().reduce(0f, Float::sum);
        System.out.printf("\n=== 财务统计 ===\n");
        System.out.printf("总投注金额: %.2f元\n", totalBet);
        System.out.printf("总赔付金额: %.2f元\n", totalPayout);
        System.out.printf("公司净收益: %.2f元\n", totalBet - totalPayout);
    }
}