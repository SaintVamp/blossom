<template>
  <div class="config-root" v-loading="!userStore.isLogin" element-loading-spinner="none" element-loading-text="请登录后查看...">
    <div class="title">通知配置</div>
    <div class="desc" style="margin-bottom: 0">通知服务的各项参数配置，若无内容请点击下方刷新。只有管理员用户具有操作服务器配置的权限。</div>
    <div class="desc">
      <el-button @click="refreshParam" text bg><span class="iconbl bl-refresh-line"></span>刷新参数</el-button>
    </div>

    <el-form :model="noticeParamForm" label-position="right" label-width="130px" style="max-width: 800px">
      <bl-row just="flex-start" class="config-module-titile"><span class="iconbl bl-sendmail-line"></span>企微通知设置</bl-row>
      <el-form-item label="说明">
        <div class="conf-tip">打开https://work.weixin.qq.com/wework_admin/frame页面</div>
      </el-form-item>
      <el-form-item label="开启企业微信通知">
        <bl-row>
          <el-switch
            size="default"
            v-model="noticeParamForm.NOTICE_WORK_ENABLED"
            @change="(cur: boolean) => updParam('NOTICE_WORK_ENABLED', cur ? '1' : '0')" />
        </bl-row>
        <div class="conf-tip">是否开启企业微信通知。</div>
      </el-form-item>
      <el-form-item label="企微公司密钥">
        <el-input
          size="default"
          v-model="noticeParamForm.NOTICE_WORK_CORP_SECRET"
          @change="(cur: any) => updParam('NOTICE_WORK_CORP_SECRET', cur)"></el-input>
        <div class="conf-tip">从应用管理标签中的自定义应用详情页面中Secret内容</div>
      </el-form-item>

      <el-form-item label="企微公司ID">
        <el-input
          size="default"
          v-model="noticeParamForm.NOTICE_WORK_CORP_ID"
          @change="(cur: any) => updParam('NOTICE_WORK_CORP_ID', cur)"></el-input>
        <div class="conf-tip">从我的企业标签中的企业ID</div>
      </el-form-item>

      <el-form-item label="企微代理ID">
        <el-input
          size="default"
          v-model="noticeParamForm.NOTICE_WORK_AGENT_ID"
          @change="(cur: any) => updParam('NOTICE_WORK_AGENT_ID', cur)"></el-input>
        <div class="conf-tip">从应用管理标签中的自定义应用详情页面中AgentId内容</div>
      </el-form-item>

      <el-form-item label="企微用户ID">
        <el-input
          size="default"
          v-model="noticeParamForm.NOTICE_WORK_USER_ID"
          @change="(cur: any) => updParam('NOTICE_WORK_USER_ID', cur)"></el-input>
        <div class="conf-tip">从通讯录标签中成员详情中的账号</div>
      </el-form-item>

      <el-form-item label="企微手机号">
        <el-input size="default" v-model="noticeParamForm.NOTICE_WORK_MOBILE" @change="(cur: any) => updParam('NOTICE_WORK_MOBILE', cur)"></el-input>
        <div class="conf-tip">从通讯录标签中成员详情中的手机</div>
      </el-form-item>

      <bl-row just="flex-start" class="config-module-titile"><span class="iconbl bl-sendmail-line"></span>邮箱通知设置</bl-row>
      <el-form-item label="开启邮件通知">
        <bl-row>
          <el-switch
            size="default"
            v-model="noticeParamForm.NOTICE_EMAIL_ENABLED"
            @change="(cur: boolean) => updParam('NOTICE_EMAIL_ENABLED', cur ? '1' : '0')" />
        </bl-row>
        <div class="conf-tip">是否开启邮件通知。</div>
      </el-form-item>
      <el-form-item label="邮件服务器">
        <el-input
          size="default"
          v-model="noticeParamForm.NOTICE_EMAIL_SMTP_SERVER"
          @change="(cur: any) => updParam('NOTICE_EMAIL_SMTP_SERVER', cur)"></el-input>
        <div class="conf-tip">邮件服务器</div>
      </el-form-item>

      <el-form-item label="邮件用户名">
        <el-input
          size="default"
          v-model="noticeParamForm.NOTICE_EMAIL_SMTP_USER"
          @change="(cur: any) => updParam('NOTICE_EMAIL_SMTP_USER', cur)"></el-input>
        <div class="conf-tip">邮件用户名</div>
      </el-form-item>

      <el-form-item label="邮件密码">
        <el-input
          size="default"
          v-model="noticeParamForm.NOTICE_EMAIL_SMTP_PWD"
          @change="(cur: any) => updParam('NOTICE_EMAIL_SMTP_PWD', cur)"></el-input>
        <div class="conf-tip">邮件密码</div>
      </el-form-item>

      <bl-row just="flex-start" class="config-module-titile"><span class="iconbl bl-sendmail-line"></span>IYUU通知设置</bl-row>
      <el-form-item label="开启IYUU通知">
        <bl-row>
          <el-switch
            size="default"
            v-model="noticeParamForm.NOTICE_IYUU_ENABLED"
            @change="(cur: boolean) => updParam('NOTICE_IYUU_ENABLED', cur ? '1' : '0')" />
        </bl-row>
        <div class="conf-tip">是否开启IYUU通知。</div>
      </el-form-item>
      <el-form-item label="IYUU密钥">
        <el-input size="default" v-model="noticeParamForm.NOTICE_IYUU_TOKEN" @change="(cur: any) => updParam('NOTICE_IYUU_TOKEN', cur)"></el-input>
        <div class="conf-tip">IYUU密钥</div>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@renderer/stores/user'
