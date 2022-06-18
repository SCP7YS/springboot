<template>
  <div>
    <div id="box3">
      <el-menu
        id="nav"
        defalt-active="2"
        class="el-menu-vertical-demo"
        background-color="#1E9D9D"
        text-color="#FFFFFF"
        active-text-color="#FFFFFF"
      >
        <el-menu-item index="1" @click="usermanage">
          <i class="el-icon-user-solid"  style="color:white;"></i>
          <span  slot="title">用户注册审核</span>
        </el-menu-item>
        <el-menu-item index="2" @click="shopmanage">
          <i class="el-icon-user-solid"  style="color:white;"></i>
          <span  slot="title">商家注册审核</span>
        </el-menu-item>
        <el-menu-item index="3" @click="goodmanage">
          <i class="el-icon-user-solid"  style="color:white;"></i>
          <span  slot="title">上架商品审核</span>
        </el-menu-item>
        <el-menu-item index="4" @click="shoplevel">
          <i class="el-icon-s-order" style="color:white;"></i>
          <span slot="title">商家等级管理</span>
        </el-menu-item>
        <el-menu-item index="5" @click="chong">
          <i class="el-icon-message-solid" style="color:white;"></i>
          <span slot="title">充  值</span>
        </el-menu-item>
        <el-menu-item index="6" id="zhuxiao" @click="tui">
          <i class="el-icon-switch-button" style="color:white;"></i>
          <span slot="title">退 出</span>
        </el-menu-item>
      </el-menu>
    </div>
    <div class="login" >
      <p>余额 :{{m}}元</p>
      <el-input  clearable  v-model.trim="v" autocomplete="off" placeholder="请输入充值金额/¥"  style="width: 250px"></el-input>
      <el-button  class="c" type="primary" @click="chongzhi">充值</el-button>
    </div>
  </div>
</template>

<script>

export default {
  data () {
    return {
      userid: '',
      userword: '',
      m: 0,
      v: ''
    }
  },
  mounted () {
    this.get()
  },
  methods: {
    usermanage () {
      this.$router.push('/admin')
    },
    goodmanage () {
      this.$router.push('/goodadmin')
    },
    shopmanage () {
      this.$router.push('/shopadmin')
    },
    shoplevel () {
      this.$router.push('/shoplevel')
    },
    car () {
      if (sessionStorage.getItem('userid') === '') {
        this.$router.push('/login')
      } else {
        this.$router.push('/car')
      }
    },
    main () {
      this.$router.push('/')
    },
    denglu () {
      this.$router.push('/login')
    },
    chong () {
      if (sessionStorage.getItem('userid') === '') {
        this.$router.push('/login')
      } else {
        this.$router.push('/adminchong')
      }
    },
    user () {
      this.$router.push('/personalinfo')
    },
    tui () {
      sessionStorage.setItem('userid', '')
      sessionStorage.setItem('userword', '')
      this.$router.push('/')
    },
    get () { // 登录按钮
      this.userid = sessionStorage.getItem('userid')
      this.userword = sessionStorage.getItem('userword')
      let formData = new FormData()
      formData.append('id', sessionStorage.getItem('userid'))
      formData.append('word', sessionStorage.getItem('userword'))
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8088/personal', formData, config
      )
        .then(Response => {
          this.m = Response.data[0].money
          sessionStorage.setItem('money', this.m)
        })
    },
    chongzhi () {
      let formData = new FormData()
      formData.append('id', sessionStorage.getItem('userid'))
      formData.append('word', sessionStorage.getItem('userword'))
      formData.append('money', this.m)
      formData.append('v', this.v)
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8088/chongzhi', formData, config
      )
        .then(Response => {
          this.get()
        })
    }
  }
}
</script>
<style type="text/css">
.login{
  position: absolute;/*绝对定位*/
  width: 300px;
  height: 200px;
  text-align: center;/*(让div中的内容居中)*/
  top: 50%;
  left: 50%;
  margin-top: -200px;
  margin-left: -150px;
}
.c {
  margin-top: 20px;
}
</style>
