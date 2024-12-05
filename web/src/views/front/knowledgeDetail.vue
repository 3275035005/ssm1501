<template>

  <div>
    <el-card style="height: 500px">
      <div style="text-align: center;">
        <el-image
            style="width: 360px; height: 180px"
            :src="knowledge.image"
        ></el-image>

      </div>
      <el-divider>垃圾分类知识信息</el-divider>
      <div style="margin-left: 400px">

        <el-form >
          <el-form-item label="垃圾知识标题：" >
            <div>{{ knowledge.title }}</div>
          </el-form-item>
          <el-form-item label="垃圾知识正文：" >
            <div v-html="knowledge.content"></div>
          </el-form-item>
          <el-form-item label="垃圾知识原理：" >
            <div v-html="knowledge.content1"></div>
          </el-form-item>
          <el-form-item label="垃圾知识正文：" >
            <div v-html="knowledge.content2"></div>
          </el-form-item>
          <el-form-item label="知识意义：" >
            <div v-html="knowledge.content3"></div>
          </el-form-item>
          <el-form-item label="发布时间：" >
            <div>{{ knowledge.createTime }}</div>
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
      knowledge:{},
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
      API.get("/userWeb/getKnowledgeById?id="+this.id).then(res => {
        this.knowledge = res.row;
      })
    }
  }
}
</script>

