<template>
  <div class="flex justify-center items-center">
    <div class="content-wrap w-[500px]">
      <div
        class="flex justify-between items-center pb-5 mb-10 border-solid border-b border-gray-200"
      >
        <img class="w-[26px]" src="../assets/on-mark.svg" alt="" />
        <a class="logo flex items-end" href="">
          <span class="mr-2 leading-[16px]">날 위한 운동 메이트</span>
          <img class="inline-block" src="../assets/logo.svg" alt="" />
        </a>
      </div>
      <h3 class="text-4xl text-center mb-6">회원가입</h3>
      <form class="flex flex-col gap-8" @submit.prevent="handleSignup">
        <!-- 아이디 -->
        <div class="form-group">
          <label for="">아이디</label>
          <div class="flex gap-3">
            <input
              class="input-style-h60 w-full flex-1"
              type="text"
              id="user_id"
              v-model="formData.userId"
              placeholder="아이디를 입력하세요"
              required
            />
            <button
              class="btn-b-white"
              @click="checkUserIdAvailability($event)"
            >
              중복확인
            </button>
          </div>
          <p v-if="userIdError" class="error-message">{{ userIdError }}</p>
        </div>

        <!-- 비밀번호 -->
        <div class="form-group">
          <label for="password">비밀번호</label>
          <input
            class="input-style-h60"
            type="password"
            id="password"
            v-model="formData.password"
            placeholder="비밀번호를 입력하세요"
            required
            @input="validatePassword"
          />
          <p v-if="passwordError" class="error-message">{{ passwordError }}</p>
        </div>

        <!-- 비밀번호 확인 -->
        <div class="form-group">
          <label for="passwordConfirm">비밀번호 확인</label>
          <input
            class="input-style-h60"
            type="password"
            id="passwordConfirm"
            placeholder="비밀번호를 입력하세요"
            required
            v-model="confirmPassword"
            @input="validateConfirmPassword"
          />
          <p v-if="passwordConfirmError" class="error-message">
            {{ passwordConfirmError }}
          </p>
        </div>

        <!-- 이름 -->
        <div class="form-group">
          <label for="user_name">이름</label>
          <input
            class="input-style-h60"
            type="text"
            id="user_name"
            v-model="formData.name"
            placeholder="이름을 입력하세요"
            required
          />
        </div>

        <!-- 이메일 -->
        <div class="form-group">
          <label for="email">이메일</label>
          <input
            class="input-style-h60"
            type="email"
            id="email"
            v-model="formData.email"
            placeholder="이메일을 입력하세요"
            required
            @input="validateEmail"
          />
          <p v-if="emailError" class="error-message">
            {{ emailError }}
          </p>
        </div>

        <!-- 성별 -->
        <div class="form-group">
          <label>성별</label>
          <div class="flex items-center">
            <label class="flex-1">
              <input
                type="radio"
                id="gender-male"
                v-model="formData.gender"
                value="M"
              />
              <!-- <span class="radio"></span> -->
              <span
                class="block h-[60px] leading-10 border-solid border-2 border-gray-200 py-2 px-4 rounded-tl-[16px] rounded-bl-[16px] text-center cursor-pointer relative"
              >
                남성
              </span>
            </label>
            <label class="flex-1 ml-[-2px]">
              <input
                type="radio"
                id="gender-female"
                v-model="formData.gender"
                value="F"
              />
              <span
                class="block h-[60px] leading-10 border-solid border-2 border-gray-200 py-2 px-4 text-center cursor-pointer relative"
              >
                여성
              </span>
            </label>
            <label class="flex-1 ml-[-2px]">
              <input
                type="radio"
                id="gender-female"
                v-model="formData.gender"
                value="noSelect"
              />
              <span
                class="block h-[60px] leading-10 border-solid border-2 border-gray-200 py-2 px-4 rounded-tr-[16px] rounded-br-[16px] text-center cursor-pointer relative"
              >
                선택안함
              </span>
            </label>
          </div>
        </div>

        <!-- 닉네임 -->
        <div class="form-group">
          <label for="nickname">닉네임</label>
          <input
            class="input-style-h60"
            type="text"
            id="nickname"
            v-model="formData.nickname"
            placeholder="닉네임을 입력하세요"
            required
          />
          <p v-if="nickError" class="error">{{ nickError }}</p>
        </div>

        <!-- 생년월일 -->
        <div class="form-group">
          <label for="birth">생년월일</label>
          <input
            class="input-style-h60"
            type="date"
            id="birth"
            v-model="formData.birth"
            required
          />
        </div>

        <!-- 주소 -->
        <div class="form-group">
          <label for="address">주소</label>
          <div class="adr-sch flex justify-between gap-2 mb-3">
            <input
              class="input-style-h60 flex-1"
              type="text"
              id="sample6_postCode"
              placeholder="우편번호"
              v-model="formData.postCode"
              required
            />
            <button
              class="input-style-h60 btn-b-black cursor-pointer"
              type="button"
              @click="openAddressSearch"
            >
              주소 검색
            </button>
          </div>
          <input
            class="input-style-h60 mb-3"
            type="text"
            id="sample6_address"
            placeholder="주소"
            v-model="formData.address"
            required
          />
          <div class="flex gap-3">
            <input
              class="input-style-h60"
              type="text"
              id="sample6_extraAddress"
              placeholder="참고항목"
              v-model="formData.extraAddress"
            />
            <input
              class="input-style-h60"
              type="text"
              id="sample6_detailAddress"
              placeholder="상세주소"
              v-model="formData.detailAddress"
              required
            />
          </div>
        </div>

        <!-- 추천받고자 하는 운동 -->
        <div>
          <label class="mb-2 block">추천받고자 하는 운동</label>
          <span class="block mb-3"
            >JustOn을 통해 추천받고 싶으신 운동을 선택해주세요.</span
          >
          <div class="bg-[#f7f7f7] p-5 rounded-[16px] flex gap-4">
            <label for="shoulder">
              <input
                id="shoulder"
                class="chk-box"
                type="checkbox"
                v-model="selectedExercises.shoulder"
              />
              <em class="text-center leading-[24px]"
                ><i class="bi bi-check text-white"></i
              ></em>
              <span class="mb-2">어깨</span>
            </label>
            <label for="leg">
              <input
                id="leg"
                class="chk-box"
                type="checkbox"
                v-model="selectedExercises.leg"
              />
              <em class="text-center leading-[24px]"
                ><i class="bi bi-check text-white"></i
              ></em>
              <span class="mb-2">다리</span>
            </label>
            <label for="abs">
              <input
                id="abs"
                class="chk-box"
                type="checkbox"
                v-model="selectedExercises.abs"
              />
              <em class="text-center leading-[24px]"
                ><i class="bi bi-check text-white"></i
              ></em>
              <span class="mb-2">복부</span>
            </label>
            <label for="chest">
              <input
                id="chest"
                class="chk-box"
                type="checkbox"
                v-model="selectedExercises.chest"
              />
              <em class="text-center leading-[24px]"
                ><i class="bi bi-check text-white"></i
              ></em>
              <span class="mb-2">가슴</span>
            </label>
            <label for="back">
              <input
                id="back"
                class="chk-box"
                type="checkbox"
                v-model="selectedExercises.back"
              />
              <em class="text-center leading-[24px]"
                ><i class="bi bi-check text-white"></i
              ></em>
              <span class="mb-2">등</span>
            </label>
            <label for="arm">
              <input
                id="arm"
                class="chk-box"
                type="checkbox"
                v-model="selectedExercises.arm"
              />
              <em class="text-center leading-[24px]"
                ><i class="bi bi-check text-white"></i
              ></em>
              <span class="mb-2">팔</span>
            </label>
            <label for="stretch">
              <input
                id="stretch"
                class="chk-box"
                type="checkbox"
                v-model="selectedExercises.stretch"
              />
              <em class="text-center leading-[24px]"
                ><i class="bi bi-check text-white"></i
              ></em>
              <span class="mb-2">스트레칭</span>
            </label>
            <label for="cardio">
              <input
                id="cardio"
                class="chk-box"
                type="checkbox"
                v-model="selectedExercises.cardio"
              />
              <em class="text-center leading-[24px]"
                ><i class="bi bi-check text-white"></i
              ></em>
              <span class="mb-2">유산소</span>
            </label>
          </div>
        </div>

        <!-- 약관동의 -->
        <div class="agreement-form">
          <label class="mb-4 block">약관동의</label>
          <!-- 이용약관 -->
          <div class="mb-8">
            <label class="flex">
              <input
                class="chk-box"
                type="checkbox"
                v-model="isTermsAccepted"
              />
              <em class="text-center leading-[24px]"
                ><i class="bi bi-check text-white"></i
              ></em>
              <span class="mb-2">JustOn 이용약관에 동의합니다.</span>
            </label>
            <div
              class="w-[100%] bg-[#f7f7f7] h-[160px] rounded-[16px] p-6 overflow-y-scroll"
            >
              <p class="w-[100%]">
                이용약관 제1조 (목적) 본 약관은 'JustOn'이 제공하는 서비스(이하
                "JustOn")를 이용함에 있어 서비스 제공자(이하 "JustOn")와 이용자
                간의 권리, 의무 및 책임사항을 규명하는 것을 목적으로 합니다.
                제2조 (약관의 효력 및 변경) 1. 본 약관은 서비스 이용자가 가입
                절차를 완료함으로써 효력이 발생합니다. 2. 회사는 약관의 내용을
                변경할 수 있으며, 변경된 약관은 이용자에게 공지하고, 공지 후 7일
                이내에 효력이 발생합니다. 제3조 (서비스의 제공) 1. 회사는 서비스
                이용을 위한 플랫폼을 제공하며, 서비스 이용자는 이를 통해
                제공되는 기능을 이용할 수 있습니다. 2. 회사는 서비스의 내용을
                변경할 수 있으며, 이에 대한 사전 고지를 통해 이용자에게 알려야
                합니다. 제4조 (이용자의 의무) 1. 이용자는 서비스 이용 시, 법령에
                위반되는 행위, 타인의 권리를 침해하는 행위를 하지 않으며,
                서비스의 정상적인 운영을 방해하는 행위를 하지 않아야 합니다. 2.
                이용자는 서비스 이용과 관련하여 제공된 정보나 자료를 본래의 목적
                외에는 사용하지 않아야 합니다. 제5조 (이용 계약의 해지) 1.
                이용자는 언제든지 서비스의 이용 계약을 해지할 수 있습니다. 해지
                방법은 서비스 내에서 제공하는 절차를 통해 이루어집니다. 2.
                회사는 이용자가 본 약관을 위반한 경우 서비스 이용 계약을 해지할
                수 있습니다. 제6조 (면책조항) 1. 회사는 천재지변, 시스템 장애 등
                불가항력적인 사유로 인한 서비스 제공 중단에 대해 책임지지
                않습니다. 2. 회사는 이용자가 서비스 이용 중 발생한 피해에 대해
                책임지지 않으며, 이용자의 개인적인 손해에 대해서는 별도의 배상을
                하지 않습니다. 제7조 (기타 사항) 1. 본 약관에 명시되지 않은
                사항은 관계 법령에 따릅니다. 2. 회사와 이용자는 본 약관과 관련된
                분쟁 발생 시, 해결을 위해 최선의 노력을 다합니다.
              </p>
            </div>
          </div>
          <!-- 개인정보 -->
          <div>
            <label class="flex">
              <input
                class="chk-box"
                type="checkbox"
                v-model="isPrivacyAccepted"
              />
              <em class="text-center leading-[24px]"
                ><i class="bi bi-check text-white"></i
              ></em>
              <span class="mb-2">개인정보 수집 및 이용에 동의합니다.</span>
            </label>
            <div
              class="w-[100%] bg-[#f7f7f7] h-[160px] rounded-[16px] p-6 overflow-y-scroll"
            >
              <p class="w-[100%]">
                개인정보 수집 및 이용 동의서 1. 개인정보 수집 항목 회사는
                회원가입을 위한 필수 정보를 다음과 같이 수집합니다: - 이름 -
                이메일 주소 - 휴대전화 번호 - 생년월일 - 성별 2. 개인정보의 수집
                및 이용 목적 회사는 수집한 개인정보를 다음과 같은 목적으로
                사용합니다: - 회원 관리 및 서비스 제공 - 서비스 제공을 위한 본인
                인증 - 이용자에게 맞춤형 서비스 제공 - 마케팅 및 광고에 활용 3.
                개인정보 보유 및 이용 기간 회사는 개인정보를 수집 후 회원 탈퇴
                시까지 보유하며, 이용자는 언제든지 자신의 개인정보를 열람, 수정,
                삭제할 수 있습니다. 4. 개인정보의 제3자 제공 회사는 이용자의
                개인정보를 제3자에게 제공하지 않으며, 예외적으로 법적 의무가
                있을 경우 이를 제공할 수 있습니다. 5. 개인정보 처리 위탁 회사는
                서비스 제공에 필요한 업무를 외부 업체에 위탁할 수 있으며,
                위탁되는 업무와 위탁업체에 대해서는 별도로 공지합니다. 6.
                이용자의 권리 이용자는 언제든지 회사가 보유한 개인정보에 대해
                열람, 수정, 삭제를 요구할 수 있습니다. 이러한 요청은 서비스 내
                개인정보 관리 메뉴에서 처리할 수 있습니다. 7. 개인정보의 안전성
                확보 조치 회사는 개인정보 보호를 위해 기술적, 관리적, 물리적
                조치를 취하고 있으며, 개인정보를 안전하게 보호하기 위해 최선을
                다합니다. 8. 개인정보 보호책임자 회사는 개인정보 보호를 위한
                책임자를 지정하고, 고객의 개인정보 보호를 위해 필요한 모든
                조치를 취할 것입니다. 개인정보 보호책임자의 연락처는 아래와
                같습니다: - 이름: 홍정표, 황연주 - 이메일:
                privacy@ourservice.com - 전화번호: 000-1234-5678 9. 동의 거부
                권리 및 불이익 이용자는 개인정보 제공에 대해 동의를 거부할 수
                있으며, 동의를 거부하더라도 서비스 이용에 불이익이 없으나, 일부
                기능이 제한될 수 있습니다.
              </p>
            </div>
          </div>
        </div>

        <button
          :disabled="!canSubmit"
          type="submit"
          class="signUp-btn w-full btn-b-black btn-submit"
        >
          회원가입
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed, watch } from "vue";
import axios from '@/axios/index';

