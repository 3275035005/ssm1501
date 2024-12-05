<template>

  <div>
    <el-card >
      <div style="text-align: center;">
        <el-upload
            v-if="garbage.image === ''"
            ref="upload"
            class="upload-demo"
            drag
            action="http://localhost:8080/userWeb/recognition"
            :on-success="fileHandleSuccess"
            accept="image/*"
            multiple>

          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传识别垃圾分类</em></div>
        </el-upload>
                  <el-image
                      v-else
                      style="width: 360px; height: 180px"
                      :src="garbage.image"
                  ></el-image>
      </div>
      <el-divider>识别结果</el-divider>
      <div v-if="flag" style="margin-left: 400px">
        <el-form >
          <el-form-item label="垃圾名称：" >
            <div>{{ garbage.title }}</div>
          </el-form-item>
          <el-form-item label="垃圾类型：">
            <div v-if="garbage.type === '0'">可回收垃圾</div>
            <div v-else-if="garbage.type === '1'">干垃圾</div>
            <div v-else-if="garbage.type === '2'">湿垃圾</div>
            <div v-else-if="garbage.type === '3'">有害垃圾</div>
          </el-form-item>
          <el-form-item label="匹配度：">
            <div>{{ garbage.matched }}</div>
          </el-form-item>
        </el-form>
        <span style="margin-left: 50px">
        <el-button type="primary" @click="handleBtn">点击上传</el-button>
        </span>
      </div>
    </el-card>
  </div>
</template>

<script>
import API from "@/utils/request";
export default {

  name: "recognition",
  data() {
    return {
      flag: false,
      garbage:{
        image:''
      }
    }
  },
  methods: {
  fileHandleSuccess(response) {
    if(response.code == 200){
      this.flag = true;
      this.garbage = response.row;
      this.$refs.upload.clearFiles() //去掉文件列表
    }else{
      this.$message({
        type: 'error',
        message: response.message
      })
    }
   },

    handleBtn() {
      API.post("/userWeb/sendGarbage", this.garbage).then(res => {
        if (res.code === 200) {
          this.$message({
            type: 'success',
            message: '上传成功，请等待审核'
          })
          this.flag = false
          this.garbage =  {}
          this.garbage.image = ''
        }
      })
    },
  }
}
</script>

<style scoped>
.box-card {
  width: 480px;
}
</style>
