<template>
  <div >
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
    <el-container style="height: 700px; border: 1px solid #eee">
      <el-header  style="background-color: rgb(238, 241, 246)">
        <p6 style="margin-left: 25px;height: 100px;font-size:22px;">后台管理审核系统</p6>
      </el-header>
        <el-main style="height:0;flex-grow:1;">
            <el-col :span="3">
            <el-input style="margin-left: 910px" width="120" v-model="search" v-on:input ="inputFunc" clearable="true" placeholder="输入关键字搜索"/>
            </el-col>
            <el-button style="margin-left: 960px" type="primary" @click="clear">全部</el-button>
            <el-table :data="tableData.slice((currentPage - 1) * pageSize, currentPage*pageSize)"
            @row-click="openDetails" border>
                <el-table-column prop="id" label="商家账号" width="80">
                </el-table-column>
                <el-table-column prop="word" label="密码" width="80">
                </el-table-column>
                <el-table-column prop="name" label="姓名" width="100">
                </el-table-column>
                <el-table-column prop="sex" label="性别" width="60">
                </el-table-column>
                <el-table-column prop="phone" label="手机号" width="130">
                </el-table-column>
                <el-table-column prop="email" label="邮箱" width="150">
                </el-table-column>
                <el-table-column prop="city" label="城市" width="76">
                </el-table-column>
                <el-table-column prop="account" label="银行账户" width="180" >
                </el-table-column>
                <el-table-column prop="licence" label="营业执照" width="200" >
                    <template slot-scope="scope">
                        <el-image style="width: 200px; height: 200px" :src="scope.row.licence" lazy></el-image>
                    </template>
                </el-table-column>
                <el-table-column prop="card" label="身份证照片" width="200" >
                    <template slot-scope="scope">
                        <el-image style="width: 200px; height: 200px" :src="scope.row.card" lazy></el-image>
                    </template>
                </el-table-column>
                <el-table-column  prop="level" width="180" label="商家等级">
                    <template slot-scope="scope">
                        <el-input-number size="mini" v-model="scope.row.level" @change="(currentValue, oldValue) => handleChange(currentValue, oldValue, scope.$index)"
                    :min="1"  :max="5" label="描述文字"></el-input-number>
                    </template>
                </el-table-column>
            </el-table>
        </el-main>
        <el-footer>
            <el-pagination
                @size-change="sizechange"
                @current-change="currentchange"
                style="margin-left: 1010px"
                background
                :current-page="currentPage"
                :page-size="pageSize"
                layout="prev, pager, next"
                :total="length">
            </el-pagination>
        </el-footer>
    </el-container>
  </div>
</template>
<script>
export default {
  data () {
    return {
      userid: '',
      userword: '',
      tableData: [],
      search: '',
      currentPage: 1,
      pageSize: 8,
      length: 0,
      index: '',
      rows: '',
      column: '',
      table: []
    }
  },
  mounted () {
    this.getData()
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
    shoplevel () {
      this.$router.push('/shoplevel')
    },
    goodmanage () {
      this.$router.push('/goodadmin')
    },
    usermanage () {
      this.$router.push('/admin')
    },
    shopmanage () {
      this.$router.push('/shopadmin')
    },
    inputFunc () { // 动态搜索
      this.currentPage = 1
      this.getList()
    },
    openDetails (row) { // 获取点击行的数据
      this.column = row
    },
    handleChange (currentValue, oldValue, index) {
      let formData = new FormData()
      formData.append('id', sessionStorage.getItem('userid'))
      formData.append('word', sessionStorage.getItem('userword'))
      formData.append('uid', this.tableData[(this.currentPage - 1) * this.pageSize + index].id)
      formData.append('level', this.tableData[(this.currentPage - 1) * this.pageSize + index].level)
      let config = {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }
      this.$axios.post('http://localhost:8088/changelevel', formData, config)
        .then(res => {
        })
    },
    getData () { // 后台获取数据
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
      this.$axios.post('http://localhost:8088/getshoplevel', formData, config)
        .then(res => {
          this.tableData = res.data
          for (var i = 0; i < res.data.length; i++) {
            this.tableData[i].licence = require('E:/大三学业/大三下/软件项目实践/pic/merchants/' + this.tableData[i].licence)
            this.tableData[i].card = require('E:/大三学业/大三下/软件项目实践/pic/merchants/' + this.tableData[i].card)
          }
          this.length = this.tableData.length
          this.table = this.tableData
        })
    },
    getList () { // 根据id搜索结果集
      this.tableData = this.tableData.filter((item, index) =>
        item.id.includes(this.search)
      )
      this.length = this.tableData.length
    },
    clear () { // 搜索转全部
      this.search = ''
      this.currentPage = 1
      this.getData()
    },
    sizechange (val) {
      this.pageSize = val
    },
    currentchange (val) {
      this.currentPage = val
    }
  }
}
</script>
<style>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 50px;
  }
</style>