const isPwComplete = ref(false);

// ========================
// 운동 부위 객체
// ========================
// 체크박스 상태를 객체로 관리
const selectedExercises = reactive({
  shoulder: false,
  leg: false,
  abs: false,
  chest: false,
  back: false,
  arm: false,
  stretch: false,
  cardio: false,
});

//==========================
// 회원가입 데이터 객체
//==========================
const formData = reactive({
  userId: "",
  password: "",
  name: "",
  email: "",
  gender: "",
  postCode: "", 
  address: "",
  detailAddress: "",
  extraAddress: "",
  nickname: "",
  birth: "",
});

//==========================
// 최종 전송 객체
//==========================
const signUpData = {
  formData,
  selectedExercises,
};

//==========================
// 아이디 중복 검사
//==========================

// 아이디 중복 검사 결과
const userIdError = ref(null);
const userIdAvailable = ref(false);
// 아이디 중복 검사 함수
const checkUserIdAvailability = async (event) => {
  event.preventDefault(); // 폼 제출을 막음

  // 아이디가 빈 값이면 검사하지 않도록
  if (!formData.userId) {
    userIdError.value = "";
    return;
  }

  try {
    // 서버로 중복 검사 요청을 보냄 (예시 URL, 실제 API 엔드포인트로 수정 필요)
    console.log("check");
    const response = await axios.get('api-user/' + formData.userId);
    console.dir(response);
    userIdError.value = response.data;
    userIdAvailable.value = true;
    // if (response.status === 200) {
    //    // 사용가능 아이디
    // } else {
    //   userIdError.value = "이미 사용 중인 아이디입니다."; // 중복 아이디
    // }
  } catch (error) {
    console.log(error);
    userIdError.value = error.response.data;
    userIdAvailable.value = false;
  }
};

