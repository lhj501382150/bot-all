<template>
  <div class="page-container">
	<!--工具栏-->
	<div class="toolbar" style="float:left;padding-top:10px;padding-left:15px;">
		<el-form :inline="true" :model="filters" :size="size">
			<el-form-item>
        <el-date-picker
          v-model="filters.fdate"
          type="datetimerange"
          align="right"
          unlink-panels
          range-separator="至"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          value-format="yyyy-MM-dd HH:mm:ss">
        </el-date-picker>
			</el-form-item>
      <el-form-item>
        <el-input v-model="filters.username" placeholder="分公司名称" clearable></el-input>
      </el-form-item>
			<el-form-item>
				<kt-button icon="fa fa-search" :label="$t('action.search')" perms="operations:levelResult:view" type="primary" @click="findPage(null)"/>
        <kt-button icon="fa fa-download" :label="$t('action.export')" perms="operations:levelResult:view" type="primary" @click="exportExcel"/>
        <ExportExcel ref="export" :columns="columns" :table-data="exportData" :info="exportInfo"></ExportExcel>
			</el-form-item>
		</el-form>
	</div>
	<!--表格内容栏-->
	<kt-table
    :data="pageResult" :columns="columns" :showOperation="showOperation" @findPage="findPage">
    <template #loss="scope">
      {{ scope.row.LOSS-scope.row.COMM }}
    </template>
	</kt-table>
  </div>
</template>

<script>
import KtTable from "@/views/Core/KtTable"
import KtButton from "@/views/Core/KtButton"
import { format,getCurrentDate } from "@/utils/datetime"
import ExportExcel from "@/views/Core/ExportExcel"
export default {
	components: {
		KtTable,
		KtButton,
    ExportExcel
	},
	data() {
		return {
			size: 'small',
      acctlist: [],//账本列表
			filters: {
        fdate: [this.getStartDate(),this.getEndDate()],
        username:'',
			},
			columns: [
				{prop:"USERNO", label:"分公司编号", minWidth:120},
				{prop:"username", label:"分公司名称", minWidth:120},
        {prop:"NUM", label:"注数", minWidth:120},
        {prop:"BAILMONEY", label:"下注金额", minWidth:100},
        {prop:"REALMONEY", label:"有效金额", minWidth:120},
        {prop:"COMM", label:"退水", minWidth:100},
        {prop:"LOSS", label:"盈亏", minWidth:100}
			],
			pageRequest: { pageNum: 1, pageSize: 50 },
      pageResult: {},
      showOperation:false,
      exportData:[],
      exportInfo:{
        id:'record-table',
        name:'分公司统计.xlsx'
      }
		}
	},
	methods: {
		// 获取分页数据
		findPage: function (data) {
			if(data !== null) {
				this.pageRequest = data.pageRequest
			}else{
        this.pageRequest = {pageNum: 1, pageSize: 50}
      }
			this.pageRequest.params = {
        'btime':this.filters.fdate == null ? '' : this.filters.fdate[0],
        'etime':this.filters.fdate == null ? '' : this.filters.fdate[1],
        'username':this.filters.username
			}
			this.$api.order.findLevelCount(this.pageRequest).then((res) => {
				this.pageResult = res.data
			}).then(data!=null?data.callback:'')
		},
    exportExcel(){
      let para = { pageNum: 1, pageSize: 10000000 }
      para.params = {
        'btime':this.filters.fdate == null ? '' : this.filters.fdate[0],
        'etime':this.filters.fdate == null ? '' : this.filters.fdate[1],
        'username':this.filters.username
			}
      this.$api.order.findLevelCount(para).then((res) => {
        this.exportData = res.data.content
        setTimeout(()=>{
          this.$refs.export.exportExcel();
        },500)
      })
    },
    getStartDate(){
      let startTime = getCurrentDate() + ' 07:00:00'
      let sdate = new Date(startTime)
      return sdate
    },
    getEndDate(){
      let startTime = getCurrentDate() + ' 07:00:00'
      let sdate = new Date(startTime)
     let edate = Date.parse(new Date(startTime)) + 1000 * 60 * 60 * 23
     return new Date(edate)
    }

	},
	mounted() {
	}
}
</script>

<style scoped>

</style>
