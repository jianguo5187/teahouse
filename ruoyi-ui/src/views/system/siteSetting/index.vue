<template>
  <div class="app-container">
    <el-form ref="siteSettingForm" :model="siteSetting.form" :rules="siteSetting.rules" label-width="200px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="站点开关" prop="siteOpenFlg">
            <el-switch v-model="siteSetting.form.siteOpenFlg"></el-switch>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="最小充值金额" prop="minChargeMoney">
            <el-input-number v-model="siteSetting.form.minChargeMoney" :min="10" placeholder="请输入最小充值金额"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="最小提现金额" prop="minPostalMoney">
            <el-input-number v-model="siteSetting.form.minPostalMoney" :min="10" placeholder="请输入最小提现金额"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="每日提现次数" prop="postalMaxCnt">
            <el-input-number v-model="siteSetting.form.postalMaxCnt" :min="1" placeholder="请输入每日提现次数"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="首页弹窗公告" prop="appHomeAnnouncement">
            <el-input v-model="siteSetting.form.appHomeAnnouncement" type="textarea" :rows="6" placeholder="请输入首页弹窗公告"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="首页轮播信息" prop="appHomeNotice">
            <el-input v-model="siteSetting.form.appHomeNotice" placeholder="请输入首页轮播公告"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-show="loginUserId == 1 || loginUserId == 3">
        <el-col :span="12">
          <el-form-item label="官方开奖URL" prop="openRecordUrl">
            <el-input v-model="siteSetting.form.openRecordUrl" placeholder="请输入官方开奖URL"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-show="loginUserId == 1 || loginUserId == 3">
        <el-col :span="12">
          <el-form-item label="网站唯一标识ID" prop="webType">
            <el-input v-model="siteSetting.form.webType" placeholder="请输入网站唯一标识ID"/> 二维码网站跳转唯一识别key
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="网站名称" prop="webName">
            <el-input v-model="siteSetting.form.webName" placeholder="请输入网站名称"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-show="loginUserId == 1 || loginUserId == 3">
        <el-col :span="12">
          <el-form-item label="微信appId" prop="wechatAppId">
            <el-input v-model="siteSetting.form.wechatAppId" placeholder="请输入微信appId"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-show="loginUserId == 1 || loginUserId == 3">
        <el-col :span="12">
          <el-form-item label="微信appSecret" prop="wechatAppSecret">
            <el-input v-model="siteSetting.form.wechatAppSecret" placeholder="请输入微信appSecret"/>
          </el-form-item>
        </el-col>
      </el-row>
<!--      <el-row>-->
<!--        <el-col :span="12">-->
<!--          <el-form-item label="授权域名" prop="wechatAuthUrl">-->
<!--            <el-input v-model="siteSetting.form.wechatAuthUrl" placeholder="请输入授权域名"/> 例如：http://c12q.zs2ux.cn-->
<!--          </el-form-item>-->
<!--        </el-col>-->
<!--      </el-row>-->
      <el-row v-show="loginUserId == 1">
        <el-col :span="12">
          <el-form-item label="客服端聊天地址" prop="kefuImUrl">
            <el-input v-model="siteSetting.form.kefuImUrl" placeholder="请输入客服端聊天地址"/> 例如：http://43.132.168.72:81
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-show="loginUserId == 1">
        <el-col :span="12">
          <el-form-item label="玩家端聊天地址" prop="appImUrl">
            <el-input v-model="siteSetting.form.appImUrl" placeholder="请输入玩家端聊天地址"/> 例如：http://43.132.168.72:82
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-show="loginUserId == 1">
        <el-col :span="12">
          <el-form-item label="二维码服务器IP地址" prop="qrServerUrl">
            <el-input v-model="siteSetting.form.qrServerUrl" placeholder="请输入二维码服务器IP地址"/> 例如：http://43.159.192.159
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12">
          <el-form-item label="自开彩种赢亏比率" prop="systemGameWinRate">
            <el-input-number v-model="siteSetting.form.systemGameWinRate" :min="1" :max="100" placeholder="请输入自开彩种赢亏比率"/> 1~100
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" v-show="customerShowFlg">
          <el-form-item label="logo图片" prop="logoImg">
            <imageUpload v-model="siteSetting.form.logoImg" :imgUrl="siteSetting.form.logoImg" :limit="1"></imageUpload>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" v-show="customerShowFlg">
          <el-form-item label="微信客服图片" prop="wechatImg">
            <imageUpload v-model="siteSetting.form.wechatImg" :imgUrl="siteSetting.form.wechatImg" :limit="1"></imageUpload>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="12" v-show="customerShowFlg">
          <el-form-item label="QQ客服图片" prop="qqChatImg">
            <imageUpload v-model="siteSetting.form.qqChatImg" :imgUrl="siteSetting.form.qqChatImg" :limit="1"></imageUpload>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-show="loginUserId == 1">
        <el-col :span="12">
          <el-form-item label="阿里云IP查询AppCode" prop="aliCloudApiCode">
            <el-input v-model="siteSetting.form.aliCloudApiCode" placeholder="请输入阿里云IP查询AppCode"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-show="loginUserId == 1">
        <el-col :span="12">
          <el-form-item label="拒绝访问省份" prop="refuseProvince">
            <el-input v-model="siteSetting.form.refuseProvince" placeholder="请输入拒绝访问省份"/>  用半角逗号隔开 例如：福建,浙江,广州
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-show="loginUserId == 1">
        <el-col :span="12">
          <el-form-item label="拒绝访问城市" prop="refuseCity">
            <el-input v-model="siteSetting.form.refuseCity" placeholder="请输入拒绝访问城市"/>  用半角逗号隔开 例如：厦门,深圳,杭州
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-show="loginUserId == 1">
        <el-col :span="12">
          <el-form-item label="拒绝访问服务商" prop="refuseIsp">
            <el-input v-model="siteSetting.form.refuseIsp" placeholder="请输入拒绝访问服务商"/>  用半角逗号隔开 例如：中国联通,中国移动
          </el-form-item>
        </el-col>
      </el-row>
      <el-row v-show="loginUserId == 1">
        <el-col :span="12">
          <el-form-item label="微信拦截状态接口Code" prop="wxAutoCheckApiCode">
            <el-input v-model="siteSetting.form.wxAutoCheckApiCode" placeholder="请输入微信拦截状态接口Code"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item>
        <el-button type="primary" size="mini" @click="submitForm">确认修改</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import {getSiteSetting, updateSiteSetting} from "@/api/system/user";

