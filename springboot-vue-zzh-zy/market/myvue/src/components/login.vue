<template>
  <div>
    <div>
      <el-menu
        id="nav"
        defalt-active="2"
        class="el-menu-vertical-demo"
        background-color="#1E9D9D"
        text-color="#FFFFFF"
        active-text-color="#FFFFFF"
      >
        <el-menu-item index="1" @click="main">
          <i class="el-icon-user-solid"  style="color:white;"></i>
          <span  slot="title">首 页</span>
        </el-menu-item>
        <el-menu-item v-if="this.userid === ''" index="2" @click="denglu">
          <i class="el-icon-user-solid"  style="color:white;"></i>
          <span  slot="title">登 录</span>
        </el-menu-item>
        <el-menu-item v-if="this.userid != ''" index="2" @click="user">
          <i class="el-icon-user-solid"  style="color:white;"></i>
          <span  slot="title">{{this.userid}}</span>
        </el-menu-item>
        <el-menu-item index="3" @click="car">
          <i class="el-icon-s-order" style="color:white;"></i>
          <span slot="title">购物车</span>
        </el-menu-item>
        <el-menu-item index="4" @click="chong">
          <i class="el-icon-message-solid" style="color:white;"></i>
          <span slot="title">充 值</span>
        </el-menu-item>
        <el-menu-item index="5" id="zhuxiao" @click="tui">
          <i class="el-icon-switch-button" style="color:white;"></i>
          <span slot="title">退 出</span>
        </el-menu-item>
      </el-menu>

    </div>
  <div class="login" >
    <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="auto" class="demo-ruleForm" >
      <p4>"闲鱼"校园版</p4>
      <el-form-item  label-width="300px" >
      </el-form-item>
      <el-form-item label="账号:" label-width="50px"  prop="pass">
        <el-input  clearable name="name" type="username" v-model.trim="ruleForm.pass" autocomplete="off" placeholder="请输入账号"  style="width: 250px"></el-input>
      </el-form-item>
      <el-form-item label="密码:" label-width="50px" prop="checkPass">
        <el-input clearable name="word" type="password" v-model.trim="ruleForm.checkPass" autocomplete="off" placeholder="请输入密码" style="width: 250px"></el-input>
      </el-form-item>
      <el-form-item label-width="95px">
        <el-button  type="primary" @click="login">登录</el-button>
        <el-button  @click="resetForm">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
  </div>
</template>

<script>

export default {
  data () {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入账号'))
      }
    }
    var validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      }
    }
    return {
      ruleForm: {
        pass: '',
        checkPass: ''
      },
      userid: '',
      userword: '',
      rules: {
        pass: [
          { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
          { validator: validatePass2, trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
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
      if (this.$g.userid === '') {
        this.$router.push('/login')
      } else {
        this.$router.push('/chongzhi')
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
    login () { // 登录按钮
      let formData = new FormData()
      formData.append('id', this.ruleForm.pass)
      formData.append('word', this.ruleForm.checkPass)
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8088/login', formData, config
      )
        .then(Response => {
          this.get()
          if (Response.data === 'admin') {
            sessionStorage.setItem('userid', this.ruleForm.pass)
            sessionStorage.setItem('userword', this.ruleForm.checkPass)
            this.$router.push('/admin')
          } else if (Response.data === 'user') {
            sessionStorage.setItem('userid', this.ruleForm.pass)
            sessionStorage.setItem('userword', this.ruleForm.checkPass)
            this.$router.push('/')
          } else {
            alert(Response.data)
          }
        })
    },
    get () {
      let formData = new FormData()
      formData.append('id', this.ruleForm.pass)
      formData.append('word', this.ruleForm.checkPass)
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8088/login1', formData, config
      )
        .then(Response => {
          // alert(sessionStorage.getItem('money'))
          sessionStorage.setItem('money', Response.data[0].money)
        })
    },
    resetForm () { // 注册按钮
      this.$router.push('/zhuce')
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

#nav {
  line-height: 30px;
  height: 950px;
  width: 120px;
  float: left;
  padding: 5px;
}
</style>
