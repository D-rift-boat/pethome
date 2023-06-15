<template>
  <section>
    <!--查询工具条-->
    <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
      <el-form :inline="true" :model="filters">
        <el-form-item>
          <el-input v-model="filters.name" placeholder="部门名称"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" v-on:click="getEmployee">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleAdd">新增</el-button>
        </el-form-item>
      </el-form>
    </el-col>

    <!--展示列表-->
    <el-table :data="employees" highlight-current-row v-loading="listLoading" @selection-change="selsChange"
              style="width: 100%;">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column type="index" width="160">
      </el-table-column>
      <el-table-column prop="username" label="员工姓名" width="100" sortable>
      </el-table-column>
      <el-table-column prop="phone" label="电话" width="100" sortable>
      </el-table-column>
      <el-table-column prop="email" label="邮箱" width="100" sortable>
      </el-table-column>
      <el-table-column prop="salt" label="salt加密" width="100" sortable>
      </el-table-column>
      <el-table-column prop="password" label="密码" width="100" sortable>
      </el-table-column>
      <el-table-column prop="age" label="年龄" width="100" sortable>
      </el-table-column>
      <el-table-column label="员工状态" prop="state" width="100" sortable>
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.state != '0' ? 'success' :  'danger'"
              disable-transitions
              effect="light">
            {{ scope.row.state == '0' ? "离职" : "在职" }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="department.name" label="部门" min-width="80" sortable>
      </el-table-column>
      <el-table-column prop="loginInfo.username" label="登录账户" min-width="100" sortable>
      </el-table-column>

      <el-table-column label="登录账户权限" prop="loginInfo.type" width="100" sortable>
        <template slot-scope="scope">
          <el-tag
              :type="scope.row.loginInfo.type != '0' ? 'success' :  'danger'"
              disable-transitions
              effect="light">
            {{ scope.row.loginInfo.type == '0' ? "用户" : "管理员" }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="shop.name" label="店铺" min-width="100" sortable>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template scope="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--分页工具条-->
    <el-col :span="24" class="toolbar">
      <el-button :disabled="this.sels.length===0" type="danger" @click="batchRemove">批量删除</el-button>
      <el-pagination
          :current-page="currentPage"
          :page-size="pageSize"
          :page-sizes="[5, 10, 20, 50]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          style="float:right;"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange">
      </el-pagination>
    </el-col>

    <!--编辑 & 新增界面-->
    <el-dialog :title="title" :visible.sync="editFormVisible" :close-on-click-modal="false">
      <el-form :model="addForm" label-width="80px" :rules="editFormRules" ref="addForm">
        <el-form-item label="员工姓名" prop="username">
          <el-input v-model="addForm.username" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="电话" prop="phone">
          <el-input v-model="addForm.phone" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="邮箱" prop="email">
          <el-input v-model="addForm.email" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="salt加密" prop="salt">
          <el-input v-model="addForm.salt" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input v-model="addForm.password" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="年龄" prop="age">
          <el-input v-model="addForm.age" auto-complete="off"></el-input>
        </el-form-item>

        <el-form-item label="员工状态" prop="state">
          <el-radio-group v-model="addForm.state">
            <el-radio class="radio" :label="1">启用</el-radio>
            <el-radio class="radio" :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="所属部门">
          <div class="block">
            <el-cascader
                v-model="addForm.departmentId"
                :options="options"
                :props="{
                  checkStrictly: true,
                  value: 'id',
                  label: 'name'
                }"
                clearable></el-cascader>
          </div>
        </el-form-item>

        <el-form-item label="登录信息账户" prop="logininfoId">
          <el-select v-model="addForm.logininfoId" placeholder="请选择登录账户">
            <el-option
                v-for="item in loginInfos"
                :key="item.id"
                :label="item.username"
                :value="item.id">
              <span style="float: left">{{ item.username }}</span>
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="店铺" prop="shopId">
          <el-select v-model="addForm.shopId" placeholder="请选择公司">
            <el-option
                v-for="item in shops"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              <span style="float: left">{{ item.name }}</span>
            </el-option>
          </el-select>
        </el-form-item>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click.native="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="addSubmit" :loading="editLoading">提交</el-button>
      </div>

    </el-dialog>

  </section>
</template>

<script>
import util from '../../common/js/util'

export default {
  data() {
    return {
      title: '',
      filters: {
        keyword: ''
      },
      departments: [],
      shops: [],
      loginInfos: [],
      total: 0,
      currentPage: 1,
      pageSize: 5,
      listLoading: false,
      //列表选中列
      sels: [],
      //编辑界面是否显示
      editFormVisible: false,
      editLoading: false,
      //无限极树参数
      options: [],

      editFormRules: {
        username: [
          {required: true, message: '请输入员工姓名', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入员工密码', trigger: 'blur'}
        ]
      },
      employees: [],
      //编辑界面数据
      addForm: {
        id: null,
        username: null,
        phone: '',
        email: '',
        salt: '',
        password: '',
        age: null,
        state: null,
        departmentId: null,
        logininfoId: null,
        shopId: null,
      },
    }
  },
  methods: {
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
      this.getEmployee()
    },
    //选择每页显示记录条数
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.getEmployee()
    },

    //删除单个
    handleDel: function (index, row) {
      this.$confirm('确认删除该记录吗?', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true;
        this.$http.delete('/employee/' + row.id).then((res) => {
          this.listLoading = false;
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.getEmployee()
        });
      }).catch(() => {
        this.$message({
          message: '操作取消',
          type: 'false'
        });
      });
    },

    //点击编辑
    handleEdit(row) {
      this.title = '编辑员工信息'

      //把当前行数据赋值给 addFrom，需要使用深拷贝（地址值不一样，其他属性都一样）
      this.addForm = Object.assign({}, row);
      //显示编辑框
      this.editFormVisible = true;
    },
    //点击新增
    handleAdd() {
      this.title = '新增员工'
      //清空新增框中的默认数据
      this.addForm = {
        id: null,
        username: null,
        phone: '',
        email: '',
        salt: '',
        password: '',
        age: null,
        state: null,
        departmentId: null,
        logininfoId: null,
        shopId: null,
      }
      //显示编辑框
      this.editFormVisible = true;
    },
    //编辑
    addSubmit: function () {
      this.$refs.addForm.validate((valid) => {
        if (valid) {

          this.$confirm('确认提交吗？', '提示', {}).then(() => {
            //点击提交后  未接受响应前  提交按钮处于加载等待加载状态
            this.editLoading = true;

            //this.addForm.parentId 值为数组(级联导致)，需要取出其真正父级部门id   取数组末位即可
            //前端级联   回显时 其父级部门id是单个值   所以需要先将其统一设为数组进行处理
            console.log(5555555555555555)
            console.log(this.addForm.departmentId)
            let dptId = []
            this.addForm.departmentId instanceof Array ? dptId = this.addForm.departmentId : dptId.push(this.addForm.departmentId)

            this.addForm.departmentId = dptId[dptId.length-1]
            //at取 数组末尾的方法  数组为null时  数据为undified  会报错
            // this.addForm.departmentId = dptId.at(-1)
            //是数组  则赋值给parent数组   不是数组则存入parent数组
            console.log(this.addForm.departmentId)
            console.log(6666666666666666666)

            let request = this.$http.put('/employee', this.addForm)
            request.then((res) => {
              if (res.data.success) {
                this.$message({
                  message: res.data.message,
                  type: 'success'
                });
              } else {
                this.$message({
                  message: res.data.message,
                  type: 'error'
                });
              }
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '系统繁忙，请联络管理员'
              });
            });
            //响应完毕 关闭编辑窗口
            this.editFormVisible = false;
            this.getEmployee();
            //接受服务器响应/未响应后 结束等待提交状态
            this.editLoading = false;
          });
        } else {
          //校验失败
          return false;
        }
      });
    },
    //复选框
    selsChange: function (val) {
      this.sels = [];
      if (val != null && val.length > 0) {
        for (var i = 0; i < val.length; i++) {
          //往sels数组中存值
          this.sels.push(val[i].id);
        }
      }
    },
    //批量删除
    batchRemove: function () {
      var ids = this.sels.map(item => item.id).toString();
      this.$confirm('确认删除选中记录吗？', '提示', {
        type: 'warning'
      }).then(() => {
        this.listLoading = true;
        this.$http.patch('/employee', this.sels).then((res) => {
          this.listLoading = false;
          this.$message({
            message: '删除成功',
            type: 'success'
          });
          this.getEmployee();
        });
      }).catch(() => {
        this.$message({
          message: '操作取消',
          type: 'error'
        });
      });
    },
    //获取无限极树部门列表  用于回显
    getDptTree() {
      this.$http.get('/department/dptTree').then((res) => {
        this.options = res.data;
      });
    },
    //获取员工表
    getEmployee() {
      let para = {
        currentPage: this.currentPage,
        pageSize: this.pageSize,
        keyword: this.filters.keyword
      };
      this.listLoading = true;
      this.$http.post('/employee',para).then((res) => {
        this.total = res.data.total
        this.employees = res.data.list;
        this.listLoading = false;

      });
    },
    //获取店铺表
    getShop() {
      this.$http.get('/shop').then((res) => {
        this.shops = res.data
      });
    },
    //获取登录账户表
    getLoginInfo() {
      this.$http.get('/logininfo').then((res) => {
        this.loginInfos = res.data
      });
    },
  },
  mounted() {
    this.getEmployee()
    this.getDptTree()
    this.getShop()
    this.getLoginInfo()
  }
}

</script>

<style scoped>

</style>