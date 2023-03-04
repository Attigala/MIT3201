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
            <h3 class="mb-0 text-white">Answers For {{question.question}}</h3>
        </b-card-header>

        <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="answers"
                  >
            <el-table-column label="Answer"
                             min-width="310px"
                             prop="answer">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.answer}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>

            <el-table-column label="Answered By"
                             min-width="170px"
                             prop="repliedBy">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span>
                        {{row.repliedBy}}
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
            <h3 class="mb-0 text-white">Answer - {{question.question}} </h3>
            <validation-observer v-slot="{handleSubmit}" ref="formValidator">
                <b-form role="form" @submit.prevent="handleSubmit(onSubmit)">
                  <base-input alternative
                              class="mb-3"
                              name="Answer"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Question"
                              v-model="answer.answer">
                  </base-input>
                  <div class="text-center">
                    <base-button type="primary" native-type="submit" class="my-4">Add Answer</base-button>
                  </div>
                </b-form>
              </validation-observer>
        </b-card-header>
        </b-card> 
       </div>
      </div>
      <base-button type="primary" @click="back" class="my-4">Back to Forum</base-button>
    </b-container>
  </div>
</template>
<script>
import ClassContractDataService from '../services/ClassContractDataService'
import ForumDataService from '../services/ForumDataService'
import { Table, TableColumn} from 'element-ui'
export default {
  name: 'ForumAnswerStudent',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      answers: [],
      question: null,
      answer: {
        id: null,
        question: null,
        answer: '',
        userClass: 'student',
        username: '',
        classId: null
      }
    }
  },
  methods: {
    getAnswers () {
        ForumDataService.getAnswers(this.question.id).then(response=> {
            this.answers = response.data
            console.log(response.data)
        }).catch(e =>{
            console.log(e)
        })
    },
    onSubmit (){
        var answer = {
            question: this.question.id,
            answer: this.answer.answer,
            username: localStorage.getItem("user"),
            userClass: 'student',
            classId: localStorage.getItem("classId")
        }
        ForumDataService.addAnswer(answer).then(response=> {
            this.answer.id = response.data.id
            this.getAnswers()
        }).catch(e => {
            console.log(e)
        })
    },
    back() {
      this.$router.push({name: 'ForumStudent', params: {classInfo: this.classInfo}})
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
    if(this.question === null) {
        this.$router.push({path: "/studentHome"})
    }
    this.getAnswers()
  },
   created () {
    this.question = this.$route.params.question === null ? null : this.$route.params.question
    this.classInfo = this.$route.params.classInfo === null ? null : this.$route.params.classInfo
   }
}
</script>
