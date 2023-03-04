import http from '../http-common'

class ForumDataService {
  // Retrieve all class forum from the database
  getQuestions (classId) {
    return http.get('/forum/question?classId='+classId)
  }

  addQuestion (question) {
    return http.post('forum/question', question)
  }

  getAnswers(questionId) {
    return http.get('/forum/answer?questionId='+questionId)
  }

  addAnswer (answer) {
    return http.post('forum/answer', answer)
  }

}
export default new ForumDataService()
