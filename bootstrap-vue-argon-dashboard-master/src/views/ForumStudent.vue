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
      <div class="ForumQuestion">
          <b-card no-body class="bg-default shadow">
        <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Forum Questions</h3>
        </b-card-header>

        <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="questions"
                  @row-click="answer">
            <el-table-column label="Question"
                             min-width="310px"
                             prop="question">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.question}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>

            <el-table-column label="Asked By"
                             min-width="170px"
                             prop="askedBy">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span>
                        {{row.askedBy}}
                        </span>
                    </badge>
                </template>
            </el-table-column>
        </el-table>

    </b-card>
    </div>
        </b-col>
      </b-row>
      <div class="mt-5">
      <div class="text-center">
      <b-card no-body class="bg-default shadow">
      <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Add Question</h3>
            <validation-observer v-slot="{handleSubmit}" ref="formValidator">
                <b-form role="form" @submit.prevent="handleSubmit(onSubmit)">
                  <base-input alternative
                              class="mb-3"
                              name="Question"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Question"
                              v-model="question.question">
                  </base-input>
                  <div class="text-center">
                    <base-button type="primary" native-type="submit" class="my-4">Add Question</base-button>
                  </div>
                </b-form>
              </validation-observer>
        </b-card-header>
        </b-card> 
       </div>
      </div>
      <base-button type="primary" @click="back" class="my-4">Back to Class</base-button>
    </b-container>
  </div>
</template>
<script>
import ClassContractDataService from '../services/ClassContractDataService'
import ForumDataService from '../services/ForumDataService'
import { Table, TableColumn} from 'element-ui'
export default {
  name: 'ForumStudent',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      questions: [],
      classInfo: null,
      question: {
        id: null,
        question: '',
        userClass: 'teacher',
        username: '',
        classId: null
      }
    }
  },
  methods: {
    getQuestions () {
        ForumDataService.getQuestions(localStorage.getItem("classId")).then(response=> {
            this.questions = response.data
            console.log(response.data)
        }).catch(e =>{
            console.log(e)
        })
    },
    onSubmit (){
        var question = {
            question: this.question.question,
            username: localStorage.getItem("user"),
            userClass: 'student',
            classId: localStorage.getItem("classId")
        }
        ForumDataService.addQuestion(question).then(response=> {
            this.question.id = response.data.id
            this.getQuestions()
        }).catch(e => {
            console.log(e)
        })
    },
    answer(row) {
        console.log("answer method called: ");
        console.log("classInfo:" );
        console.log(this.classInfo);
        this.$router.push({name: 'ForumAnswerStudent', params: {question: row, classInfo: this.classInfo}})
    },
    back() {
      this.$router.push({name: 'StudentClass', params: {classInfo: this.classInfo}})
    }    
  },
  mounted () {
    if(localStorage.getItem("role") !== "student"){
       this.$router.push({ path: "/NotFound" })
    }
    if(localStorage.getItem("user") === null ){
       this.$router.push({ path: "/login" })
    }
    if(localStorage.getItem("classId") === null) {
        this.$router.push({ path: "/studentHome" })
    }
    this.getQuestions()
  },
   created () {
    this.classInfo = this.$route.params.classInfo === null ? null : this.$route.params.classInfo
   }
}
</script>
