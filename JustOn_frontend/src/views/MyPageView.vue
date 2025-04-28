<template>
  <div class="h-[100%] flex justify-center items-center">
    <div class="content-wrap w-[500px] h-[calc(100vh-(160px))]">
      <div
        class="flex justify-between items-center pb-5 mb-10 border-solid border-b border-gray-200"
      >
        <img class="w-[26px]" src="../assets/on-mark.svg" alt="" />
        <a class="logo flex items-end" href="">
          <span class="mr-2 leading-[16px]">날 위한 운동 메이트</span>
          <img class="inline-block" src="../assets/logo.svg" alt="" />
        </a>
      </div>
      <div class="max-h-[87%] pr-2 overflow-y-scroll">
        <h3 class="text-4xl text-center mb-6">마이페이지</h3>
        <div class="mb-12">
          <h4 class="text-2xl font-bold pt-8 pb-7" for="">프로필 수정</h4>
          <div
            class="profile flex flex-col items-center bg-[--juston-bg-w] rounded-2xl gap-3 p-6"
          >
            <!-- 프로필 이미지 부분 -->
            <div class="profile-img relative w-[140px] h-[140px] mb-2">
              <div
                class="w-[140px] h-[140px] rounded-[50%] overflow-hidden flex items-center justify-center"
              >
                <img
                  class="object-cover max-width-auto"
                  referrerpolicy="no-referrer"
                  :src="profileStore.imagePreview"
                  alt="프로필 이미지 미리보기"
                  width="200"
                />
                <!-- <div>
                  <img
                    class="w-[100%]"
                    src="../assets/juston-default-profile-140.png"
                    alt="프로필 기본 이미지"
                  />
                </div> -->
              </div>
              <!-- 프로필 이미지 수정 관련 -->
              <input
                class="hidden"
                type="file"
                @change="onFileChange"
                accept="image/*"
                href=""
                id="proImg-upload"
              />
              <label
                class="absolute bottom-0 right-0 cursor-pointer"
                for="proImg-upload"
              >
                <i
                  class="bi bi-gear shadow-lg shadow-slate-800/10 bg-[#fff] block w-10 h-10 rounded-[50%] text-center leading-10 text-xl"
                ></i>
              </label>
              <!-- 이미지 삭제 관련 -->
              <input
                class="hidden"
                type=""
                @click="profileStore.resetUserImage"
                accept="image/*"
                href=""
                id="reset-img"
              />
              <label
                class="absolute bottom-0 left-0 cursor-pointer"
                for="reset-img"
              >
                <i
                  class="bi bi-arrow-clockwise shadow-lg shadow-slate-800/10 bg-[#fff] block w-10 h-10 rounded-[50%] text-center leading-10 text-xl"
                ></i>
              </label>
            </div>

            <div calss="profile-txt">
              <p class="flex flex-col items-center rounded-[50%] mb-1 text-xl">
                <span class=""
                  ><em class="font-[800]">{{ formData.userId }}</em
                  >님</span
                >
                <span>{{ formData.email }} </span>
              </p>
            </div>
          </div>
        </div>
        <form class="flex flex-col gap-8" @submit.prevent="updateUserInfo">
          <h4
            class="text-2xl font-bold border-solid border-t border-gray-200 pt-8"
            for=""
          >
            회원정보 수정
          </h4>
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

          <!-- 이름 -->
          <div class="form-group">
            <label for="user_name">이름</label>
            <input
              class="input-style-h60 bg-[#f3f3f3]"
              type="text"
              id="user_name"
              v-model="formData.name"
              placeholder="이름을 입력하세요"
              required
              disabled
            />
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
            <p v-if="passwordError" class="error-message">
              {{ passwordError }}
            </p>
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
                  class="block h-[60px] leading-10 border-solid border-2 border-gray-200 py-2 px-4 text-center cursor-pointer relative rounded-tr-[16px] rounded-br-[16px]"
                >
                  여성
                </span>
              </label>
              <!-- <label class="flex-1 ml-[-2px]">
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
              </label> -->
            </div>
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
            <div class="bg-[#f7f7f7] p-5 rounded-[16px] flex gap-4 flex-wrap">
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

          <button
            :disabled="!canSubmit"
            type="submit"
            class="signUp-btn w-full btn-b-black btn-submit"
          >
            수정하기
          </button>
          <div></div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed, watch } from "vue";
import axios from "@/axios/index";
import { useProfileStore } from "@/stores/profile";
import Swal from "sweetalert2";
const profileStore = useProfileStore();

const isPwComplete = ref(false);

// ========================
// 프로필 이미지 파일 선택 및 업로드
// ========================
const file = ref(null);

