<template>
    <div>
        <h2>재생목록 선택</h2>
        <div v-for="p in playlist">
            <div class="item">
                <input :id="p.categoryName" class="cbx" type="checkbox" v-model="p.registed" style="display: none;" />
                <label :for="p.categoryName" class="cbx" @click="insertOrDeleteVideo(p.registed, p.categoryName)">
                    <svg width="14px" height="12px" viewBox="0 0 14 12">
                        <polyline points="1 7.6 5 11 13 1"></polyline>
                    </svg>
                </label>
            </div>
            <span>{{ p.categoryName }}</span>
            <!-- <span v-if="p.groupDesc !== ''">> {{ p.groupDesc }}</span> -->
            <span class="deleteBtn" @click="deletePlaylist(p.categoryName)">❌</span>
        </div>
        <div v-if="playlist.length === 0">재생목록이 없습니다.</div>
        <button @click="openAddPlaylistView">+ 재생목록 추가</button>
    </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import axios from 'axios';
import { useToast } from "vue-toastification";

const Toast = useToast();
const emit = defineEmits(['openAddPlaylistView', 'deletePlaylist']);
const openAddPlaylistView = () => {
    emit('openAddPlaylistView');
}
const props = defineProps(['count', 'videoNo']);
const playlist = ref([]);
const registedPlaylist = ref([]);
const requestPlaylist = async () => {
    const { data } = await axios.get("http://localhost:8080/api/video/playlist/" + props.videoNo);
    console.dir(data);
    playlist.value = data.list;
    registedPlaylist.value = data.registedList;
    for (const p of playlist.value) {
        if (registedPlaylist.value.includes(p.categoryName)) {
            p.registed = true;
        }
    }
}
requestPlaylist();


watch(() => props.count, () => {
    requestPlaylist();
});

const deletePlaylist = async (categoryName) => {
    if (confirm(`재생목록명 : ${categoryName}\n 재생목록을 삭제하시겠습니까?`)) {
        try {
            await axios.delete("http://localhost:8080/api/video/playlist/" + categoryName);
            emit('deletePlaylist');
        } catch (error) {
            console.dir(error);
        }

    }
}

const insertOrDeleteVideo = async (registed, categoryName) => {
    console.log(registed);
    if (!registed) {
        try {
            await axios.post(`http://localhost:8080/api/video/${categoryName}/${props.videoNo}`);
            requestPlaylist();
            Toast(`[${categoryName}] 재생목록에 영상이 추가되었습니다.`, {
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
                toastClassName: "toast"
            });
        } catch (error) {
            console.dir(error);
        }
    } else {
        try {
            await axios.delete(`http://localhost:8080/api/video/${categoryName}/${props.videoNo}`);
            requestPlaylist();
            Toast("재생목록에서 영상이 삭제되었습니다.", {
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

                // // 토스트 팝업 내부 글자 혹은 컴포넌트에 대한 스타일 선언
                // bodyClassName: ["toastCustomBodyStyle-1", "toastCustomBodyStyle-2"]
            });
        } catch (error) {
            console.dir(error);
        }
    }

}
</script>

<style>
.Vue-Toastification__toast--default.toast {
    /* background-color: black;
    color: #18cda6; */
    background-color: #ecd6ffd2;
    color: black;
}
span {
    margin-right: 5vh;
}

.deleteBtn {
    cursor: pointer;
}

.item {
    /* align-self: center; */
    user-select: none;
    transform: translateZ(0);
    margin-right: 6px;
    display: inline-block;
}

.item .cbx {
    position: relative;
    top: 5px;
    display: inline-block;
    width: 14px;
    height: 14px;
    border: 1px solid #c8ccd4;
    border-radius: 3px;
    cursor: pointer;
}

.item .cbx>svg {
    position: relative;
    top: -5px;
    transform: scale(0);
    fill: none;
    stroke-linecap: round;
    stroke-linejoin: round;
}

.item .cbx svg polyline {
    stroke-width: 2;
    stroke: #18cda6;
}

.item .cbx:before {
    content: '';
    position: absolute;
    top: 50%;
    left: 50%;
    margin: -10px 0 0 -10px;
    width: 20px;
    height: 20px;
    border-radius: 100%;
    background: #18cda6;
    transform: scale(0);
}

.item .cbx:after {
    content: '';
    position: absolute;
    top: 5px;
    left: 5px;
    width: 2px;
    height: 2px;
    border-radius: 2px;
    box-shadow: 0 -18px 0 #18cda6, 12px -12px 0 #18cda6, 18px 0 0 #18cda6, 12px 12px 0 #18cda6, 0 18px 0 #18cda6, -12px 12px 0 #18cda6, -18px 0 0 #18cda6, -12px -12px 0 #18cda6;
    transform: scale(0);
}

.item .cbx-lbl {
    position: relative;
    cursor: pointer;
    transition: color 0.3s ease;
}

.item .cbx-lbl:after {
    content: '';
    position: absolute;
    top: 50%;
    left: 0;
    width: 0;
    height: 1px;
    background: #9098a9;
}

.item input {
    display: none;
}

.item input:checked+.cbx {
    border-color: transparent;
}

.item input:checked+.cbx svg {
    transform: scale(1);
    transition: all 0.4s ease;
    transition-delay: 0.1s;
}

.item input:checked+.cbx:before {
    transform: scale(1);
    opacity: 0;
    transition: all 0.3s ease;
}

.item input:checked+.cbx:after {
    transform: scale(1);
    opacity: 0;
    transition: all 0.6s ease;
}

.item input:checked~.cbx-lbl {
    color: #9098a9;
}

.item input:checked~.cbx-lbl:after {
    width: 100%;
    transition: all 0.4s ease;
}
</style>