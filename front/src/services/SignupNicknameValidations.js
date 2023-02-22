import Validations from './Validations'

export default class SignupNicknameValidations {

    constructor( nickname ) {
        this.nickname = nickname;
    }
    
    checkValidations() {
        let errors = [];
        // 닉네임 네이밍 규칙
        if (!Validations.lengthRange(this.nickname, 3, 10) ) {
          errors['nickname'] = '닉네임은 3자 이상 10자 이하여야합니다.'
        }


        return errors;
    }
}