export default {
  name: "SiteSetting",
  data() {
    return {
      // 登录用户ID
      loginUserId: this.$store.state.user.id,
      customerShowFlg : false,
      // 上传chat参数
      siteSetting: {
        // 表单参数
        form: {
          siteOpenFlg: false,
          minChargeMoney: 10,
          minPostalMoney: 10,
          postalMaxCnt: 1,
          // imSite: undefined,
          // appImSite: undefined,
          appHomeNotice: undefined,
          appHomeAnnouncement:undefined,
          openRecordUrl: undefined,
          webType: undefined,
          webName: undefined,
          wechatAppId: undefined,
          wechatAppSecret: undefined,
          // wechatAuthUrl: undefined,
          kefuImUrl: undefined,
          appImUrl: undefined,
          qrServerUrl: undefined,

          aliCloudApiCode: undefined,
          refuseProvince: undefined,
          refuseCity: undefined,
          refuseIsp: undefined,
          wxAutoCheckApiCode: undefined,
          systemGameWinRate: 10,
          logoImg: undefined,
          wechatImg: undefined,
          qqChatImg: undefined,
        },
        // 表单校验
        rules: {
          siteOpenFlg: [
            { required: true, message: "站点开关不能为空", trigger: "blur" }
          ],
          minChargeMoney: [
            { required: true, message: "最小充值金额不能为空", trigger: "blur" }
          ],
          minPostalMoney: [
            { required: true, message: "最小提现金额不能为空", trigger: "blur" }
          ],
          postalMaxCnt: [
            { required: true, message: "每日提现次数不能为空", trigger: "blur" }
          ],
          appHomeAnnouncement: [
            { required: true, message: "app首页公告内容不能为空", trigger: "blur" }
          ],
          appHomeNotice: [
            { required: true, message: "轮播公告不能为空", trigger: "blur" }
          ],
          openRecordUrl: [
            { required: true, message: "官方开奖URL不能为空", trigger: "blur" }
          ],
          webType: [
            { required: true, message: "网站唯一标识ID不能为空", trigger: "blur" }
          ],
          webName: [
            { required: true, message: "网站名称不能为空", trigger: "blur" }
          ],
          wechatAppId: [
            { required: true, message: "微信appId不能为空", trigger: "blur" }
          ],
          wechatAppSecret: [
            { required: true, message: "微信Secret不能为空", trigger: "blur" }
          ],
          // wechatAuthUrl: [
          //   { required: true, message: "授权域名不能为空", trigger: "blur" }
          // ],
          kefuImUrl: [
            { required: true, message: "客服聊天网站地址不能为空", trigger: "blur" }
          ],
          appImUrl: [
            { required: true, message: "客户聊天网站地不能为空", trigger: "blur" }
          ],
          qrServerUrl: [
            { required: true, message: "二维码服务器IP地址不能为空", trigger: "blur" }
          ],
          systemGameWinRate: [
            { required: true, message: "自开彩种赢亏比率不能为空", trigger: "blur" }
          ],

          aliCloudApiCode: [
            { required: true, message: "阿里云IP查询AppCode不能为空", trigger: "blur" }
          ],
          // refuseProvince: [
          //   { required: true, message: "拒绝访问省份不能为空", trigger: "blur" }
          // ],
          // refuseCity: [
          //   { required: true, message: "拒绝访问城市不能为空", trigger: "blur" }
          // ],
          wxAutoCheckApiCode: [
            { required: true, message: "微信拦截状态接口Code不能为空", trigger: "blur" }
          ],
          logoImg: [
            { required: true, message: "logo图片不能为空", trigger: "blur" }
          ],
          wechatImg: [
            { required: true, message: "微信客服图片不能为空", trigger: "blur" }
          ],
          qqChatImg: [
            { required: true, message: "QQ客服图片不能为空", trigger: "blur" }
          ],
        }
      },
    };
  },
  created() {
    this.getSiteSetting();
  },
  methods: {
    getSiteSetting(){
      // this.siteSetting.form = {
      //   siteOpenFlg: false
      // }
      getSiteSetting().then(response => {
        this.siteSetting.form.siteOpenFlg = Boolean(response.siteOpenFlg);

        if(response.minChargeMoney != undefined){
          this.siteSetting.form.minChargeMoney = response.minChargeMoney;
        }
        if(response.minPostalMoney != undefined){
          this.siteSetting.form.minPostalMoney = response.minPostalMoney;
        }
        if(response.postalMaxCnt != undefined){
          this.siteSetting.form.postalMaxCnt = response.postalMaxCnt;
        }
        if(response.appHomeAnnouncement != undefined){
          this.siteSetting.form.appHomeAnnouncement = response.appHomeAnnouncement;
        }
        if(response.appHomeNotice != undefined){
          this.siteSetting.form.appHomeNotice = response.appHomeNotice;
        }
        if(response.openRecordUrl != undefined){
          this.siteSetting.form.openRecordUrl = response.openRecordUrl;
        }
        if(response.webType != undefined){
          this.siteSetting.form.webType = response.webType;
        }
        if(response.webName != undefined){
          this.siteSetting.form.webName = response.webName;
        }
        if(response.wechatAppId != undefined){
          this.siteSetting.form.wechatAppId = response.wechatAppId;
        }
        if(response.wechatAppSecret != undefined){
          this.siteSetting.form.wechatAppSecret = response.wechatAppSecret;
        }
        // if(response.wechatAuthUrl != undefined){
        //   this.siteSetting.form.wechatAuthUrl = response.wechatAuthUrl;
        // }
        if(response.kefuImUrl != undefined){
          this.siteSetting.form.kefuImUrl = response.kefuImUrl;
        }
        if(response.appImUrl != undefined){
          this.siteSetting.form.appImUrl = response.appImUrl;
        }
        if(response.qrServerUrl != undefined){
          this.siteSetting.form.qrServerUrl = response.qrServerUrl;
        }
        if(response.systemGameWinRate != undefined){
          this.siteSetting.form.systemGameWinRate = response.systemGameWinRate;
        }

        if(response.aliCloudApiCode != undefined){
          this.siteSetting.form.aliCloudApiCode = response.aliCloudApiCode;
        }
        if(response.refuseProvince != undefined){
          this.siteSetting.form.refuseProvince = response.refuseProvince;
        }
        if(response.refuseCity != undefined){
          this.siteSetting.form.refuseCity = response.refuseCity;
        }
        if(response.refuseIsp != undefined){
          this.siteSetting.form.refuseIsp = response.refuseIsp;
        }
        if(response.wxAutoCheckApiCode != undefined){
          this.siteSetting.form.wxAutoCheckApiCode = response.wxAutoCheckApiCode;
        }
        if(response.logoImg != undefined){
          this.siteSetting.form.logoImg = response.logoImg;
        }
        if(response.wechatImg != undefined){
          this.siteSetting.form.wechatImg = response.wechatImg;
        }
        if(response.qqChatImg != undefined){
          this.siteSetting.form.qqChatImg = response.qqChatImg;
        }
        console.log("getSiteSetting");
      });
    },
    submitForm(){
      this.$refs["siteSettingForm"].validate(valid => {
        if (valid) {
          updateSiteSetting(this.siteSetting.form).then(response => {
            this.$modal.msgSuccess("更新成功");
          });
        }
      });
    },
  }
};
</script>
