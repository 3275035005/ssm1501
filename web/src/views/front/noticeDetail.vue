<template>

  <div>
    <el-card>
      <div style="text-align: center;">
        <el-image
            style="width: 360px; height: 180px"
            :src="notice.image"
        ></el-image>

      </div>
      <el-divider>公告信息</el-divider>
      <div style="margin-left: 400px">

        <el-form >
          <el-form-item label="公告标题：" >
            <div>{{ notice.title }}</div>
          </el-form-item>
          <el-form-item label="公告内容：" >
            <div  v-html="notice.content"></div>
          </el-form-item>
          <el-form-item label="发布时间：" >
            <div>{{ notice.createTime }}</div>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
import API from "@/utils/request";
export default {
  name: "noticeDetail",
  data() {
    return {
      notice:{},
      user:'',
      id:''
    }
  },
  created() {
    this.user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : {}
    this.id = this.$route.query.id
    this.init()
  },
  methods: {
    init(){
      API.get("/userWeb/getNoticeById?id="+this.id).then(res => {
        this.notice = res.row;
      })
    }
  }
}
</script>

