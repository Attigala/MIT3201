import http from '../http-common'

class ClassContractDataService {
  // Retrieve all class contracts from the database
  getAll () {
    return http.get('/classContracts')
  }

  getByMediumOrGrade (medium, grade) {
    return http.get('/classContracts/filter?medium=' + medium + '&grade=' + grade)
  }

  filterClasses(subject, medium, grade) {
    return http.get('/classContracts/filter?subject='+subject+'&grade='+grade+'&medium='+medium)
  }

  create (classContract) {
    return http.post('/classContracts', classContract)
  }

  createStudentClass (studentClass) {
    return http.post('/studentClass', studentClass)
  }

  getYearlyStatistics () {
    return http.get('/studentClass/yearlyStatistics')
  }

  getClassesByStudentId (studentId) {
    return http.get('/studentClass/student?studentId=' + studentId)
  }

  getClassesByTeacher (teacher) {
    return http.post('/classByTeacher', teacher)
  }
  getOverview (email) {
    return http.get('/classContracts/teacher?teacher=' + email)
  }
  saveFile (classId, file, headers) {
    return http.post('/studyMaterial/upload?classId='+classId, file, headers)
  }
  getFiles (classId) {
    return http.get('/studyMaterial/all?classId='+classId)
  }

  createClassRequest(classRequest) {
    return http.post('/classContracts/request', classRequest)
  }

  getClassRequestsForClass(classId) {
    return http.get('/classContracts/request/class?classId='+classId)
  }

  getClassRequestsForTeacher(email) {
    return http.get('/classContracts/request/teacher?username='+email)
  }

  getClassRequests() {
    return http.get('/classContracts/unscheduled')
  }

  getClassRequestSuggestion(requestId) {
    return http.get('/classContracts/suggestions?requestId='+requestId)
  }

  saveMaualOnsiteExtraClass(request) {
    return http.post('/classContracts/onsite/manual', request)
  }

  test(){
    alert("test")
  }

  saveManualOnlineExtraClass(request) {
    return http.post('/classContracts/online/manual', request)
  }

  getStudentOverview(email) {
    return http.get('/classContracts/student?student=' + email)
  }

  getAssignments(classId) {
    return http.get('/assignment/all?classId='+classId)
  }

  saveAssignment(classId, duedate, file, headers) {
    return http.post('/assignment/upload?classId='+classId+'&date='+duedate, file, headers)
  }

  saveMarksSheet( file, headers ) {
    return http.post('/assignment/marks/upload',file, headers)
  }
  savePayment(studentId, classId, month, file, headers){
    return http.post('/reciept/upload?classId='+classId+'&studentId='+studentId+"&month="+month,file,headers)
  }

  getAllNewPayments() {
    return http.get('/reciept/all/new')
  }

  downloadReciept(id) {
    return http.get('/reciept/all/'+id)
  }

  changeRecieptStatus(id, status){
    return http.post('/reciept/update?id='+id+'&status='+status)
  }

  getRejected(email){
    return http.get('/reciept/all/rejected?email='+email)
  }

  saveSubmission(classId, studentId, assignmentId, file, headers) {
    return http.post('/assignmentSubmission/upload?classId='+classId+'&studentId='+studentId+'&assignmentId='+assignmentId,file,headers)
  }

  getSubmittedAssignmentForStudent(studentId, assignmentId) {
    return http.get('/assignmentSubmission/submitted?studentId='+studentId+'&assignmentId='+assignmentId)
  }
  getAssignmentSubmissions(assignmentId) {
    return http.get('/assignmentSubmission/all?assignmentId='+assignmentId)
  }

  getNotSubmittedStudents(assignmentId) {
    return http.get('/assignmentSubmission/not?assignmentId='+ assignmentId)
  }

  getLateSubmissionStudents(assingmentId) {
    return http.get('/assignmentSubmission/late?assignmentId='+assingmentId)
  }

  suspendGuardian(guardianId) {
    return http.put('/guardian/suspend/all?id='+guardianId)
  }

  suspendStudentClass(studentId, classContractId) {
    return http.put('/guardian/suspend/studentClass?studentId='+studentId+'&classContractId='+classContractId)
  }

  subscribeToBeNotified(request) {
    return http.post('/classContracts/notify', request)
  }

  getStudentsInClass(classId) {
    return http.get('/studentClass/classContract?classId='+classId)
  }

  getMarksForAssignment(assignmentId){
    return http.get('/assignmentSubmission/mark/all?assignmentId='+assignmentId)
  }

  saveMark(mark) {
    return http.post('/assignentSubmission/mark', mark)
  }
  getMarksForStudent(email) {
    return http.get('/assignmentSubmission/mark/student?email='+email)
  }
  getRequested() {
    return http.get('/classContracts/requested')
  }
  getRequestedPersons(){
    return http.get('/classContracts/notify')
  }

  getRequestsForYear() {
    return http.get("/classContracts/notify/year")
  }
  saveMessageToRequester(request) {
    return http.post("/classContracts/notify/message", request)
  }

  getPendingAssignments(email) {
    return http.get("/studentClass/assignment/pending?email=" + email)
  }

  getStudentStatus(classId) {
    return http.get("/classContracts/students/status?classId="+classId)
  }
}
export default new ClassContractDataService()