import { paramListApi, paramRefreshApi, paramUpdApi } from '@renderer/api/blossom'
import Notify from '@renderer/scripts/notify'


const userStore = useUserStore()

const noticeParamForm = ref({
  NOTICE_IYUU_ENABLED: false,
  NOTICE_IYUU_TOKEN: '',
  NOTICE_EMAIL_ENABLED: false,
  NOTICE_EMAIL_SMTP_PWD: '',
  NOTICE_EMAIL_SMTP_USER: '',
  NOTICE_EMAIL_SMTP_SERVER: '',
  NOTICE_WORK_ENABLED: false,
  NOTICE_WORK_MOBILE: '',
  NOTICE_WORK_USER_ID: '',
  NOTICE_WORK_AGENT_ID: '',
  NOTICE_WORK_CORP_ID: '',
  NOTICE_WORK_CORP_SECRET: ''
})

/**
 * 获取参数列表
 */
const getParamList = () => {
  paramListApi().then((resp) => {
    noticeParamForm.value = {
      ...resp.data,
      ...{
        NOTICE_IYUU_ENABLED: resp.data.NOTICE_IYUU_ENABLED === '1',
        NOTICE_WORK_ENABLED: resp.data.NOTICE_WORK_ENABLED === '1',
        NOTICE_EMAIL_ENABLED: resp.data.NOTICE_EMAIL_ENABLED === '1'
      }
    }
  })
}

const refreshParam = () => {
  paramRefreshApi().then((_) => {
    Notify.success('', '刷新成功')
    getParamList()
    userStore.getUserinfo()
  })
}

const updParam = (paramName: string, paramValue: string) => {
  paramUpdApi({ paramName: paramName, paramValue: paramValue }).then((_resp) => {
    userStore.getUserinfo()
    ElMessage.info({ message: '保存成功', grouping: true })
  })
}

const reload = () => {
  getParamList()
}

defineExpose({ reload })
</script>

<style scoped lang="scss">
@import './styles/config-root.scss';

.server-config {
  padding: 10px;
  font-size: 12px;
  white-space: pre;
  color: var(--bl-text-color-light);
}
</style>