// 중복검사 후 아이디 변경 시 중복검사 해제
watch(() => formData.userId, () => {
  userIdAvailable.value = false;
})

//==========================
// 비밀번호 유효성 검사
//==========================
const passwordError = ref(""); // 비밀번호 유효성 검사 에러 메시지

function validatePassword() {
  // const password = computed(() => formData.password || "");
  const passwordRegex =
    /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&#])[A-Za-z\d@$!%*?&#]{8,16}$/;

  if (!formData.password) {
    passwordError.value = "비밀번호를 입력해주세요.";
    return false;
  }
  if (!passwordRegex.test(formData.password)) {
    passwordError.value =
      "8~16자의 영문 대/소문자, 숫자, 특수문자(@$!%*?&#)를 포함해주세요.";
    return false;
  }
  passwordError.value = "";
  isPwComplete.value = true;
  return true;
}

//==========================
// 재확인 비밀번호 유효성 검사
//==========================
const passwordConfirmError = ref(""); // 비밀번호 유효성 검사 에러 메시지
const confirmPassword = ref(""); // 재확인용 pw 를 담을 변수

function validateConfirmPassword() {
  // const password = computed(() => formData.password || "");

  if (confirmPassword.value !== formData.password) {
    passwordConfirmError.value = "비밀번호가 일치하지 않습니다..";
    return false;
  } else {
    passwordConfirmError.value = "";
  }
  return true;
}

