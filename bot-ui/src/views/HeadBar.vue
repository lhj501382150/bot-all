<template>
  <div class="headbar" :style="{'background':themeColor}"
    :class="collapse?'position-collapse-left':'position-left'">
    <!-- 导航收缩 -->
    <span class="hamburg">
      <el-menu class="el-menu-demo" :background-color="themeColor"
        :active-text-color="themeColor" mode="horizontal">
        <el-menu-item index="1" @click="onCollapse">
          <hamburger :isActive="collapse"></hamburger>
        </el-menu-item>
      </el-menu>
    </span>

    <!-- 工具栏 -->
    <span class="head-toolbar">
      <el-menu class="el-menu-demo" :background-color="themeColor" text-color="#14889A"
        :active-text-color="themeColor" mode="horizontal">
        <el-menu-item index="1">
          <!-- 主题切换 -->
          <theme-picker class="theme-picker" :default="themeColor"
            @onThemeChange="onThemeChange">
          </theme-picker>
        </el-menu-item>

        <el-menu-item index="5" v-popover:popover-personal>
          <!-- 用户信息 -->
          <span class="user-info">登陆用户：{{user.loginname}}</span>
          <el-popover ref="popover-personal" placement="bottom-end" trigger="click" :visible-arrow="false">
            <personal-panel :user="user"></personal-panel>
          </el-popover>
        </el-menu-item>
      </el-menu>
    </span>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import mock from "@/mock/index"
import Hamburger from "@/components/Hamburger"
import ThemePicker from "@/components/ThemePicker"
import PersonalPanel from "@/views/Core/PersonalPanel"
export default {
  components:{
    Hamburger,
    ThemePicker,
    PersonalPanel
  },
  data() {
    return {
      user: {
      },
      activeIndex: '1',
      langVisible: false
    }
  },
  methods: {
    openWindow(url) {
      window.open(url)
    },
    selectNavBar(key, keyPath) {
      console.log(key, keyPath)
    },
    // 折叠导航栏
    onCollapse: function() {
      this.$store.commit('onCollapse')
    },
    // 切换主题
    onThemeChange: function(themeColor) {
      this.$store.commit('setThemeColor', themeColor)
    },
    //缓存客户类型
    setCurUser: function (user){
      this.$store.commit('setCurUser',user)
    },
    // 语言切换
    changeLanguage(lang) {
      lang === '' ? 'zh_cn' : lang
      this.$i18n.locale = lang
      this.langVisible = false
    }
  },
  mounted() {
    var user = sessionStorage.getItem("user")
    if (user) {
      let params = {name:user}
      this.$api.user.findByName(params).then((res) => {
				if(res.code == 200) {
          this.user = res.data
          this.setCurUser(res.data)
        }
      })
    }
  },
  computed:{
    ...mapState({
      themeColor: state=>state.app.themeColor,
      collapse: state=>state.app.collapse
    })
  }
}
</script>

<style scoped lang="scss">
.headbar {
  position: fixed;
  top: 0;
  right: 0;
  z-index: 1030;
  height: 60px;
  line-height: 60px;
  border-color: rgba(180, 190, 190, 0.8);
  border-left-width: 1px;
  border-left-style: solid;
}
.hamburg {
  float: left;
}
.navbar {
  float: left;
}
.head-toolbar {
  float: right;
}
.lang-item {
  font-size: 16px;
  padding-left: 8px;
  padding-top: 8px;
  padding-bottom: 8px;
  cursor: pointer;
}
.lang-item:hover {
  font-size: 18px;
  background: #b0d6ce4d;
}
.user-info {
  font-size: 16px;
  color: #fff;
  cursor: pointer;

}
.badge {
  line-height: 18px;
}
.position-left {
  left: 200px;
}
.position-collapse-left {
  left: 65px;
}
</style>
