import { ref } from 'vue';
import { defineStore } from "pinia";
import axios from '@/axios/index';
import defaultImg from "@/assets/juston-default-profile-140.png";

export const useProfileStore = defineStore("profile", () => {

    const imagePreview = ref(null);

    // ========================
    // 프로필 이미지 조회
    // ========================
    const imgSysName = ref("");
    const getUserImage = async () => {
        try {
            const response = await axios.get("/api-user/profile");
            const imgData = response.data;
            imgSysName.value = imgData.systemName;

            const url = `http://localhost/uploads/${imgSysName.value}/${imgSysName.value}`;

            const response2 = await axios.get(url, {
                responseType: "blob",
                // headers: {
                //   Authorization: "Bearer " + localStorage.getItem("jwt"),
                // },
            });

            imagePreview.value = URL.createObjectURL(response2.data);

            // imagePreview.value = `http://localhost:8080/uploads/${imgSysName}`;
            // console.log("프로필 이미지 데이터 response", response);
            // console.log("프로필 이미지 imgSysName", imgSysName.value);
        } catch (error) {
            imagePreview.value = defaultImg;
            // console.log("프로필 이미지 imgSysName", imgSysName.value);
            console.error("프로필 이미지 조회 실패", error);
        }
    };


    // ========================
    // 프로필 이미지 파일 업로드
    // ========================
    // 이미지를 서버로 업로드하는 함수
    const uploadImage = async (file) => {
        // 파일 상태 체크
        if (!file) {
            alert("업로드할 파일을 선택해주세요.");
            return;
        }

        // const formData = new FormData();
        // formData.append("profileImage", file.value);

        try {
            const response = await axios.post(
                "/api-user/profile/upload",
                { file }, // 객체? 그대로?
                {
                    headers: {
                        "Content-Type": "multipart/form-data",
                    },
                }
            );

            // console.log("response:", response);
            // 성공적으로 업로드된 경우, 응답으로 받은 이미지 URL을 사용해 프로필 이미지 업데이트
            const imageUrl = response.data.imageUrl;
            // console.log("업로드된 이미지 URL:", imageUrl);
            alert("프로필 이미지가 성공적으로 업로드되었습니다.");
            getUserImage();
        } catch (error) {
            console.error("이미지 업로드 오류:", error);
            alert("이미지 업로드에 실패했습니다.");
        }
    };

    // ========================
    // 프로필 이미지 파일 삭제
    // ========================
    const resetUserImage = async () => {
        const url = "src/main/resources/static/imgs/juston-default-profile-140.png";
        // imagePreview.value = URL.createObjectURL(url);
        try {
            const response = await axios.delete(`api-user/profile`);
            console.log("프로필 이미지 삭제 성공", response);
            imagePreview.value = defaultImg;
        } catch (error) {
            console.error("프로필 이미지 삭제 실패", error);
        }
    };
    return { imagePreview, getUserImage, uploadImage, resetUserImage };
})