//==========================
// Email 유효성 검사
//==========================
// 이메일 값과 에러 상태를 관리하는 변수
const emailError = ref("");

// 이메일 유효성 검사 함수
const validateEmail = () => {
  const emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  emailError.value =
    formData.email && !emailPattern.test(formData.email)
      ? "이메일 주소가 정확한지 확인해 주세요."
      : "";
};

//==========================
// 데이터 유효성 검사 및 제출
//==========================
// const handleSignup = () => {
//   if (validatePassword()) {
//     alert("회원가입 성공!");
//     console.log("회원가입 데이터: ", formData.value);
//     // 서버로 데이터 전송 로직 추가
//   }
// };

// 회원가입 폼 제출 (여기서 실제 회원가입 로직이 들어갈 수 있습니다)
// function submitForm() {
//   if (canSubmit.value) {
//     alert("회원가입 하시겠습니까?");
//     // 실제 회원가입 API 호출 등을 여기에 작성
//     signUpData;
//   }
// }

// ** 전체 데이터 유효성 검사
// - 아이디V, 비밀번호V, 이름V, 이메일V, 성별, 닉네임, 생년월일 (V-Valid 대상)

const isPasswordValid = computed(
  () =>
    passwordRegex.test(formData.password) &&
    formData.password === confirmPassword
);
const isnameValid = computed(() => formData.name.length >= 5);
const isEmailValid = computed(() => emailPattern.test(formData.email));