// 이미지 파일 선택 시 실행
const onFileChange = (event) => {
  const selectedFile = event.target.files[0];
  console.log(event);

  if (selectedFile) {
    // 미리보기 이미지 업데이트
    // imagePreview.value = URL.createObjectURL(selectedFile);

    file.value = selectedFile; // 파일 상태 저장
    console.log("##file.value : ", file.value);
    profileStore.uploadImage(file.value);
  }
};


// ========================
// 회원정보 받아오기
// ========================
// 컴포넌트 마운트 시 유저 데이터 로드
onMounted(() => {
  fetchUserData();
  profileStore.getUserImage();
});

const fetchUserData = async () => {
  try {
    const response = await axios.get(`api-user/userInfo`);
    const userData = response.data;

    // 가져온 데이터로 formData 채우기
    formData.userId = userData.userId;
    formData.name = userData.name;
    formData.email = userData.email;
    formData.gender = userData.gender;
    formData.postCode = userData.postCode;
    formData.address = userData.address;
    formData.detailAddress = userData.detailAddress;
    formData.extraAddress = userData.extraAddress;
    formData.nickname = userData.nickname;
    formData.birth = userData.birth;

    // 운동 부위 정보 채우기
    // selectedExercises.shoulder = userData.selectedExercises.shoulder;
    // selectedExercises.leg = userData.selectedExercises.leg;
    // selectedExercises.abs = userData.selectedExercises.abs;
    // selectedExercises.chest = userData.selectedExercises.chest;
    // selectedExercises.back = userData.selectedExercises.back;
    // selectedExercises.arm = userData.selectedExercises.arm;
    // selectedExercises.stretch = userData.selectedExercises.stretch;
    // selectedExercises.cardio = userData.selectedExercises.cardio;
  } catch (error) {
    console.error("회원 정보 로딩 오류:", error);
  }
};

// ========================
// 회원 정보 제출/전송
// ========================
const updateUserInfo = async () => {
  try {
    const response = await axios.put("/api-user/signup", formData); // 수정된 정보 전송
    Swal.fire({
      icon : "success",
      text : "회원 정보가 수정되었습니다."
    })
  } catch (error) {
    console.error("회원 정보 수정 오류:", error);
  }
};

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
const signUpModifyData = {
  formData,
  selectedExercises,
};

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

// ** 전체 데이터 유효성 검사
// - 아이디V, 비밀번호V, 이름V, 이메일V, 성별, 닉네임, 생년월일 (V-Valid 대상)

const isPasswordValid = computed(
  () =>
    passwordRegex.test(formData.password) &&
    formData.password === confirmPassword
);
// const isnameValid = computed(() => formData.name.length >= 5);
const isEmailValid = computed(() => emailPattern.test(formData.email));

// ** 약관동의 및 개인정보

// const isTermsAccepted = ref(false); // 이용약관 동의 여부
// const isPrivacyAccepted = ref(false); // 개인정보 동의 여부

const canSubmit = computed(() => {
  return (
    // formData.userId && // ★ ID 중복검사
    isPasswordValid && // 비밀번호 Valid
    // isnameValid && // 성명 Valid
    isEmailValid && // 이메일 Valid
    formData.gender && // 성별
    formData.nickname && // 닉네임
    formData.birth && // 생일
    formData.postCode && // 우편번호
    formData.address // 주소
    // isTermsAccepted.value && // 이용약관
    // isPrivacyAccepted.value // 개인정보
  );
});

//=======================
// 회원 가입 요청 처리
//=======================
const responseMessage = ref("");
const handleSignup = async () => {
  if (!userIdAvailable.value) {
    responseMessage.value = "아이디 중복 검사를 진행해주세요.";
    Swal.fire({
      icon : "warning",
      text : responseMessage.value
    })
    return;
  }
  console.dir(formData);
  try {
    // axios로 POST 요청 보내기
    // const response = await axios.post("api-user/signup", signUpData);
    const response = await axios.post("api-user/signup", formData);

    // 서버 응답 성공 시 처리
    responseMessage.value = "회원가입 성공! 환영합니다.";
    Swal.fire({
      icon : "success",
      text : responseMessage.value
    })
    console.log("서버 응답:", response.data);
  } catch (error) {
    // 서버 오류 또는 네트워크 오류 처리
    if (error.response) {
      // 서버에서 반환한 오류 메시지
      responseMessage.value = `회원가입 실패: ${error.response.data.message}`;
      Swal.fire({
      icon : "error",
      text : responseMessage.value
    })
    } else {
      // 네트워크 오류 등 기타 문제
      responseMessage.value = "서버와 연결할 수 없습니다. 다시 시도해주세요.";
      Swal.fire({
      icon : "error",
      text : responseMessage.value
    })
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

img {
  max-width: none;
}
</style>
