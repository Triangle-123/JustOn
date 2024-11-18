<template>
    <div class="container">
        <div>
            <div class="list" v-for="video in videoList">
                <span>{{ video.title }}</span>
                <button class="add-btn" @click="selectVideo(video)">재생목록에 추가</button>
                <svg xmlns="http://www.w3.org/2000/svg" @click="openMenu(video)" width="16" height="16" fill="currentColor"
                    class="bi bi-three-dots-vertical menu" viewBox="0 0 16 16">
                    <path
                        d="M9.5 13a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0m0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0m0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0" />
                </svg>
                <label class="menu-box" v-if="video.menuView" v-click-outside="closeMenu">
                    <div @click="modifyVideo(video.videoNo)">
                        <p>수정</p>
                    </div>
                    <hr>
                    <div @click="deleteVideo(video.videoNo)">
                        <p>삭제</p>
                    </div>
                </label>
                <hr>
            </div>
        </div>
        <div v-for="video in videoList">
            <VideoPlaylist v-if="video.selected" @deletePlaylist="playlistAddCount--" :count="playlistAddCount"
                :videoNo="video.videoNo" @open-add-playlist-view="doOpenAddPlaylist" />
        </div>
        <div v-if="videoList === ''">등록된 영상이 없습니다.</div>
    </div>
    <VideoAddPlaylist @success-add="playlistAddCount++" @close-add-playlist-view="doCloseAddPlaylist"
        v-if="addListView" />
</template>

<script setup>
import { ref, watch } from 'vue';
import axios from '@/axios/index';
import VideoPlaylist from './VideoPlaylist.vue';
import VideoAddPlaylist from './VideoAddPlaylist.vue';
const addListView = ref(false);
const playlistAddCount = ref(0);

const props = defineProps(['count']);
const emit = defineEmits(['deleteVideo', 'modifyVideo']);

const doOpenAddPlaylist = () => {
    addListView.value = true;
}

const doCloseAddPlaylist = () => {
    addListView.value = false;
}


const videoList = ref([]);
const requestVideoList = async () => {
    const { data } = await axios.get("api-video");
    // console.dir(data);
    videoList.value = data;
    for (const video of videoList.value) {
        video.selected = false;
        video.menuView = false;
    }
}

watch(() => props.count, () => {
    requestVideoList();
});
requestVideoList();

const selectVideo = (selectedVideo) => {
    if (selectedVideo.selected) {
        selectedVideo.selected = false;
    }
    else {
        for (const video of videoList.value) {
            video.selected = false;
        }
        selectedVideo.selected = true;
    }
}
const openMenu = (selectedVideo) => {
    if (selectedVideo.menuView) {
        selectedVideo.menuView = false;
    }
    else {
        for (const video of videoList.value) {
            video.menuView = false;
        }
        selectedVideo.menuView = true;
    }
}
const closeMenu = () => {
    for (const video of videoList.value) {
        video.menuView = false;
    }
}
const deleteVideo = async (videoNo) => {
    try {
        if(confirm("영상 삭제 시 재생목록 내에서도 지워집니다.\n삭제하시겠습니까?")) {
            await axios.delete("api-video/" + videoNo);
            emit('deleteVideo');
        }
    } catch (error) {
        alert("영상 삭제에 실패하였습니다.");
    }
}

const modifyVideo = (videoNo) => {
    emit('modifyVideo', videoNo);
}
</script>

<style scoped>
.container {
    display: flex;
}

.list {
    margin: 1vh 0;
    margin-right: 2vw;
    position: relative;
    width: 40vw;
}

.add-btn {
    cursor: pointer;
    margin-right: 1vw;
}
span {
    margin-right: 20px;
}

.menu {
    cursor: pointer;
}

.menu-box {
    display: inline-block;
    background-color: #f8eeee;
    > hr {
        margin: auto;
    }
    > div {
        width: 5vw;
        height: 3vh;
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        cursor: pointer;
        > p {
            font-weight: bold;
            margin: auto;
        }
    }
    :hover {
        background-color: #ccc;
        transition: 0.2s;
    }
}
</style>