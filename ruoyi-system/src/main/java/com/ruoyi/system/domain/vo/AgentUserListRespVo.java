package com.ruoyi.system.domain.vo;

public class AgentUserListRespVo {

    /** 用户ID */
    private Long userId;

    /** 用户昵称 */
    private String nickName;

    /** 真实姓名 */
    private String realName;

    /** 用户备注名 */
    private String remarkName;

    /** 会员头像 */
    private String avatar;

    /** 产生佣金金额 */
    private Float generateCommissionMoney;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Float getGenerateCommissionMoney() {
        return generateCommissionMoney;
    }

    public void setGenerateCommissionMoney(Float generateCommissionMoney) {
        this.generateCommissionMoney = generateCommissionMoney;
    }
}
