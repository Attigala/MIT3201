<template>
  <div>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-success">
      <!-- Card stats -->
      <b-row>
      </b-row>
    </base-header>
    <b-container fluid class="mt--7">
      <b-row>
        <b-col>
      <div class="unpaidList">
          <b-card no-body class="bg-default shadow">
        <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Yearly Statistics: {{year}}</h3>
        </b-card-header>

        <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="classStatistics">
            <el-table-column label="Class ID"
                             min-width="310px"
                             prop="classId">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.classId}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>

            <el-table-column label="Teacher"
                             min-width="170px"
                             prop="medium">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.teacherName}}</span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Subject"
                             min-width="170px"
                             prop="medium">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.subject}}</span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Medium"
                             min-width="170px"
                             prop="medium">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.medium}}</span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Grade"
                             min-width="170px"
                             prop="grade">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.grade}}</span>
                    </badge>
                </template>
            </el-table-column>
        </el-table>
        <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="classStatistics">
            <el-table-column label="Class ID"
                             min-width="310px"
                             prop="classId">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.classId}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>

            <el-table-column label="Total Students"
                             min-width="170px"
                             prop="totalStudents">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.totalStudents}}</span>
                    </badge>
                </template>
            </el-table-column>
        </el-table>
        <h3 class="mb-0 text-white"> Monthly Enrollment Statistics </h3>
        <div v-for="statistic in classStatistics" :key="statistic.classId">
        <h3 class="mb-0 text-white"> Class {{statistic.subject}} {{statistic.medium}} {{statistic.grade}} By  {{statistic.teacherName}} </h3>
          <table class="table">
<thead class="thead-light">
<tr>
<th scope="col"> Month </th>
<th scope="col"> Total </th>
</tr>
</thead>
<tbody>

<tr>
<td scope row> January </td>
<td> {{statistic.totalPerMonth.January}} </td>
</tr>
<tr>
<td scope row> February </td>
<td> {{statistic.totalPerMonth.February}} </td>
</tr>
<tr>
<td scope row> March </td>
<td> {{statistic.totalPerMonth.March}} </td>
</tr>
<tr>
<td scope row> April </td>
<td> {{statistic.totalPerMonth.April}} </td>
</tr>
<tr>
<td scope row> May </td>
<td> {{statistic.totalPerMonth.May}} </td>
</tr>
<tr>
<td scope row> June </td>
<td> {{statistic.totalPerMonth.June}} </td>
</tr>
<tr>
<td scope row> July </td>
<td> {{statistic.totalPerMonth.July}} </td>
</tr>
<tr>
<td scope row> August </td>
<td> {{statistic.totalPerMonth.August}} </td>
</tr>
<tr>
<td scope row> September </td>
<td> {{statistic.totalPerMonth.September}} </td>
</tr>
<tr>
<td scope row> October </td>
<td> {{statistic.totalPerMonth.October}} </td>
</tr>
<tr>
<td scope row> November </td>
<td> {{statistic.totalPerMonth.November}} </td>
</tr>
</tbody>
</table>
<br/>
      </div>
    </b-card>
    </div>
        </b-col>
      </b-row>
      <div class="mt-5"></div>
    </b-container>
  </div>
</template>
<script>
import GuardianDataService from '../services/GuardianDataService'
import ClassPaymentDataService from '../services/ClassPaymentDataService'
import classContractDataService from '../services/ClassContractDataService'
import { Table, TableColumn} from 'element-ui'
export default {
  name: 'ClassYearlyStatisticsReport',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      classStatistics: [],
      year: new Date().getFullYear(),
      role: null,
      user: null
    }
  },
  methods: {
    retrieveStatistics () {
      classContractDataService.getYearlyStatistics().then(response => {
        this.classStatistics = response.data
        console.log(this.classStatistics)
      }).catch(e => {
        console.log(e)
      })
    }
  },
  mounted () {
    this.user = localStorage.getItem("email")
    if(localStorage.getItem("role") === null || localStorage.getItem("user") === null ){
        this.$router.push({ name: 'login' })
    }
    if(localStorage.getItem("role") !== "manager"){
       this.$router.push({ name: 'login' })
    }

    this.retrieveStatistics()

  }
}
</script>
