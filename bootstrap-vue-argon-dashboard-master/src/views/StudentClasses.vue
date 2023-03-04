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
      <div class="studentClassList">
          <b-card no-body class="bg-default shadow">
        <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Student Enrollments</h3>
        </b-card-header>

        <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="studentClasses">
            <el-table-column label="Full Name"
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
            <el-table-column label="subject"
                             prop="subject"
                             min-width="140px">
            </el-table-column>

            <el-table-column label="Contact Number"
                             min-width="170px"
                             prop="medium">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.medium}}</span>
                    </badge>
                </template>
            </el-table-column>

            <el-table-column label="Grade"
                             prop="grade"
                             min-width="240px">
                <template v-slot="{row}">
                    <div class="d-flex align-items-center">
                        <span class="completion mr-2">{{row.grade}}</span>
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="Grade"
                             prop="grade"
                             min-width="240px">
                <template v-slot="{row}">
                    <div class="d-flex align-items-center">
                        <base-button type="primary" @click="editRow(row)">Un-Enroll</base-button>
                    </div>
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
  import { Table, TableColumn} from 'element-ui'
export default {
  name: 'guardianList',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      studentClasses: [],
      role: null,
      user: null
    }
  },
  methods: {
    retrieveGuardians () {
      GuardianDataService.getClassesByGuardianEmail(localStorage.getItem("user")).then(response => {
        this.studentClasses = response.data
        console.log(response.data)
      })
        .catch(e => {
          console.log(e)
        })
    },
    editRow(row){
        if(confirm("Are you sure, that you wish to un-enroll " + row.studentName + "from this class?")) {
            GuardianDataService.deleteStudentFromClass(row).then(response => {
              alert("Removed from class")
              console.log(response)
            })
          .catch(e => {
            console.log(e)
           })
        } else {
            console.log("pressed no");
        }
    }
  },
  mounted () {
    this.user = localStorage.getItem("email")
    if(localStorage.getItem("role") === null || localStorage.getItem("user") === null ){
        this.$router.push({ name: 'login' })
    }
    if(localStorage.getItem("role") !== "guardian"){
       this.$router.push({ name: 'login' })
    }

    this.retrieveGuardians()

  }
}
</script>
