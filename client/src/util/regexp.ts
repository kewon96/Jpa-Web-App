const memberNameReg = /^[가-힣]{2,4}|[a-zA-Z]{2,15}$/;
const emailReg = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
const passwordReg = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,30}$/;

export { memberNameReg, emailReg, passwordReg };