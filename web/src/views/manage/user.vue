<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="用户姓名">
        <el-input v-model="dataVo.name" placeholder="请输入用户姓名"/>
      </el-form-item>
      <el-form-item label="账号状态">
        <el-select v-model="dataVo.status" clearable placeholder="请选择账号状态">
          <el-option value="0" label="禁用"/>
          <el-option value="1" label="正常"/>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">重置</el-button>
      <el-button type="primary" @click="updateAndSave(null)">新增</el-button>
    </el-form>
    <!-- 表格 -->
    <el-table
        :data="list"
        border
        fit
        highlight-current-row
        empty-text="暂无数据"
    >
      <el-table-column align="center" label="头像">
        <template slot-scope="scope">
          <img
              style="width: 80px; height: 80px"
              :src="scope.row.image"/>
        </template>
      </el-table-column>
      <el-table-column prop="username" label="账号"/>
      <el-table-column prop="name" label="姓名"/>
      <el-table-column prop="status" label="账号状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === '0'" type="warning">禁用</el-tag>
          <el-tag v-if="scope.row.status === '1'" type="primary">正常</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="性别">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.sex === '1'" type="primary">男</el-tag>
          <el-tag v-if="scope.row.sex === '0'" type="danger">女</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="注册时间"/>
      <el-table-column label="操作" align="center" width="300">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="updateAndSave(scope.row)">修改</el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 新增修改弹框 -->
    <el-dialog
        :title="title"
        :visible.sync="dialogVisible"
        width="30%"
    >
      <el-form :model="formData" label-position="left" label-width="100px">
        <el-form-item label="账号">
          <el-input v-model="formData.username" :disabled="disabledFlag" placeholder="请填写账号"/>
        </el-form-item>
        <el-form-item v-if="!disabledFlag" label="用户密码">
          <el-input v-model="formData.password" type="password" placeholder="请填写密码"/>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="formData.name" placeholder="请填写姓名"/>
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="formData.sex" placeholder="请选择性别">
            <el-option label="女" value="0"></el-option>
            <el-option label="男" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="头像">
          <el-upload
              ref="upload"
              :file-list="fileList"
              action="http://localhost:8080/files/file"
              :on-success="fileHandleSuccess"
              accept="image/*"
              class="avatar-uploader"
          >
            <img v-if="formData.image" :src="formData.image" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="账号状态">
          <el-select v-model="formData.status" placeholder="请选择账号状态">
            <el-option label="禁用" value="0"></el-option>
            <el-option label="正常" value="1"></el-option>
          </el-select>
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
import API from '../../utils/request'

const url = "/user/"
export default {
  data() { // 定义变量和初始值
    return {
      list: null, // 查询之后接口返回集合
      dataVo: {}, // 条件封装对象
      page: 1, // 当前页
      limit: 9, // 每页记录数
      total: 100, // 总页数
      dialogVisible: false,
      disabledFlag: false,
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
      this.disabledFlag = false
      if (row == null) {
        this.title = '新增用户信息'
        this.formData = {
          image: ''
        }
      } else {
        this.title = '修改用户信息'
        this.formData = row
        this.disabledFlag = true
      }
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
        API.post(url+"update", this.formData)
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
