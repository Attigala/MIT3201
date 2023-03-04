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
            <h3 class="mb-0 text-white">Suspended Guardians and Students</h3>
        </b-card-header>
        <h3> Guardians </h3>
        <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="suspendedGuardians">
            <el-table-column label="Guardian Name"
                             min-width="170px"
                             prop="medium">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.firstName}} {{row.lastName}}</span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Contact Number"
                             min-width="170px"
                             prop="medium">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.contactNumber}}</span>
                    </badge>
                </template>
            </el-table-column>
        </el-table>
        <h3> Student Classes </h3>
        <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="suspendedStudentClasses">
            <el-table-column label="Student Full Name"
                             min-width="310px"
                             prop="name">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.studentName}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>

            <el-table-column label="Class"
                             min-width="170px"
                             prop="medium">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.classDetails}}</span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Guardian Name"
                             min-width="170px"
                             prop="medium">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.guardianName}}</span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Contact Number"
                             min-width="170px"
                             prop="medium">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.contactNumber}}</span>
                    </badge>
                </template>
            </el-table-column>
        </el-table>

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
  name: 'SuspendedList',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      suspendedGuardians: [],
      suspendedStudentClasses:[],
      role: null,
      user: null
    }
  },
  methods: {
    retrieveGuardians () {
      ClassPaymentDataService.getSuspendedGuardians().then(response => {
        this.suspendedGuardians = response.data
        console.log(response.data)
      })
      .catch(e => {
          console.log(e)
      })
    },
    retrieveStudentClasses() {
        ClassPaymentDataService.getSuspendedStudentClasses().then(response => {
            this.suspendedStudentClasses = response.data
            console.log(response.data)
        })
        .catch(e => {
            console.log(e)
        })
    },
  },
  mounted () {
    this.user = localStorage.getItem("email")
    if(localStorage.getItem("role") === null || localStorage.getItem("user") === null ){
        this.$router.push({ name: 'login' })
    }
    if(localStorage.getItem("role") !== "manager"){
       this.$router.push({ name: 'login' })
    }

    this.retrieveGuardians()
    this.retrieveStudentClasses()

  }
}
</script>
