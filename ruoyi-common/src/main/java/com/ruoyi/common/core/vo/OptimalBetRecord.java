package com.ruoyi.common.core.vo;import java.util.Objects;

/**
 * 投注记录类（支持小数金额和赔率）
 */
public class OptimalBetRecord {
    private String employee;
    private Integer position;      // 1=冠军,...,10=第10名
    private Float smallBet;    // 投注"小"金额
    private Float bigBet;      // 投注"大"金额
    private Float oddBet;      // 投注"单"金额
    private Float evenBet;     // 投注"双"金额
    private Float smallPayoutRate;
    private Float bigPayoutRate;
    private Float oddPayoutRate;
    private Float evenPayoutRate;

    public OptimalBetRecord(){}

    public OptimalBetRecord(String employee, Integer position,
                            Float smallBet, Float smallPayoutRate,
                            Float bigBet, Float bigPayoutRate,
                            Float oddBet, Float oddPayoutRate,
                            Float evenBet, Float evenPayoutRate) {
        this.employee = Objects.requireNonNull(employee);
        if (position < 1 || position > 10) {
            throw new IllegalArgumentException("名次必须是1-10");
        }
        this.position = position;
        this.smallBet = smallBet;
        this.bigBet = bigBet;
        this.oddBet = oddBet;
        this.evenBet = evenBet;
        this.smallPayoutRate = smallPayoutRate;
        this.bigPayoutRate = bigPayoutRate;
        this.oddPayoutRate = oddPayoutRate;
        this.evenPayoutRate = evenPayoutRate;
    }

    public Float calculatePayout(Integer number) {
        Float payout = 0f;
        boolean isSmall = number <= 5;
        boolean isEven = number % 2 == 0;

        if (smallBet != null && isSmall) payout += smallBet * smallPayoutRate;
        if (bigBet != null && !isSmall) payout += bigBet * bigPayoutRate;
        if (oddBet != null && !isEven) payout += oddBet * oddPayoutRate;
        if (evenBet != null && isEven) payout += evenBet * evenPayoutRate;

        return payout;
    }

    // Getter方法
    public String getEmployee() { return employee; }
    public Integer getPosition() { return position; }
    public Float getSmallBet() { return smallBet; }
    public Float getBigBet() { return bigBet; }
    public Float getOddBet() { return oddBet; }
    public Float getEvenBet() { return evenBet; }
    public Float getSmallPayoutRate() { return smallPayoutRate; }
    public Float getBigPayoutRate() { return bigPayoutRate; }
    public Float getOddPayoutRate() { return oddPayoutRate; }
    public Float getEvenPayoutRate() { return evenPayoutRate; }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public void setSmallBet(Float smallBet) {
        this.smallBet = smallBet;
    }

    public void setBigBet(Float bigBet) {
        this.bigBet = bigBet;
    }

    public void setOddBet(Float oddBet) {
        this.oddBet = oddBet;
    }

    public void setEvenBet(Float evenBet) {
        this.evenBet = evenBet;
    }

    public void setSmallPayoutRate(Float smallPayoutRate) {
        this.smallPayoutRate = smallPayoutRate;
    }

    public void setBigPayoutRate(Float bigPayoutRate) {
        this.bigPayoutRate = bigPayoutRate;
    }

    public void setOddPayoutRate(Float oddPayoutRate) {
        this.oddPayoutRate = oddPayoutRate;
    }

    public void setEvenPayoutRate(Float evenPayoutRate) {
        this.evenPayoutRate = evenPayoutRate;
    }
}