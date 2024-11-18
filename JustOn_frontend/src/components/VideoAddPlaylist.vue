<template>
  <div class="modal-overlay">
    <div
      class="modal-con p-6 rounded-[16px] w-[500px] h-[400px]"
      v-click-outside="closeAddPlaylistView"
    >
      <h2 class="mb-3">재생목록 추가하기</h2>
      <div>
        <form @submit.prevent="addPlaylist">
          <div class="flex flex-col mb-4">
            <label class="mb-1">재생목록명</label>
            <input
              class="w-[100%] input-style-h52"
              type="text"
              v-model="playlist.categoryName"
            />
          </div>
          <div class="flex flex-col mb-6">
            <label>설명</label>
            <textarea v-model="playlist.groupDesc"></textarea>
          </div>
          <div class="flex justify-between gap-3">
            <button class="btn-m-white flex-1">만들기</button>
            <button
              class="btn-m-white flex-1"
              type="button"
              @click="closeAddPlaylistView"
            >
              취소
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { useToast } from "vue-toastification";

const Toast = useToast();


const playlist = ref({
  categoryName: "",
  groupDesc: "",
});
const emit = defineEmits(["closeAddPlaylistView", "successAdd"]);
const closeAddPlaylistView = () => {
  emit("closeAddPlaylistView");
};
const addPlaylist = async () => {
  try {
    if (playlist.value.categoryName.trim() === "") {
      alert("재생목록명을 입력해주세요");
      return false;
    }
    const { response } = await axios.post(
      "http://localhost:8080/api-video/playlist",
      playlist.value
    );
    Toast(`[${playlist.value.categoryName}] 재생목록이 추가되었습니다.`, {
        position: "top-right",
        timeout: 3000,
        closeOnClick: true,
        pauseOnFocusLoss: true,
        pauseOnHover: false,
        draggable: true,
        draggablePercent: 0.6,
        showCloseButtonOnHover: false,
        hideProgressBar: true,
        closeButton: false,
        icon: false,
        rtl: false,
        toastClassName: "toast",
      });
    emit("successAdd");
  } catch (error) {
  } finally {
    playlist.value.categoryName = "";
    playlist.value.groupDesc = "";
    closeAddPlaylistView();
  }
};
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  left: 0;
  top: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
}
.modal-con {
  background-color: #fff;
}
.content {
  height: 100%;
  overflow-y: scroll;
}
</style>
