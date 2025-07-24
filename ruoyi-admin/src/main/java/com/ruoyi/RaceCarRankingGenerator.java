package com.ruoyi;
import java.util.*;
import java.util.stream.IntStream;

public class RaceCarRankingGenerator {

    // 记录每个名次上的选择次数 [position][option]
    // position 0-9 (对应1-10名)
    // option 0:小(1-5), 1:大(6-10), 2:单, 3:双
    private int[][] choiceCounts;
    private Random random;

    public RaceCarRankingGenerator() {
        this.choiceCounts = new int[10][4];
        this.random = new Random();
    }

    // 记录选择
    // position: 0=冠军, 1=亚军,..., 9=第10名
    // optionType: 0=大小, 1=单双
    // optionValue: 对于大小选项: 0=小, 1=大; 对于单双选项: 0=单, 1=双
    public void recordChoice(int position, int optionType, int optionValue) {
        if (position < 0 || position >= 10) return;
        int option = optionType * 2 + optionValue;
        if (option >= 0 && option < 4) {
            choiceCounts[position][option]++;
        }
    }

    // 记录具体号码的选择
    public void recordNumberChoice(String user, int number, int position) {
        if (number < 1 || number > 10 || position < 0 || position >= 10) return;

        boolean isSmall = number <= 5;
        boolean isOdd = number % 2 == 1;

        // 打印用户选择
        System.out.printf("%s选择%d号作为第%d名(小=%b, 双=%b)%n",
                user, number, position+1, isSmall, !isOdd);

        // 记录大小选择
        recordChoice(position, 0, isSmall ? 0 : 1);
        // 记录单双选择
        recordChoice(position, 1, isOdd ? 0 : 1);
    }

    // 生成最少被选择的号码组合
    public int[] generateRanking() {
        List<Integer> remainingNumbers = new ArrayList<>();
        IntStream.rangeClosed(1, 10).forEach(remainingNumbers::add);
        Collections.shuffle(remainingNumbers, random);

        int[] ranking = new int[10];
        boolean[] used = new boolean[11]; // 1-10

        for (int pos = 0; pos < 10; pos++) {
            // 获取当前名次的所有选项统计
            int smallCount = choiceCounts[pos][0];   // 小
            int bigCount = choiceCounts[pos][1];     // 大
            int oddCount = choiceCounts[pos][2];     // 单
            int evenCount = choiceCounts[pos][3];    // 双

            // 确定优先选择的属性组合
            boolean preferSmall = smallCount <= bigCount;
            boolean preferOdd = oddCount <= evenCount;

            // 尝试优先选择的数字
            Integer selected = findNumber(remainingNumbers, used, preferSmall, preferOdd);

            // 如果首选没有可用数字，尝试其他组合
            if (selected == null) {
                selected = findNumber(remainingNumbers, used, preferSmall, !preferOdd);
            }
            if (selected == null) {
                selected = findNumber(remainingNumbers, used, !preferSmall, preferOdd);
            }
            if (selected == null) {
                selected = findNumber(remainingNumbers, used, !preferSmall, !preferOdd);
            }

            // 如果还是没有，就从剩余数字中选
            if (selected == null && !remainingNumbers.isEmpty()) {
                selected = remainingNumbers.get(0);
            }

            if (selected != null) {
                ranking[pos] = selected;
                used[selected] = true;
                remainingNumbers.remove(selected);
            }
        }

        return ranking;
    }

    // 根据偏好查找可用数字
    private Integer findNumber(List<Integer> numbers, boolean[] used,
                               boolean preferSmall, boolean preferOdd) {
        for (int num : numbers) {
            boolean isSmall = num <= 5;
            boolean isOdd = num % 2 == 1;
            if (!used[num] && isSmall == preferSmall && isOdd == preferOdd) {
                return num;
            }
        }
        return null;
    }

    // 生成随机用户名
    private String randomUserName(Random random) {
        String[] names = {"张三", "李四", "王五", "赵六", "钱七", "孙八", "周九", "吴十"};
        return names[random.nextInt(names.length)];
    }

    // 示例用法
    public static void main(String[] args) {
        RaceCarRankingGenerator generator = new RaceCarRankingGenerator();
        Random testRandom = new Random();

        System.out.println("===== 测试数据开始 =====");
        // 随机生成50条测试数据
        for (int i = 0; i < 50; i++) {
            String user = generator.randomUserName(testRandom);
            int number = testRandom.nextInt(10) + 1; // 1-10
            int position = testRandom.nextInt(10);   // 0-9

            generator.recordNumberChoice(user, number, position);
        }
        System.out.println("===== 测试数据结束 =====\n");

        // 生成排序
        int[] ranking = generator.generateRanking();
        System.out.println("生成的赛车排序: " + Arrays.toString(ranking));

        // 查看选择统计数据
        System.out.println("\n选择统计数据:");
        String[] optionNames = {"小(1-5)", "大(6-10)", "单", "双"};
        for (int i = 0; i < 10; i++) {
            System.out.printf("第%d名: ", i+1);
            for (int j = 0; j < 4; j++) {
                System.out.printf("%s=%d ", optionNames[j], generator.choiceCounts[i][j]);
            }
            System.out.println();
        }
    }
}