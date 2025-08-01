package com.ruoyi.common.core.vo;

import java.util.Objects;

/**
 * 投注记录类（每个记录只包含一个投注选项）
 */
public class OptimalBetRecord {
    public enum BetType {
        SMALL,   // 小(1-5)
        BIG,     // 大(6-10)
        ODD,     // 单
        EVEN,    // 双
        CHAMPION_NUMBER,    // 冠军号码
        RUNNER_UP_NUMBER,   // 亚军号码
        THIRD_PLACE_NUMBER,  // 季军号码
        FOURTH_PLACE_NUMBER, // 第四名号码
        FIFTH_PLACE_NUMBER,  // 第五名号码
        SIXTH_PLACE_NUMBER,  // 第六名号码
        SEVENTH_PLACE_NUMBER, // 第七名号码
        EIGHTH_PLACE_NUMBER,  // 第八名号码
        NINTH_PLACE_NUMBER,   // 第九名号码
        TENTH_PLACE_NUMBER    // 第十名号码
    }

    private String employee;
    private BetType betType;
    private Integer position; // 1=冠军,...,10=第10名（仅属性投注使用）
    private Integer predictedNumber; // 预测的号码（仅号码预测使用）
    private Float betAmount;
    private Float payoutRate;

    // 空构造函数
    public OptimalBetRecord() {}

    /**
     * 属性投注构造方法
     */
    public OptimalBetRecord(String employee, int position, BetType betType,
                            Float betAmount, Float payoutRate) {
        if (betType.ordinal() > BetType.EVEN.ordinal()) {
            throw new IllegalArgumentException("属性投注只能使用SMALL/BIG/ODD/EVEN类型");
        }
        this.employee = Objects.requireNonNull(employee);
        this.betType = betType;
        this.position = position;
        this.predictedNumber = null;
        this.betAmount = betAmount;
        this.payoutRate = payoutRate;
    }

    /**
     * 号码预测构造方法
     */
    public OptimalBetRecord(String employee, BetType betType,
                            int predictedNumber, Float betAmount, Float payoutRate) {
        if (betType.ordinal() < BetType.CHAMPION_NUMBER.ordinal()) {
            throw new IllegalArgumentException("号码预测只能使用CHAMPION_NUMBER/RUNNER_UP_NUMBER/THIRD_PLACE_NUMBER类型");
        }
        this.employee = Objects.requireNonNull(employee);
        this.betType = betType;
        this.position = null;
        this.predictedNumber = predictedNumber;
        this.betAmount = betAmount;
        this.payoutRate = payoutRate;
    }

    public Float calculatePayout(int[] ranking) {
        if (betAmount == null || payoutRate == null) return 0f;

        switch (betType) {
            case SMALL:
                return ranking[position - 1] <= 5 ? betAmount * payoutRate : 0f;
            case BIG:
                return ranking[position - 1] >= 6 ? betAmount * payoutRate : 0f;
            case ODD:
                return ranking[position - 1] % 2 == 1 ? betAmount * payoutRate : 0f;
            case EVEN:
                return ranking[position - 1] % 2 == 0 ? betAmount * payoutRate : 0f;
            case CHAMPION_NUMBER:
                return predictedNumber == ranking[0] ? betAmount * payoutRate : 0f;
            case RUNNER_UP_NUMBER:
                return predictedNumber == ranking[1] ? betAmount * payoutRate : 0f;
            case THIRD_PLACE_NUMBER:
                return predictedNumber == ranking[2] ? betAmount * payoutRate : 0f;
            case FOURTH_PLACE_NUMBER:
                return predictedNumber == ranking[3] ? betAmount * payoutRate : 0f;
            case FIFTH_PLACE_NUMBER:
                return predictedNumber == ranking[4] ? betAmount * payoutRate : 0f;
            case SIXTH_PLACE_NUMBER:
                return predictedNumber == ranking[5] ? betAmount * payoutRate : 0f;
            case SEVENTH_PLACE_NUMBER:
                return predictedNumber == ranking[6] ? betAmount * payoutRate : 0f;
            case EIGHTH_PLACE_NUMBER:
                return predictedNumber == ranking[7] ? betAmount * payoutRate : 0f;
            case NINTH_PLACE_NUMBER:
                return predictedNumber == ranking[8] ? betAmount * payoutRate : 0f;
            case TENTH_PLACE_NUMBER:
                return predictedNumber == ranking[9] ? betAmount * payoutRate : 0f;
            default:
                return 0f;
        }
    }

    // Getter和Setter方法
    public String getEmployee() { return employee; }
    public void setEmployee(String employee) { this.employee = employee; }

    public BetType getBetType() { return betType; }
    public void setBetType(BetType betType) { this.betType = betType; }

    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }

    public Integer getPredictedNumber() { return predictedNumber; }
    public void setPredictedNumber(Integer predictedNumber) { this.predictedNumber = predictedNumber; }

    public Float getBetAmount() { return betAmount; }
    public void setBetAmount(Float betAmount) { this.betAmount = betAmount; }

    public Float getPayoutRate() { return payoutRate; }
    public void setPayoutRate(Float payoutRate) { this.payoutRate = payoutRate; }

    @Override
    public String toString() {
        if (betType.ordinal() <= BetType.EVEN.ordinal()) {
            return String.format("%s 投注第%d名 %s %.2f元(%.1f倍)",
                    employee, position, betType, betAmount, payoutRate);
        } else {
            String positionName = "";
            switch (betType) {
                case CHAMPION_NUMBER: positionName = "冠军"; break;
                case RUNNER_UP_NUMBER: positionName = "亚军"; break;
                case THIRD_PLACE_NUMBER: positionName = "季军"; break;
                case FOURTH_PLACE_NUMBER: positionName = "第四名"; break;
                case FIFTH_PLACE_NUMBER: positionName = "第五名"; break;
                case SIXTH_PLACE_NUMBER: positionName = "第六名"; break;
                case SEVENTH_PLACE_NUMBER: positionName = "第七名"; break;
                case EIGHTH_PLACE_NUMBER: positionName = "第八名"; break;
                case NINTH_PLACE_NUMBER: positionName = "第九名"; break;
                case TENTH_PLACE_NUMBER: positionName = "第十名"; break;
            }
            return String.format("%s 预测%s %d号 %.2f元(%.1f倍)",
                    employee, positionName, predictedNumber, betAmount, payoutRate);
        }
    }
}