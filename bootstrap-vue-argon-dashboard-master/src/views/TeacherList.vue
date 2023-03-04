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
      <div class="hallList">
          <b-card no-body class="bg-default shadow">
        <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Contracted Teachers</h3>
        </b-card-header>

        <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="teachers">
            <el-table-column label="Teacher Name"
                             min-width="310px"
                             prop="name">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.firstName}} {{row.lastName}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>

            <el-table-column label="Contact Number"
                             min-width="170px"
                             prop="status">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.contactNumber}}</span>
                    </badge>
                </template>
            </el-table-column>

            <el-table-column label="Email"
                             prop="email"
                             min-width="240px">
                <template v-slot="{row}">
                    <div class="d-flex align-items-center">
                        <span class="completion mr-2">{{row.email}}</span>
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
import TeacherDataService from '../services/TeacherDataService'
  import { Table, TableColumn} from 'element-ui'
export default {
  name: 'guardianList',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      teachers: []
    }
  },
  methods: {
    retrieveTeachers () {
      TeacherDataService.getAll().then(response => {
        this.teachers = response.data
        console.log(response.data)
      })
        .catch(e => {
          console.log(e)
        })
    }
  },
  mounted () {
    if(localStorage.getItem("role") !== "admin" && localStorage.getItem("role") !=="manager" ){
       this.$router.push({ path: "/NotFound" })
    }
    this.retrieveTeachers()
  }
}
</script>
