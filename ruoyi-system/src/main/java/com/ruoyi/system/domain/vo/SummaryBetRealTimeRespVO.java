package com.ruoyi.system.domain.vo;

public class SummaryBetRealTimeRespVO {

    /** 游戏ID */
    private Long gameId;

    /** 游戏名 */
    private String gameName;

    /** 期数 */
    private Long periods;

    /** 玩法分类 */
    private String playType;

    /** 投注内容 */
    private String playDetail;

    /** 投注金额 */
    private Float money;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Long getPeriods() {
        return periods;
    }

    public void setPeriods(Long periods) {
        this.periods = periods;
    }

    public String getPlayType() {
        return playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }

    public String getPlayDetail() {
        return playDetail;
    }

    public void setPlayDetail(String playDetail) {
        this.playDetail = playDetail;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }
}
