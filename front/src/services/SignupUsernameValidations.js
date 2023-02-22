import Validations from './Validations'

export default class SignupUsernameValidations {
    
    constructor(username) {
        this.username = username
    }

    checkValidations() {
        let errors = [];
        // 아이디 네이밍 규칙
        // console.log(this.username)
        if (!Validations.idNaming(this.username) ) {
            errors['username'] = '아이디는 영문, 숫자만 가능하며 2 ~ 12자리까지 가능합니다.'
        }
        return errors;
    }
}