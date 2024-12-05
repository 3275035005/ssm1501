<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="垃圾名称">
        <el-input v-model="dataVo.title" placeholder="请输入垃圾名称"/>
      </el-form-item>
      <el-form-item label="垃圾类型">
        <el-select v-model="dataVo.type" placeholder="请选择垃圾类型">
          <el-option label="可回收垃圾" value="0"></el-option>
          <el-option label="干垃圾" value="1"></el-option>
          <el-option label="湿垃圾 " value="2"></el-option>
          <el-option label="有害垃圾 " value="3"></el-option>
          <el-option label="无法识别 " value="4"></el-option>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">重置</el-button>
    </el-form>
    <!-- 表格 -->
    <el-table
        :data="list"
        border
        fit
        highlight-current-row
        empty-text="暂无数据"
    >
      <el-table-column align="center" label="上传图片">
        <template slot-scope="scope">
          <img
              style="width: 80px; height: 80px"
              :src="scope.row.image"/>
        </template>
      </el-table-column>
      <el-table-column prop="title" label="垃圾名称"/>
      <el-table-column label="垃圾类型" prop="type">
        <template slot-scope="scope">
          <span v-if="scope.row.type ==='0'">可回收垃圾</span>
          <span v-if="scope.row.type ==='1'">干垃圾</span>
          <span v-if="scope.row.type ==='2'">湿垃圾</span>
          <span v-if="scope.row.type ==='3'">有害垃圾</span>
          <span v-if="scope.row.type ==='4'">无法识别</span>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" prop="status">
        <template slot-scope="scope">
          <span v-if="scope.row.status ==='0'">未审核</span>
          <span v-if="scope.row.status ==='1'">已审核</span>
        </template>
      </el-table-column>
      <el-table-column prop="matched" label="匹配度"/>
      <el-table-column prop="userName" label="上传用户"/>
      <el-table-column prop="point" label="奖励积分"/>
      <el-table-column prop="evaluate" label="评价内容"/>
      <el-table-column prop="createTime" label="上传时间"/>
      <el-table-column label="操作" align="center" width="300">
        <template slot-scope="scope">
          <el-button type="primary" v-if="scope.row.status === '0'" size="mini" icon="el-icon-edit" @click="updateAndSave(scope.row)">审核</el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
        title="审核"
        :visible.sync="dialogVisible"
        width="60%"
    >
      <el-form :model="formData" label-position="left" label-width="100px">
        <el-form-item label="垃圾名称">
          <el-input v-model="formData.title" :disabled="true" placeholder="请填写垃圾名称"/>
        </el-form-item>
        <el-form-item label="奖励积分">
          <el-input-number v-model="formData.point" placeholder="请填写奖励积分"/>
        </el-form-item>
        <el-form-item label="评论">
          <el-input  type="textarea"
                    :rows="2" v-model="formData.evaluate" placeholder="请填写评价内容"/>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleBtn">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 分页 -->
    <el-pagination
        :current-page="page"
        style="padding: 30px 0; text-align: center;"
        :page-size="limit"
        layout="total, prev, pager, next, jumper"
        :total="total"
        @current-change="getList"
    />
  </div>
</template>
<script>
import Tinymce from '@/components/Tinymce'
import API from '../../utils/request'

const url = "/garbage/"
export default {
  components: {Tinymce},
  data() { // 定义变量和初始值
    return {
      list: null, // 查询之后接口返回集合
      dataVo: {}, // 条件封装对象
      page: 1, // 当前页
      limit: 9, // 每页记录数
      total: 100, // 总页数
      dialogVisible: false,
      formData: {},
      title: '',
      fileList: [],
    }
  },
  created() { // 页面渲染之前执行，一般调用method定义方法
    this.getList()
  },
  methods: { // 创建具体的方法, 定义的方法
    // 查询
    getList(page = 1) {
      this.page = page // 获取用户点击的页码赋值

      API.post(url+`pageQuery/${this.page}/${this.limit}`, this.dataVo).then(response => {
        this.list = response.list.records
        this.total = response.list.total
      })
    },

    // 修改和更新功能
    updateAndSave(row) {
      this.formData = row
      this.dialogVisible = true
    },
    // 上传头像成功
    fileHandleSuccess(response) {
      this.formData.image = response.row;
      this.$refs.upload.clearFiles() //去掉文件列表
    },
    // 确认按钮
    handleBtn() {
      // 关闭弹窗
      this.dialogVisible = false
      if (this.formData.id) {
        API.post(url+"audit", this.formData)
            .then(response => { // 请求成功
              this.$message({
                type: 'success',
                message: '修改成功！'
              })
              this.getList()
            })
      } else {
        API.post(url+"save", this.formData)
            .then(response => { // 请求成功
              this.$message({
                type: 'success',
                message: '新增成功！'
              })
              this.getList()
            })
      }
    },

    // 删除
    removeDataById(id) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'

      }).then(() => { // 确定执行的方法
        API.delete(url+"delete/"+id)
            .then(response => { // 删除成功执行的方法
              if (response.code === 200) {
                this.$message({
                  type: 'success',
                  message: '删除成功！'
                })
                // 刷新表格
                this.getList()
              } else {
                this.$message({
                  type: 'error',
                  message: '删除失败！'
                })
              }
            })
      })
    },
    // 清空按按钮执行的方法
    resetData() {
      // 第一步清空条件数据
      this.dataVo = {};
      this.page = 1; // 当前页
      this.limit = 10; // 当前页
      this.getList()
    }
  }
}
</script>
<style scoped>
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
  border-style: dotted;
  margin: 10px 0;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