// ** 약관동의 및 개인정보

const isTermsAccepted = ref(false); // 이용약관 동의 여부
const isPrivacyAccepted = ref(false); // 개인정보 동의 여부

const canSubmit = computed(() => {
  return (
    formData.userId && // ★ ID 중복검사
    isPasswordValid && // 비밀번호 Valid
    isnameValid && // 성명 Valid
    isEmailValid && // 이메일 Valid
    formData.gender && // 성별
    formData.nickname && // 닉네임
    formData.birth && // 생일
    formData.postCode && // 우편번호
    formData.address && // 주소
    isTermsAccepted.value && // 이용약관
    isPrivacyAccepted.value // 개인정보
  );
});

//=======================
// 회원 가입 요청 처리
//=======================
const responseMessage = ref("");
const handleSignup = async () => {
  if(!userIdAvailable.value) {
    responseMessage.value = "아이디 중복 검사를 진행해주세요.";
    alert(responseMessage.value);
    return;
  }
  console.dir(formData);
  try {
    // axios로 POST 요청 보내기
    // const response = await axios.post("api-user/signup", signUpData);
    const response = await axios.post("api-user/signup", formData);
  
    // 서버 응답 성공 시 처리
    responseMessage.value = "회원가입 성공! 환영합니다.";
    alert(responseMessage.value);
    console.log("서버 응답:", response.data);
  } catch (error) {
    // 서버 오류 또는 네트워크 오류 처리
    if (error.response) {
      // 서버에서 반환한 오류 메시지
      responseMessage.value = `회원가입 실패: ${error.response.data.message}`;
      alert(responseMessage.value);
    } else {
      // 네트워크 오류 등 기타 문제
      responseMessage.value = "서버와 연결할 수 없습니다. 다시 시도해주세요.";
      alert(responseMessage.value);
    }
    console.error("에러 발생:", error);
  }
};

