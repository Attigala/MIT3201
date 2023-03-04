<template>
  <div>
    <!-- Header -->
    <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9">
      <b-container>
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white">Search Classes</h1>
            </b-col>
          </b-row>
        </div>
      </b-container>
      <div class="separator separator-bottom separator-skew zindex-100">
        <svg x="0" y="0" viewBox="0 0 2560 100" preserveAspectRatio="none" version="1.1"
             xmlns="http://www.w3.org/2000/svg">
          <polygon class="fill-default" points="2560 0 2560 100 0 100"></polygon>
        </svg>
      </div>
    </div>
    <!-- Page content -->
    <b-container class="mt--8 pb-5">
      <b-row class="justify-content-center">
        <b-col lg="5" md="7">
          <b-card no-body class="bg-secondary border-0 mb-0">
            
            <b-card-body class="px-lg-5 py-lg-5">
              <div class="text-center text-muted mb-4">
              </div>
              <validation-observer v-slot="{handleSubmit}" ref="formValidator">
                <b-form role="form" @submit.prevent="handleSubmit(onSubmit)">
                  <base-input alternative
                              class="mb-3"
                              name="subject"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Subject"
                              v-model= "classSearch.subject">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="grade"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Grade"
                              v-model="classSearch.grade">
                  </base-input>

                  <base-input alternative
                              class="mb-3"
                              name="medium"
                              :rules="{required: true}"
                              prepend-icon="ni ni-building"
                              placeholder="Medium"
                              v-model="classSearch.medium">
                  </base-input>
                  <div class="text-center">
                    <base-button type="primary" native-type="submit" class="my-4">Search</base-button>
                  </div>
                </b-form>
              </validation-observer>
            </b-card-body>
          </b-card>
          <b-row class="mt-3">
           
          </b-row>
        </b-col>
      </b-row>
    </b-container>
     <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="classContracts">
            <el-table-column label="Subject"
                             min-width="310px"
                             prop="name">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.subject}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>
            <el-table-column label="Medium"
                             min-width="310px"
                             prop="medium">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.medium}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>
            <el-table-column label="Grade"
                             min-width="310px"
                             prop="grade">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.grade}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>
            <el-table-column label="day"
                             min-width="170px"
                             prop="day">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="status">{{row.day}}</span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Time Slot"
                             min-width="310px"
                             prop="timeSlot">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.timeSlot}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>
        </el-table>
        <h3 class="text-white"> Subscribe for class notifications </h3>
        <validation-observer v-slot="{handleSubmit}" ref="formValidator">
                <b-form role="form" @submit.prevent="handleSubmit(subscribe)">
                  <base-input alternative
                              class="mb-3"
                              name="subject"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Subject"
                              v-model= "classRequest.subject">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="grade"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Grade"
                              v-model="classRequest.grade">
                  </base-input>

                  <base-input alternative
                              class="mb-3"
                              name="medium"
                              :rules="{required: true}"
                              prepend-icon="ni ni-building"
                              placeholder="Medium"
                              v-model="classRequest.medium">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="Name"
                              :rules="{required: true}"
                              prepend-icon="ni ni-building"
                              placeholder="Name"
                              v-model="classRequest.name">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="email"
                              :rules="{required: true}"
                              prepend-icon="ni ni-building"
                              placeholder="Email"
                              v-model="classRequest.email">
                  </base-input>
                  <div class="text-center">
                    <base-button type="primary" native-type="submit" class="my-4">Subscribe</base-button>
                  </div>
                </b-form>
              </validation-observer>
  </div>
</template>
<script>
import teacherDataService from '../../services/TeacherDataService'
import classContractDataService from '../../services/ClassContractDataService'
import { Table, TableColumn} from 'element-ui'
export default{
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  name: 'classContract',
  data () {
    return {
      classContracts: [],
      medium: '',
      subject: '',
      grade: '',
      classSearch: {
        grade: '',
        medium: '',
        subject: ''
      },
      classRequest: {
        grade: '',
        medium: '',
        subject: '',
        email: '',
        name: ''
      }
    }
  },
  methods: {
    onSubmit () {

        var subject = this.classSearch.subject
        var medium = this.classSearch.medium
        var grade = this.classSearch.grade

        classContractDataService.filterClasses(subject, medium, grade).then(response=>{
          this.classContracts = response.data
          if(this.classContracts == 'undefined' ||this.classContracts.length == 0){
            alert("Looks like we do not have that class yet. \nIf you wish to get notified of this class in the future, please enter your details in the form below" )
          }
        }).catch(e=>{
          console.log(e)
        })

    },
    subscribe () {
      var request = {
        subject : this.classRequest.subject,
        medium: this.classRequest.medium,
        grade: this.classRequest.grade,
        name: this.classRequest.name,
        email: this.classRequest.email
      }
      classContractDataService.subscribeToBeNotified(request).then(response => {
        alert("Thank you for subscribing. We will send you an email, once the class is scheduled")
      })
        .catch(e => {
          console.log(e)
        })
    }
  },
  mounted() {

  }
}
</script>
