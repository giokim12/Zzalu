import Validations from "./Validations";

export default class SignupEmailValidations {
  constructor(email) {
    this.email = email;
  }

  checkValidations() {
    let errors = [];
    // 이메일 네이밍 규칙
    if (!Validations.emailNaming(this.email)) {
      errors["email"] = "올바른 메일주소를 입력해주세요.";
    }
    return errors;
  }
}
