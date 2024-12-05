<template>

  <div>
    <el-card style="height: 500px">
      <div style="text-align: center;">
        <el-image
            style="width: 360px; height: 180px"
            :src="garbage.image"
        ></el-image>

      </div>
      <el-divider>垃圾分类信息</el-divider>
      <div style="margin-left: 400px">

        <el-form >
          <el-form-item label="垃圾名称：" >
            <div>{{ garbage.title }}</div>
          </el-form-item>
          <el-form-item label="垃圾类型：">
            <div v-if="garbage.type === '0'">可回收垃圾</div>
            <div v-else-if="garbage.type === '1'">干垃圾</div>
            <div v-else-if="garbage.type === '2'">湿垃圾</div>
            <div v-else-if="garbage.type === '3'">有害垃圾</div>
            <div v-else-if="garbage.type === '4'">无法识别</div>
          </el-form-item>
          <el-form-item label="匹配度：">
            <div>{{ garbage.matched }}</div>
          </el-form-item>
          <el-form-item label="发布人：">
            <div style="float: left"> <el-image
                style="width: 50px; height: 50px; margin: 5px; border-radius: 50px"
                :src="garbage.userImage"
            ></el-image></div>
            <div style="margin-top: 20px">{{ garbage.userName }}</div>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
  </div>
</template>

<script>
import API from "@/utils/request";
export default {
  name: "garbageDetail",
  data() {
    return {
      garbage:{},
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
      API.get("/userWeb/getGarbageById?id="+this.id).then(res => {
        this.garbage = res.row;
      })
    }
  }
}
</script>

