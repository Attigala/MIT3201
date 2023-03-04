import http from '../http-common'

class ClassPaymentDataService {
  savePayment (classPayment) {
    return http.post('/classPayments', classPayment)
  }

  getUnpaidForGuardian(email) {
    return http.get('/classPayments/unpaid/guardian?email='+email)
  }

  getUnpaidForMonth(){
    return http.get('/classPayments/unpaid/currentMonth')
  }

  getSuspendedGuardians() {
    return http.get('/guardian/suspended')
  }
  getSuspendedStudentClasses(){
    return http.get('/studentClass/suspended')
  }

  getYearlyStatistics () {
    return http.get('/studentClass/yearlyStatistics')
  }
}
export default new ClassPaymentDataService()