//==========================
// 주소 검색 API : Kakao
//==========================
function openAddressSearch() {
  new daum.Postcode({
    oncomplete: function (data) {
      // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

      // 각 주소의 노출 규칙에 따라 주소를 조합한다.
      // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
      // var addr = ""; // 주소 변수
      // var extraAddr = ""; // 참고항목 변수
      var addr = ""; // 주소 변수
      var extraAddr = ""; // 참고항목 변수

      //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
      if (data.userSelectedType === "R") {
        // 사용자가 도로명 주소를 선택했을 경우
        // addr.value = data.roadAddress;
        addr = data.roadAddress;
      } else {
        // 사용자가 지번 주소를 선택했을 경우(J)
        // addr.value = data.jibunAddress;
        addr = data.jibunAddress;
      }

      // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
      if (data.userSelectedType === "R") {
        // 법정동명이 있을 경우 추가한다. (법정리는 제외)
        // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
        if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
          extraAddr += data.bname;
        }
        // 건물명이 있고, 공동주택일 경우 추가한다.
        if (data.buildingName !== "" && data.apartment === "Y") {
          extraAddr +=
            extraAddr !== "" ? ", " + data.buildingName : data.buildingName;
        }
        // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
        if (extraAddr !== "") {
          extraAddr = " (" + extraAddr + ")";
        }
        // 조합된 참고항목을 해당 필드에 넣는다.
        // document.getElementById("sample6_extraAddress").value = extraAddr;
        formData.extraAddress = extraAddr;
      } else {
        // document.getElementById("sample6_extraAddress").value = "";
        formData.extraAddress = "";
      }

      // 우편번호와 주소 정보를 해당 필드에 넣는다.
      // document.getElementById("sample6_postCode").value = data.zonecode;
      formData.postCode = data.zonecode;
      // document.getElementById("sample6_address").value = addr;
      formData.address = addr;

      // 커서를 상세주소 필드로 이동한다.
      document.getElementById("sample6_detailAddress").focus();
    },
  }).open();
}
</script>

<style scoped>
input[type="text"],
input[type="password"],
input[type="date"],
input[type="email"] {
  width: 100%;
}
.error-message {
  margin-top: 4px;
  color: #f42a8d;
  padding-left: 16px;
}
.btn-box a {
  padding: 0 12px;
  margin: 24px 0;
  border-right: 1px solid #ccc;
  line-height: 1;
}
.btn-box a:last-child {
  border-right: none;
}
.social-login button {
  width: 60px;
  height: 60px;
  border-radius: 16px;
}

/* 기본 라디오 버튼 숨기기 */
input[type="radio"] {
  display: none; /* 기본 라디오 버튼을 숨깁니다 */
}

/* 체크된 라디오 버튼 스타일 */
input[type="radio"]:checked + .radio {
  background-color: var(--juston-black);
  border-color: var(--juston-black);
}

input[type="radio"]:checked {
  box-sizing: border-box;
}
input[type="radio"]:checked + span {
  border: 2px solid var(--juston-black);
  color: var(--juston-black);
  font-weight: 800;
  z-index: 1;
}

/* 체크된 라디오 버튼의 표시(원 모양의 체크 표시) */
input[type="radio"]:checked + .radio::after {
  content: "";
  background-image: url();
  position: absolute;
  left: 5px;
  top: 5px;
  width: 10px;
  height: 10px;
  background-color: white;
}

#map {
  width: 100%;
  height: 400px;
}

input:valid {
  border-color: var(--juston-black); /* 유효할 때 테두리 색 */
  background-color: #fff; /* 배경색 */
}

input:focus {
  background-color: #fff;
}

/* 체크박스 스타일 */
input[type="checkbox"] {
  display: none;
}

.chk-box + em {
  display: inline-block;
  width: 24px;
  height: 24px;
  border: 1px solid #ccc;
  border-radius: 450%;
  margin-right: 6px;
  background-color: #fff;
}

.chk-box:checked + em {
  background-color: var(--juston-black);
}

.signUp-btn:disabled {
  background-color: #ccc;
}
</style>
